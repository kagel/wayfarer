package com.wavedroid.wayfarer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.ResultMeta;
import fi.foyt.foursquare.api.entities.Checkin;
import fi.foyt.foursquare.api.entities.CheckinGroup;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteVenue;
import fi.foyt.foursquare.api.entities.Location;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dkhvatov
 *         created on 18.12.12, 14:45
 */
public class FoursquareUtils {

    private static final Logger log = LoggerFactory.getLogger(FoursquareUtils.class);
    private static final String baseUrl = "http://where.yahooapis.com";
    private static final String appId = "JwXC7G32";
    private static Map<String, String> cityCache = new HashMap<>(); // hi-hello, java7 !

    public static String unshorten(String shortened) throws IOException {
        HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter("http.protocol.handle-redirects", false);

        HttpResponse response = client.execute(new HttpGet(shortened));
        int statusCode = response.getStatusLine().getStatusCode();
        Header[] locationHeader = response.getHeaders("Location");
        String location = locationHeader[0].getValue();

        while (statusCode == 301) {
            client = new DefaultHttpClient();
            client.getParams().setParameter("http.protocol.handle-redirects", false);
            response = client.execute(new HttpGet(location.contains("://") ? location : ("https://foursquare.com" + location)));
            statusCode = response.getStatusLine().getStatusCode();
            locationHeader = response.getHeaders("Location");
            if (locationHeader.length == 0) break;
            location = locationHeader[0].getValue();
        }

        return location;

    }

    public static Result<Checkin> getCheckinByURL(String url, FoursquareApi api) throws FoursquareApiException {
        String checkinId = url.substring(url.indexOf("/checkin/") + 9, url.indexOf("?"));
        String signature = url.substring(url.indexOf("?s=") + 3, url.indexOf("&"));
        return api.checkin(checkinId, signature);
    }

    public static Checkin getLastCheckin(FoursquareApi api, String userId) throws FoursquareApiException {

        int days = -1;
        log.debug("Getting last checkin for last " + Math.abs(days) + " day(s)");
        Checkin[] checkins = getLastCheckins(api, userId, 1, days);
        while (checkins == null && days >= -7) {
            days--;
            log.debug("Getting last checkin for last " + Math.abs(days) + " day(s)");
            checkins = getLastCheckins(api, userId, 1, days);
        }

        if (checkins == null || checkins.length == 0) {
            log.debug("We tried everything, but could not find your last checkin :(");
            return null;
        }

        return checkins[0];
    }

    public static Checkin[] getLastCheckins(FoursquareApi api, String userId, int limit, int days) throws FoursquareApiException {

        Date now = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DATE, days);
        Date ndaysAgo = cal.getTime();

        Result<CheckinGroup> result;
        result = api.usersCheckins(userId, 100, 0, ndaysAgo.getTime() / 1000, 999999999999L);

        ResultMeta meta = result.getMeta();
        String errorDetail = meta.getErrorDetail();
        String errorType = meta.getErrorType();
        Integer code = meta.getCode();
        log.debug("errorDetail=" + errorDetail + ", errorType=" + errorType + ", code=" + code);

        Checkin[] checkins = result.getResult().getItems();
        if (checkins.length == 0) {
            log.debug("There was no checkins last " + Math.abs(days) + " days");
            return null;
        }

        if (limit > checkins.length)
            limit = checkins.length;
        Checkin[] limited = new Checkin[limit];
        System.arraycopy(checkins, checkins.length - limit, limited, 0, limit);

