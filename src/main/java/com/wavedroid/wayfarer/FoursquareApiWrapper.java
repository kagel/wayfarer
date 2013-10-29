package com.wavedroid.wayfarer;

import java.util.Map;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.ResultMeta;
import fi.foyt.foursquare.api.entities.Badges;
import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.Checkin;
import fi.foyt.foursquare.api.entities.CheckinGroup;
import fi.foyt.foursquare.api.entities.Comment;
import fi.foyt.foursquare.api.entities.CompactUser;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteSpecial;
import fi.foyt.foursquare.api.entities.CompleteTip;
import fi.foyt.foursquare.api.entities.CompleteUser;
import fi.foyt.foursquare.api.entities.CompleteVenue;
import fi.foyt.foursquare.api.entities.LeaderboardItemGroup;
import fi.foyt.foursquare.api.entities.LinkGroup;
import fi.foyt.foursquare.api.entities.Photo;
import fi.foyt.foursquare.api.entities.PhotoGroup;
import fi.foyt.foursquare.api.entities.Recommended;
import fi.foyt.foursquare.api.entities.Setting;
import fi.foyt.foursquare.api.entities.SpecialGroup;
import fi.foyt.foursquare.api.entities.TipGroup;
import fi.foyt.foursquare.api.entities.Todo;
import fi.foyt.foursquare.api.entities.TodoGroup;
import fi.foyt.foursquare.api.entities.UserGroup;
import fi.foyt.foursquare.api.entities.VenueHistoryGroup;
import fi.foyt.foursquare.api.entities.VenuesAutocompleteResult;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;
import fi.foyt.foursquare.api.io.IOHandler;

import static com.wavedroid.wayfarer.WayfarerProperties.getAccessToken;
import static com.wavedroid.wayfarer.WayfarerProperties.getClientId;
import static com.wavedroid.wayfarer.WayfarerProperties.getClientSecret;
import static com.wavedroid.wayfarer.WayfarerProperties.getRedirectUrl;

/**
 * @author DKhvatov
 */
public class FoursquareApiWrapper {

    public static final FoursquareApiWrapper api = new FoursquareApiWrapper();
    private final FoursquareApi foursquareApi;
    private ResultMeta lastResult;
    private ResultMeta lastCheckinResult;

    private FoursquareApiWrapper() {
        foursquareApi = new FoursquareApi(getClientId(), getClientSecret(), getRedirectUrl());
        foursquareApi.setoAuthToken(getAccessToken());
    }

    public ResultMeta getLastResult() {
        return lastResult;
    }

    public ResultMeta getLastCheckinResult() {
        return lastCheckinResult;
    }

    public String getOAuthToken() {
        return foursquareApi.getOAuthToken();
    }


    public void setoAuthToken(String oAuthToken) {
        foursquareApi.setoAuthToken(oAuthToken);
    }


    public void setSkipNonExistingFields(boolean skipNonExistingFields) {
        foursquareApi.setSkipNonExistingFields(skipNonExistingFields);
    }


    public void setVersion(String version) {
        foursquareApi.setVersion(version);
    }


    public void setUseCallback(boolean useCallback) {
        foursquareApi.setUseCallback(useCallback);
    }


    public boolean getUseCallback() {
        return foursquareApi.getUseCallback();
    }