        return limited;
    }

    public static Checkin[] getLastCheckins(FoursquareApi api, String userId, int limit) throws FoursquareApiException {
        return getLastCheckins(api, userId, limit, -7);
    }

    public static Checkin[] getLastCheckins(FoursquareApi api, String userId, Date since) throws FoursquareApiException {
        Result<CheckinGroup> result;
        result = api.usersCheckins(userId, 500, 0, since.getTime() / 1000, 999999999999L);

        ResultMeta meta = result.getMeta();
        String errorDetail = meta.getErrorDetail();
        String errorType = meta.getErrorType();
        Integer code = meta.getCode();
        log.debug("errorDetail=" + errorDetail + ", errorType=" + errorType + ", code=" + code);

        Checkin[] checkins = result.getResult().getItems();
        if (checkins.length == 0) {
            log.debug("There was no checkins since " + since);
            return null;
        }

        return checkins;
    }

    public static Result<CompleteVenue> getVenueByURL(FoursquareApi api, String url) throws FoursquareApiException {
        String venueId = url.substring(url.lastIndexOf("/") + 1);
        return api.venue(venueId);
    }

    public static String findCity(FoursquareApi api, CompactVenue venue) {
        String ll = venue.getLocation().getLat() + "," + venue.getLocation().getLng();
        if (cityCache.containsKey(ll)) {
            String city = cityCache.get(ll);
            log.debug("found in cache: " + ll + "->" + city);
            return city;
        }
        log.debug("Trying to find city name of " + ll);
        Result<VenuesSearchResult> searchResult;
        try {
            searchResult = api.venuesSearch(ll, 0.0, 0.0, 0.0, null, 10, "checkin", null, null, null, null);
        } catch (FoursquareApiException e) {
            e.printStackTrace();
            return null;
        }

        if (searchResult == null) {
            log.debug("nothing found near null city venue, maybe try another search params?");
            return null;
        }

        ResultMeta meta = searchResult.getMeta();
        log.debug("api result code: " + meta.getCode());
        log.debug("api result error: " + meta.getErrorType() + ", " + meta.getErrorDetail());

        VenuesSearchResult result = searchResult.getResult();
        CompactVenue[] venues = result.getVenues();
        if (venues == null || venues.length == 0) {
            log.debug("nothing found near null city venue, maybe try another search params?");
            return null;
        }

        log.debug("Found " + venues.length + " locations nearby");

        String foundCity = null;
        for (CompactVenue found : venues) {
            Location loc = found.getLocation();
            if (loc == null) {
                log.debug("Trying out " + found.getName() + "вЂ¦ " + "no location");
                continue;
            }
            if (loc.getCity() == null) {
                log.debug("Trying out " + found.getName() + "вЂ¦ " + "no city");
                continue;
            }
            if (loc.getCity().equals("")) {
                log.debug("Trying out " + found.getName() + "вЂ¦ " + "empty city");
                continue;
            }
            foundCity = loc.getCity();
            log.debug("Trying out " + found.getName() + "вЂ¦ " + "city=" + foundCity);
            break;
        }

        log.debug("Resulting city for location: " + foundCity);
        cityCache.put(ll, foundCity);
        return foundCity;
    }

    public static String findCity(CompactVenue venue) {
        String latlon = venue.getLocation().getLat() + "," + venue.getLocation().getLng();
        return findCity(latlon);
    }

    private static String findCity(String latlon) {
        String city = null;
        if (cityCache.containsKey(latlon)) {
            city = cityCache.get(latlon);
            log.debug("found in cache: " + latlon + "->" + city);
            return city;
        }

        String url = baseUrl + "/geocode?q=" + latlon + "&flags=J&gflags=R&appid=" + appId;

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);

        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
        HttpResponse response;
        try {
            response = client.execute(get);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String jsonStr = br.readLine();
            JSONObject json = new JSONObject(jsonStr);
            JSONObject resultSet = (JSONObject) json.get("ResultSet");

            if (resultSet.has("Results")) {
                JSONArray results = (JSONArray) resultSet.get("Results");
                JSONObject result = (JSONObject) results.get(0);
                city = (String) result.get("city");
            } else {
                log.debug("No results from yahoo for location=" + latlon);
            }

        } catch (IOException | JSONException e) { // hi-hello, java7 !
            e.printStackTrace();
        }

        log.debug("Resulting city for location: " + city);
        cityCache.put(latlon, city);
        return city;
    }

    public static String printVenue(CompactVenue venue) {
        Location loc = venue.getLocation();
        if (loc == null) return venue.getName();
        return venue.getName() + " [" + loc.getCity() + ", " + loc.getCountry() + "]";
    }

    public static void main(String args[]) {
        log.debug(findCity("69.878729931068584,38.352887588814184"));
    }


    public static String getLatLon(CompactVenue venue) {
        return getLatLon(venue, 0.0, 0.0);
    }

    public static String getLatLon(CompactVenue venue, double latOffset, double lonOffset) {
        if (venue == null) return "";
        Location loc = venue.getLocation();
        if (loc == null) return "";
        if (loc.getLat() == null) return "";
        if (loc.getLng() == null) return "";
        return ((loc.getLat() + latOffset) + "," + (loc.getLng() + lonOffset));
    }


}