    public Result<CompleteUser> user(String userId) throws FoursquareApiException {
        Result<CompleteUser> result = foursquareApi.user(userId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<LeaderboardItemGroup> usersLeaderboard(Integer neighbors) throws FoursquareApiException {
        Result<LeaderboardItemGroup> result = foursquareApi.usersLeaderboard(neighbors);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Badges> usersBadges(String userId) throws FoursquareApiException {
        Result<Badges> result = foursquareApi.usersBadges(userId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CheckinGroup> usersCheckins(String userId, Integer limit, Integer offset, Long afterTimestamp, Long beforeTimestamp) throws FoursquareApiException {
        Result<CheckinGroup> result = foursquareApi.usersCheckins(userId, limit, offset, afterTimestamp, beforeTimestamp);
        lastResult = result.getMeta();
        return result;
    }


    public Result<TipGroup> usersTips(String userId, String sort, String ll, Integer limit, Integer offset) throws FoursquareApiException {
        Result<TipGroup> result = foursquareApi.usersTips(userId, sort, ll, limit, offset);
        lastResult = result.getMeta();
        return result;
    }


    public Result<TodoGroup> usersTodos(String userId, String sort, String ll) throws FoursquareApiException {
        Result<TodoGroup> result = foursquareApi.usersTodos(userId, sort, ll);
        lastResult = result.getMeta();
        return result;
    }


    public Result<VenueHistoryGroup> usersVenueHistory(String userId, Long beforeTimestamp, Long afterTimestamp) throws FoursquareApiException {
        Result<VenueHistoryGroup> result = foursquareApi.usersVenueHistory(userId, beforeTimestamp, afterTimestamp);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompleteUser> usersRequest(String id) throws FoursquareApiException {
        Result<CompleteUser> result = foursquareApi.usersRequest(id);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompleteUser> usersUnfriend(String userId) throws FoursquareApiException {
        Result<CompleteUser> result = foursquareApi.usersUnfriend(userId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompleteUser> usersApprove(String userId) throws FoursquareApiException {
        Result<CompleteUser> result = foursquareApi.usersApprove(userId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompleteUser> usersDeny(String userId) throws FoursquareApiException {
        Result<CompleteUser> result = foursquareApi.usersDeny(userId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompleteUser> usersSetPings(String userId, String value) throws FoursquareApiException {
        Result<CompleteUser> result = foursquareApi.usersSetPings(userId, value);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompactUser[]> usersSearch(String phone, String email, String twitter, String twitterSource, String fbid, String name) throws FoursquareApiException {
        Result<CompactUser[]> result = foursquareApi.usersSearch(phone, email, twitter, twitterSource, fbid, name);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompactUser[]> usersRequests() throws FoursquareApiException {
        Result<CompactUser[]> result = foursquareApi.usersRequests();
        lastResult = result.getMeta();
        return result;
    }


    public Result<UserGroup> usersFriends(String userId) throws FoursquareApiException {
        Result<UserGroup> result = foursquareApi.usersFriends(userId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompleteVenue> venue(String venueId) throws FoursquareApiException {
        Result<CompleteVenue> result = foursquareApi.venue(venueId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Recommended> venuesExplore(String ll, Double llAcc, Double alt, Double altAcc, Integer radius, String section, String query, Integer limit, String basis) throws FoursquareApiException {
        Result<Recommended> result = foursquareApi.venuesExplore(ll, llAcc, alt, altAcc, radius, section, query, limit, basis);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CheckinGroup> venuesHereNow(String venueId, Integer limit, Integer offset, Long afterTimestamp) throws FoursquareApiException {
        Result<CheckinGroup> result = foursquareApi.venuesHereNow(venueId, limit, offset, afterTimestamp);
        lastResult = result.getMeta();
        return result;
    }


    public Result<TipGroup> venuesTips(String venueId, String sort, Integer limit, Integer offset) throws FoursquareApiException {
        Result<TipGroup> result = foursquareApi.venuesTips(venueId, sort, limit, offset);
        lastResult = result.getMeta();
        return result;
    }


    public Result<PhotoGroup> venuesPhotos(String venueId, String group, Integer limit, Integer offset) throws FoursquareApiException {
        Result<PhotoGroup> result = foursquareApi.venuesPhotos(venueId, group, limit, offset);
        lastResult = result.getMeta();
        return result;
    }


    public Result<LinkGroup> venuesLinks(String id) throws FoursquareApiException {
        Result<LinkGroup> result = foursquareApi.venuesLinks(id);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Todo> venuesMarkTodo(String venuesId, String text) throws FoursquareApiException {
        Result<Todo> result = foursquareApi.venuesMarkTodo(venuesId, text);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Object> venuesFlag(String id, String problem) throws FoursquareApiException {
        Result<Object> result = foursquareApi.venuesFlag(id, problem);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Object> venuesProposeEdit(String id, String name, String address, String crossStreet, String city, String state, String zip, String phone, String ll, String primaryCategoryId) throws FoursquareApiException {
        Result<Object> result = foursquareApi.venuesProposeEdit(id, name, address, crossStreet, city, state, zip, phone, ll, primaryCategoryId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Object> venuesEdit(String id, String name, String address, String crossStreet, String city, String state, String zip, String phone, String ll, String categoryId, String twitter, String description, String url) throws FoursquareApiException {
        Result<Object> result = foursquareApi.venuesEdit(id, name, address, crossStreet, city, state, zip, phone, ll, categoryId, twitter, description, url);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompleteVenue> venuesAdd(String name, String address, String crossStreet, String city, String state, String zip, String phone, String ll, String primaryCategoryId) throws FoursquareApiException {
        Result<CompleteVenue> result = foursquareApi.venuesAdd(name, address, crossStreet, city, state, zip, phone, ll, primaryCategoryId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Category[]> venuesCategories() throws FoursquareApiException {
        Result<Category[]> result = foursquareApi.venuesCategories();
        lastResult = result.getMeta();
        return result;
    }


    public Result<VenuesSearchResult> venuesSearch(String ll, Double llAcc, Double alt, Double altAcc, String query, Integer limit, String intent, String categoryId, String url, String providerId, String linkedId) throws FoursquareApiException {
        Result<VenuesSearchResult> result = foursquareApi.venuesSearch(ll, llAcc, alt, altAcc, query, limit, intent, categoryId, url, providerId, linkedId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<VenuesSearchResult> venuesSearch(Map<String, String> params) throws FoursquareApiException {
        Result<VenuesSearchResult> result = foursquareApi.venuesSearch(params);
        lastResult = result.getMeta();
        return result;
    }


    public Result<VenuesSearchResult> venuesSearch(String near, String query, Integer limit, String intent, String categoryId, String url, String providerId, String linkedId) throws FoursquareApiException {
        Result<VenuesSearchResult> result = foursquareApi.venuesSearch(near, query, limit, intent, categoryId, url, providerId, linkedId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<VenuesAutocompleteResult> venuesSuggestCompletion(String ll, Double llAcc, Double alt, Double altAcc, String query, int limit) throws FoursquareApiException {
        Result<VenuesAutocompleteResult> result = foursquareApi.venuesSuggestCompletion(ll, llAcc, alt, altAcc, query, limit);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompactVenue[]> venuesTrending(String ll, Integer limit, Integer radius) throws FoursquareApiException {
        Result<CompactVenue[]> result = foursquareApi.venuesTrending(ll, limit, radius);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Checkin> checkin(String checkinId, String signature) throws FoursquareApiException {
        Result<Checkin> result = foursquareApi.checkin(checkinId, signature);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Checkin> checkinsAdd(String venueId, String venue, String shout, String broadcast, String ll, Double llAcc, Double alt, Double altAcc) throws FoursquareApiException {
        Result<Checkin> result = foursquareApi.checkinsAdd(venueId, venue, shout, broadcast, ll, llAcc, alt, altAcc);
        lastResult = result.getMeta();
        lastCheckinResult = result.getMeta();
        return result;
    }


    public Result<Checkin[]> checkinsRecent(String ll, Integer limit, Long afterTimestamp) throws FoursquareApiException {
        Result<Checkin[]> result = foursquareApi.checkinsRecent(ll, limit, afterTimestamp);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Comment> checkinsAddComment(String checkinId, String text) throws FoursquareApiException {
        Result<Comment> result = foursquareApi.checkinsAddComment(checkinId, text);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Checkin> checkinsDeleteComment(String checkinId, String commentId) throws FoursquareApiException {
        Result<Checkin> result = foursquareApi.checkinsDeleteComment(checkinId, commentId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompleteTip> tip(String id) throws FoursquareApiException {
        Result<CompleteTip> result = foursquareApi.tip(id);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompleteTip> tipsAdd(String venueId, String text, String url) throws FoursquareApiException {
        Result<CompleteTip> result = foursquareApi.tipsAdd(venueId, text, url);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompleteTip[]> tipsSearch(String ll, Integer limit, Integer offset, String filter, String query) throws FoursquareApiException {
        Result<CompleteTip[]> result = foursquareApi.tipsSearch(ll, limit, offset, filter, query);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Todo> tipsMarkTodo(String tipId) throws FoursquareApiException {
        Result<Todo> result = foursquareApi.tipsMarkTodo(tipId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompleteTip> tipsMarkDone(String tipId) throws FoursquareApiException {
        Result<CompleteTip> result = foursquareApi.tipsMarkDone(tipId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompleteTip> tipsUnmark(String tipId) throws FoursquareApiException {
        Result<CompleteTip> result = foursquareApi.tipsUnmark(tipId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Photo> photo(String id) throws FoursquareApiException {
        Result<Photo> result = foursquareApi.photo(id);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Photo> photosAdd(String checkinId, String tipId, String venueId, String broadcast, String ll, Double llAcc, Double alt, Double altAcc, byte[] data) throws FoursquareApiException {
        Result<Photo> result = foursquareApi.photosAdd(checkinId, tipId, venueId, broadcast, ll, llAcc, alt, altAcc, data);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Setting> settingSet(String settingId, Boolean value) throws FoursquareApiException {
        Result<Setting> result = foursquareApi.settingSet(settingId, value);
        lastResult = result.getMeta();
        return result;
    }


    public Result<Setting> settingsAll() throws FoursquareApiException {
        Result<Setting> result = foursquareApi.settingsAll();
        lastResult = result.getMeta();
        return result;
    }


    public Result<CompleteSpecial> special(String id, String venueId) throws FoursquareApiException {
        Result<CompleteSpecial> result = foursquareApi.special(id, venueId);
        lastResult = result.getMeta();
        return result;
    }


    public Result<SpecialGroup> specialsSearch(String ll, Double llAcc, Double alt, Double altAcc, Integer limit) throws FoursquareApiException {
        Result<SpecialGroup> result = foursquareApi.specialsSearch(ll, llAcc, alt, altAcc, limit);
        lastResult = result.getMeta();
        return result;
    }


    public String getAuthenticationUrl() {
        return foursquareApi.getAuthenticationUrl();
    }


    public void authenticateCode(String code) throws FoursquareApiException {
        foursquareApi.authenticateCode(code);
    }


    public IOHandler getIOHandler() {
        return foursquareApi.getIOHandler();
    }
}
