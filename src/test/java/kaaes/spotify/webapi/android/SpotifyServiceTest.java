package kaaes.spotify.webapi.android;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import kaaes.spotify.webapi.android.models.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.*;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedOutput;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class SpotifyServiceTest {

    private SpotifyService mSpotifyService;
    private Client mMockClient;
    private Gson mGson;

    private class MatchesId extends ArgumentMatcher<Request> {

        private final String mId;

        MatchesId(String id) {
            mId = id;
        }

        public boolean matches(Object request) {
            try {
                return ((Request) request).getUrl().contains(URLEncoder.encode(mId, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return false;
            }
        }
    }

    @Before
    public void setUp() {
        mMockClient = mock(Client.class);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(mMockClient)
                .setEndpoint(SpotifyApi.SPOTIFY_WEB_API_ENDPOINT)
                .build();

        mSpotifyService = restAdapter.create(SpotifyService.class);
        mGson = new GsonBuilder().create();
    }

    @Test
    public void shouldGetTrackData() throws IOException {
        String body = TestUtils.readTestData("track.json");
        Track fixture = mGson.fromJson(body, Track.class);

        Response response = TestUtils.getResponseFromModel(fixture, Track.class);
        when(mMockClient.execute(argThat(new MatchesId(fixture.id)))).thenReturn(response);

        Track track = mSpotifyService.getTrack(fixture.id);
        this.compareJSONWithoutNulls(body, track);
    }

    @Test
    public void shouldGetMultipleTrackData() throws IOException {
        String body = TestUtils.readTestData("tracks.json");
        Tracks fixture = mGson.fromJson(body, Tracks.class);

        String ids = "";
        for (int i = 0; i < fixture.tracks.size(); i++) {
            if (i > 0) {
                ids += ",";
            }
            ids += fixture.tracks.get(i).id;
        }

        Response response = TestUtils.getResponseFromModel(fixture, Tracks.class);
        when(mMockClient.execute(argThat(new MatchesId(ids)))).thenReturn(response);

        Tracks tracks = mSpotifyService.getTracks(ids);
        this.compareJSONWithoutNulls(body, tracks);
    }

    @Test
    public void shouldGetAlbumData() throws IOException {
        String body = TestUtils.readTestData("album.json");
        Album fixture = mGson.fromJson(body, Album.class);

        Response response = TestUtils.getResponseFromModel(fixture, Album.class);
        when(mMockClient.execute(argThat(new MatchesId(fixture.id)))).thenReturn(response);

        Album album = mSpotifyService.getAlbum(fixture.id);
        this.compareJSONWithoutNulls(body, album);
    }

    @Test
    public void shouldGetMultipleAlbumData() throws IOException {
        String body = TestUtils.readTestData("albums.json");
        Albums fixture = mGson.fromJson(body, Albums.class);

        String ids = "";
        for (int i = 0; i < fixture.albums.size(); i++) {
            if (i > 0) {
                ids += ",";
            }
            ids += fixture.albums.get(i).id;
        }

        Response response = TestUtils.getResponseFromModel(fixture, Albums.class);
        when(mMockClient.execute(argThat(new MatchesId(ids)))).thenReturn(response);

        Albums albums = mSpotifyService.getAlbums(ids);
        this.compareJSONWithoutNulls(body, albums);
    }

    @Test
    public void shouldGetArtistData() throws IOException {
        String body = TestUtils.readTestData("artist.json");
        Artist fixture = mGson.fromJson(body, Artist.class);

        Response response = TestUtils.getResponseFromModel(fixture, Artist.class);
        when(mMockClient.execute(argThat(new MatchesId(fixture.id)))).thenReturn(response);

        Artist artist = mSpotifyService.getArtist(fixture.id);
        this.compareJSONWithoutNulls(body, artist);
    }

    @Test
    public void shouldGetMultipleArtistData() throws IOException {
        String body = TestUtils.readTestData("artists.json");
        Artists fixture = mGson.fromJson(body, Artists.class);

        String ids = "";
        for (int i = 0; i < fixture.artists.size(); i++) {
            if (i > 0) {
                ids += ",";
            }
            ids += fixture.artists.get(i).id;
        }

        Response response = TestUtils.getResponseFromModel(fixture, Artists.class);
        when(mMockClient.execute(argThat(new MatchesId(ids)))).thenReturn(response);

        Artists artists = mSpotifyService.getArtists(ids);

        this.compareJSONWithoutNulls(body, artists);
    }

    @Test
    public void shouldGetArtistsAlbumsData() throws IOException {
        Type modelType = new TypeToken<Pager<Album>>() {
        }.getType();

        String artistId = "1vCWHaC5f2uS3yhpwWbIA6";
        String body = TestUtils.readTestData("artist-album.json");
        Pager<Album> fixture = mGson.fromJson(body, modelType);

        Response response = TestUtils.getResponseFromModel(fixture, modelType);
        when(mMockClient.execute(argThat(new MatchesId(artistId)))).thenReturn(response);

        Pager<Album> albums = mSpotifyService.getArtistAlbums(artistId);

        this.compareJSONWithoutNulls(body, albums);
    }

    @Test
    public void shouldGetPlaylistData() throws IOException {
        String body = TestUtils.readTestData("playlist-response.json");
        Playlist fixture = mGson.fromJson(body, Playlist.class);

        Response response = TestUtils.getResponseFromModel(fixture, Playlist.class);
        when(mMockClient.execute(isA(Request.class))).thenReturn(response);

        Playlist playlist = mSpotifyService.getPlaylist(fixture.owner.id, fixture.id);
        compareJSONWithoutNulls(body, playlist);
    }

    @Test
    public void shouldGetPlaylistTracks() throws IOException {
        Type modelType = new TypeToken<Pager<PlaylistTrack>>() {
        }.getType();

        String body = TestUtils.readTestData("playlist-tracks.json");
        Pager<PlaylistTrack> fixture = mGson.fromJson(body, modelType);

        Response response = TestUtils.getResponseFromModel(fixture, modelType);
        when(mMockClient.execute(isA(Request.class))).thenReturn(response);

        Pager<PlaylistTrack> playlistTracks = mSpotifyService.getPlaylistTracks("test", "test");
        compareJSONWithoutNulls(body, playlistTracks);
    }

    @Test
    public void shouldGetNewReleases() throws IOException {
        final String countryId = "SE";
        final int limit = 5;

        String body = TestUtils.readTestData("new-releases.json");
        NewReleases fixture = mGson.fromJson(body, NewReleases.class);

        Response response = TestUtils.getResponseFromModel(fixture, NewReleases.class);

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {

                try {
                    return ((Request) argument).getUrl().contains("limit=" + limit) &&
                            ((Request) argument).getUrl().contains("country=" + URLEncoder.encode(countryId, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    return false;
                }
            }
        }))).thenReturn(response);

        Map<String, Object> options = new HashMap<String, Object>();
        options.put(SpotifyService.COUNTRY, countryId);
        options.put(SpotifyService.OFFSET, 0);
        options.put(SpotifyService.LIMIT, limit);
        NewReleases newReleases = mSpotifyService.getNewReleases(options);

        this.compareJSONWithoutNulls(body, newReleases);
    }

    @Test
    public void shouldGetFeaturedPlaylists() throws IOException {
        final String countryId = "SE";
        final String locale = "sv_SE";
        final int limit = 5;

        String body = TestUtils.readTestData("featured-playlists.json");
        FeaturedPlaylists fixture = mGson.fromJson(body, FeaturedPlaylists.class);

        Response response = TestUtils.getResponseFromModel(fixture, FeaturedPlaylists.class);

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {

                try {
                    return ((Request) argument).getUrl().contains("limit=" + limit) &&
                            ((Request) argument).getUrl().contains("country=" + URLEncoder.encode(countryId, "UTF-8")) &&
                            ((Request) argument).getUrl().contains("locale=" + URLEncoder.encode(locale, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    return false;
                }
            }
        }))).thenReturn(response);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(SpotifyService.COUNTRY, countryId);
        map.put(SpotifyService.LOCALE, locale);
        map.put(SpotifyService.OFFSET, 0);
        map.put(SpotifyService.LIMIT, limit);

        FeaturedPlaylists featuredPlaylists = mSpotifyService.getFeaturedPlaylists(map);

        this.compareJSONWithoutNulls(body, featuredPlaylists);
    }

    @Test
    public void shouldGetUserData() throws IOException {
        String body = TestUtils.readTestData("user.json");
        UserSimple fixture = mGson.fromJson(body, UserSimple.class);

        Response response = TestUtils.getResponseFromModel(fixture, UserSimple.class);
        when(mMockClient.execute(argThat(new MatchesId(fixture.id)))).thenReturn(response);

        UserSimple userSimple = mSpotifyService.getUser(fixture.id);
        this.compareJSONWithoutNulls(body, userSimple);
    }

    @Test
    public void shouldGetCurrentUserData() throws IOException {
        String body = TestUtils.readTestData("current-user.json");
        User fixture = mGson.fromJson(body, User.class);

        Response response = TestUtils.getResponseFromModel(fixture, User.class);
        when(mMockClient.execute(Matchers.<Request>any())).thenReturn(response);

        User user = mSpotifyService.getMe();
        this.compareJSONWithoutNulls(body, user);
    }

    @Test
    public void shouldCheckFollowingUsers() throws IOException {
        Type modelType = new TypeToken<List<Boolean>>() {
        }.getType();
        String body = TestUtils.readTestData("follow_is_following_users.json");
        List<Boolean> fixture = mGson.fromJson(body, modelType);

        final String userIds = "thelinmichael,wizzler";

        Response response = TestUtils.getResponseFromModel(fixture, modelType);

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                try {
                    return ((Request) argument).getUrl().contains("type=user") &&
                            ((Request) argument).getUrl().contains("ids=" + URLEncoder.encode(userIds, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    return false;
                }
            }
        }))).thenReturn(response);

        Boolean[] result = mSpotifyService.isFollowingUsers(userIds);
        this.compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldCheckFollowingArtists() throws IOException {
        Type modelType = new TypeToken<List<Boolean>>() {
        }.getType();
        String body = TestUtils.readTestData("follow_is_following_artists.json");
        List<Boolean> fixture = mGson.fromJson(body, modelType);

        final String artistIds = "3mOsjj1MhocRVwOejIZlTi";

        Response response = TestUtils.getResponseFromModel(fixture, modelType);

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                try {
                    return ((Request) argument).getUrl().contains("type=artist") &&
                            ((Request) argument).getUrl().contains("ids=" + URLEncoder.encode(artistIds, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    return false;
                }
            }
        }))).thenReturn(response);

        Boolean[] result = mSpotifyService.isFollowingArtists(artistIds);
        this.compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldGetSearchedTracks() throws IOException {
        String body = TestUtils.readTestData("search-track.json");
        TracksPager fixture = mGson.fromJson(body, TracksPager.class);

        Response response = TestUtils.getResponseFromModel(fixture, TracksPager.class);
        when(mMockClient.execute(isA(Request.class))).thenReturn(response);

        TracksPager tracks = mSpotifyService.searchTracks("Christmas");
        compareJSONWithoutNulls(body, tracks);
    }

    @Test
    public void shouldGetSearchedAlbums() throws IOException {
        String body = TestUtils.readTestData("search-album.json");
        AlbumsPager fixture = mGson.fromJson(body, AlbumsPager.class);

        Response response = TestUtils.getResponseFromModel(fixture, AlbumsPager.class);
        when(mMockClient.execute(isA(Request.class))).thenReturn(response);

        AlbumsPager result = mSpotifyService.searchAlbums("Christmas");
        compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldGetSearchedArtists() throws IOException {
        String body = TestUtils.readTestData("search-artist.json");
        ArtistsPager fixture = mGson.fromJson(body, ArtistsPager.class);

        Response response = TestUtils.getResponseFromModel(fixture, ArtistsPager.class);
        when(mMockClient.execute(isA(Request.class))).thenReturn(response);

        ArtistsPager result = mSpotifyService.searchArtists("Christmas");
        compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldGetSearchedPlaylists() throws IOException {
        String body = TestUtils.readTestData("search-playlist.json");
        PlaylistsPager fixture = mGson.fromJson(body, PlaylistsPager.class);

        Response response = TestUtils.getResponseFromModel(fixture, PlaylistsPager.class);
        when(mMockClient.execute(isA(Request.class))).thenReturn(response);

        PlaylistsPager result = mSpotifyService.searchPlaylists("Christmas");
        compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldGetPlaylistFollowersContains() throws IOException {
        final Type modelType = new TypeToken<List<Boolean>>() {
        }.getType();
        final String body = TestUtils.readTestData("playlist-followers-contains.json");
        final List<Boolean> fixture = mGson.fromJson(body, modelType);

        final Response response = TestUtils.getResponseFromModel(fixture, modelType);

        final String userIds = "thelinmichael,jmperezperez,kaees";

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                try {
                    return ((Request) argument).getUrl()
                            .contains("ids=" + URLEncoder.encode(userIds, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    return false;
                }
            }
        }))).thenReturn(response);

        final String requestPlaylist = TestUtils.readTestData("playlist-response.json");
        final Playlist requestFixture = mGson.fromJson(requestPlaylist, Playlist.class);

        final Boolean[] result = mSpotifyService.areFollowingPlaylist(requestFixture.owner.id, requestFixture.id, userIds);
        this.compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldGetCategories() throws Exception {
        final Type modelType = new TypeToken<CategoriesPager>() {
        }.getType();
        final String body = TestUtils.readTestData("get-categories.json");
        final CategoriesPager fixture = mGson.fromJson(body, modelType);

        final Response response = TestUtils.getResponseFromModel(fixture, modelType);

        final String country = "SE";
        final String locale = "sv_SE";
        final int offset = 1;
        final int limit = 2;

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                String requestUrl = ((Request) argument).getUrl();
                return requestUrl.contains(String.format("limit=%d", limit)) &&
                        requestUrl.contains(String.format("offset=%d", offset)) &&
                        requestUrl.contains(String.format("country=%s", country)) &&
                        requestUrl.contains(String.format("locale=%s", locale));
            }
        }))).thenReturn(response);


        final Map<String, Object> options = new HashMap<String, Object>();
        options.put("offset", offset);
        options.put("limit", limit);
        options.put("country", country);
        options.put("locale", locale);

        final CategoriesPager result = mSpotifyService.getCategories(options);
        this.compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldGetCategory() throws Exception {
        final Type modelType = new TypeToken<Category>() {
        }.getType();
        final String body = TestUtils.readTestData("category.json");
        final Category fixture = mGson.fromJson(body, modelType);

        final Response response = TestUtils.getResponseFromModel(fixture, modelType);

        final String categoryId = "mood";
        final String country = "SE";
        final String locale = "sv_SE";

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                String requestUrl = ((Request) argument).getUrl();
                return requestUrl.contains(String.format("locale=%s", locale)) &&
                        requestUrl.contains(String.format("country=%s", country));

            }
        }))).thenReturn(response);

        final Map<String, Object> options = new HashMap<String, Object>();
        options.put("country", country);
        options.put("locale", locale);

        final Category result = mSpotifyService.getCategory(categoryId, options);
        this.compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldGetPlaylistsForCategory() throws Exception {
        final Type modelType = new TypeToken<PlaylistsPager>() {
        }.getType();
        final String body = TestUtils.readTestData("category-playlist.json");
        final PlaylistsPager fixture = mGson.fromJson(body, modelType);

        final Response response = TestUtils.getResponseFromModel(fixture, modelType);

        final String categoryId = "mood";
        final String country = "SE";
        final int offset = 1;
        final int limit = 2;

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                String requestUrl = ((Request) argument).getUrl();
                return requestUrl.contains(String.format("limit=%d", limit)) &&
                        requestUrl.contains(String.format("offset=%d", offset)) &&
                        requestUrl.contains(String.format("country=%s", country));
            }
        }))).thenReturn(response);

        final Map<String, Object> options = new HashMap<String, Object>();
        options.put("country", country);
        options.put("offset", offset);
        options.put("limit", limit);

        final PlaylistsPager result = mSpotifyService.getPlaylistsForCategory(categoryId, options);
        this.compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldCreatePlaylistUsingBodyMap() throws Exception {
        final Type modelType = new TypeToken<Playlist>() {
        }.getType();
        final String body = TestUtils.readTestData("created-playlist.json");
        final Playlist fixture = mGson.fromJson(body, modelType);

        final Response response = TestUtils.getResponseFromModel(fixture, modelType);

        final String owner = "thelinmichael";
        final String name = "Coolest Playlist";
        final boolean isPublic = true;

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                final Request request = (Request) argument;

                final OutputStream outputStream = new ByteArrayOutputStream();
                final TypedOutput output = request.getBody();
                String body = null;
                try {
                    output.writeTo(outputStream);
                    body = outputStream.toString();
                } catch (IOException e) {
                    fail("Could not read body");
                }


                final String expectedBody = String.format("{\"name\":\"%s\",\"public\":%b}",
                        name, isPublic);

                return request.getUrl().endsWith(String.format("/users/%s/playlists", owner)) &&
                        JSONsContainSameData(expectedBody, body) &&
                        "POST".equals(request.getMethod());
            }
        }))).thenReturn(response);

        final Map<String, Object> options = new HashMap<String, Object>();
        options.put("name", name);
        options.put("public", isPublic);

        final Playlist result = mSpotifyService.createPlaylist(owner, options);
        this.compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldAddTracksToPlaylist() throws Exception {
        final Type modelType = new TypeToken<SnapshotId>() {
        }.getType();
        final String body = TestUtils.readTestData("snapshot-response.json");
        final SnapshotId fixture = mGson.fromJson(body, modelType);

        final Response response = TestUtils.getResponseFromModel(fixture, modelType);

        final String owner = "thelinmichael";
        final String playlistId = "4JPlPnLULieb2WPFKlLiRq";
        final String trackUri1 = "spotify:track:76lT30VRv09h5MQp5snmsb";
        final String trackUri2 = "spotify:track:2KCmalBTv3SiYxvpKrXmr5";
        final int position = 1;

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                final Request request = (Request) argument;

                final OutputStream outputStream = new ByteArrayOutputStream();
                final TypedOutput output = request.getBody();
                String body = null;
                try {
                    output.writeTo(outputStream);
                    body = outputStream.toString();
                } catch (IOException e) {
                    fail("Could not read body");
                }

                final String expectedBody = String.format("{\"uris\":[\"%s\",\"%s\"]}",
                        trackUri1, trackUri2);
                return request.getUrl().endsWith(String.format("/users/%s/playlists/%s/tracks?position=%d",
                        owner, playlistId, position)) &&
                        JSONsContainSameData(expectedBody, body) &&
                        "POST".equals(request.getMethod());
            }
        }))).thenReturn(response);

        final Map<String, Object> options = new HashMap<String, Object>();
        final List<String> trackUris = Arrays.asList(trackUri1, trackUri2);
        options.put("uris", trackUris);

        final Map<String, Object> queryParameters = new HashMap<String, Object>();
        queryParameters.put("position", String.valueOf(position));

        final SnapshotId result = mSpotifyService.addTracksToPlaylist(owner, playlistId, queryParameters, options);
        this.compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldRemoveTracksFromPlaylist() throws Exception {
        final Type modelType = new TypeToken<SnapshotId>() {
        }.getType();
        final String body = TestUtils.readTestData("snapshot-response.json");
        final SnapshotId fixture = mGson.fromJson(body, modelType);
        final Response response = TestUtils.getResponseFromModel(fixture, modelType);

        final String owner = "thelinmichael";
        final String playlistId = "4JPlPnLULieb2WPFKlLiRq";
        final String trackUri1 = "spotify:track:76lT30VRv09h5MQp5snmsb";
        final String trackUri2 = "spotify:track:2KCmalBTv3SiYxvpKrXmr5";

        TracksToRemove ttr = new TracksToRemove();
        TrackToRemove trackObject1 = new TrackToRemove();
        trackObject1.uri = trackUri1;
        TrackToRemove trackObject2 = new TrackToRemove();
        trackObject2.uri = trackUri2;

        ttr.tracks = Arrays.asList(trackObject1, trackObject2);

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                final Request request = (Request) argument;

                final OutputStream outputStream = new ByteArrayOutputStream();
                final TypedOutput output = request.getBody();
                String body = null;
                try {
                    output.writeTo(outputStream);
                    body = outputStream.toString();
                } catch (IOException e) {
                    fail("Could not read body");
                }

                final String expectedBody = String.format("{\"tracks\":[{\"uri\":\"%s\"},{\"uri\":\"%s\"}]}",
                        trackUri1, trackUri2);
                return request.getUrl().endsWith(String.format("/users/%s/playlists/%s/tracks",
                        owner, playlistId)) &&
                        JSONsContainSameData(expectedBody, body) &&
                        "DELETE".equals(request.getMethod());
            }
        }))).thenReturn(response);

        final SnapshotId result = mSpotifyService.removeTracksFromPlaylist(owner, playlistId, ttr);
        this.compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldRemoveTracksFromPlaylistSpecifyingPositions() throws Exception {
        final Type modelType = new TypeToken<SnapshotId>() {
        }.getType();
        final String body = TestUtils.readTestData("snapshot-response.json");
        final SnapshotId fixture = mGson.fromJson(body, modelType);
        final Response response = TestUtils.getResponseFromModel(fixture, modelType);

        final String owner = "thelinmichael";
        final String playlistId = "4JPlPnLULieb2WPFKlLiRq";
        final String trackUri1 = "spotify:track:76lT30VRv09h5MQp5snmsb";
        final String trackUri2 = "spotify:track:2KCmalBTv3SiYxvpKrXmr5";

        TracksToRemoveWithPosition ttrwp = new TracksToRemoveWithPosition();
        TrackToRemoveWithPosition trackObject1 = new TrackToRemoveWithPosition();
        trackObject1.uri = trackUri1;
        trackObject1.positions = Arrays.asList(0,3);
        TrackToRemoveWithPosition trackObject2 = new TrackToRemoveWithPosition();
        trackObject2.uri = trackUri2;
        trackObject2.positions = Arrays.asList(1);

        ttrwp.tracks = Arrays.asList(trackObject1, trackObject2);

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                final Request request = (Request) argument;

                final OutputStream outputStream = new ByteArrayOutputStream();
                final TypedOutput output = request.getBody();
                String body = null;
                try {
                    output.writeTo(outputStream);
                    body = outputStream.toString();
                } catch (IOException e) {
                    fail("Could not read body");
                }

                final String expectedBody = String.format("{\"tracks\":[{\"uri\":\"%s\",\"positions\":[0,3]},{\"uri\":\"%s\",\"positions\":[1]}]}",
                        trackUri1, trackUri2);
                return request.getUrl().endsWith(String.format("/users/%s/playlists/%s/tracks",
                        owner, playlistId)) &&
                        JSONsContainSameData(expectedBody, body) &&
                        "DELETE".equals(request.getMethod());
            }
        }))).thenReturn(response);

        final SnapshotId result = mSpotifyService.removeTracksFromPlaylist(owner, playlistId, ttrwp);
        this.compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldChangePlaylistDetails() throws Exception {
        final Type modelType = new TypeToken<Result>() {}.getType();
        final String body = ""; // Returns empty body
        final Result fixture = mGson.fromJson(body, modelType);

        final Response response = TestUtils.getResponseFromModel(fixture, modelType);

        final String owner = "thelinmichael";
        final String playlistId = "4JPlPnLULieb2WPFKlLiRq";
        final String name = "Changed name";
        final boolean isPublic = false;

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                final Request request = (Request) argument;

                final OutputStream outputStream = new ByteArrayOutputStream();
                final TypedOutput output = request.getBody();
                String body = null;
                try {
                    output.writeTo(outputStream);
                    body = outputStream.toString();
                } catch (IOException e) {
                    fail("Could not read body");
                }

                final String expectedBody = String.format("{\"name\":\"%s\",\"public\":%b}", name, isPublic);
                return request.getUrl().endsWith(String.format("/users/%s/playlists/%s",
                                                               owner, playlistId)) &&
                       JSONsContainSameData(expectedBody, body) &&
                       "PUT".equals(request.getMethod());
            }
        }))).thenReturn(response);

        final Map<String, Object> options = new HashMap<String, Object>();
        options.put("name", name);
        options.put("public", isPublic);

        final Result result = mSpotifyService.changePlaylistDetails(owner, playlistId, options);
        this.compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldFollowAPlaylist() throws Exception {
        final Type modelType = new TypeToken<Result>() {}.getType();
        final String body = ""; // Returns empty body
        final Result fixture = mGson.fromJson(body, modelType);

        final Response response = TestUtils.getResponseFromModel(fixture, modelType);

        final String owner = "thelinmichael";
        final String playlistId = "4JPlPnLULieb2WPFKlLiRq";

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                final Request request = (Request) argument;
                return request.getUrl().endsWith(String.format("/users/%s/playlists/%s/followers",
                        owner, playlistId)) &&
                        "PUT".equals(request.getMethod());
            }
        }))).thenReturn(response);

        final Result result = mSpotifyService.followPlaylist(owner, playlistId);
        this.compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldFollowAPlaylistPrivately() throws Exception {
        final Type modelType = new TypeToken<Result>() {}.getType();
        final String body = ""; // Returns empty body
        final Result fixture = mGson.fromJson(body, modelType);

        final Response response = TestUtils.getResponseFromModel(fixture, modelType);

        final boolean isPublic = false;
        final String owner = "thelinmichael";
        final String playlistId = "4JPlPnLULieb2WPFKlLiRq";

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                final Request request = (Request) argument;
                final OutputStream outputStream = new ByteArrayOutputStream();
                final TypedOutput output = request.getBody();
                String body = null;
                try {
                    output.writeTo(outputStream);
                    body = outputStream.toString();
                } catch (IOException e) {
                    fail("Could not read body");
                }

                final String expectedBody = String.format("{\"public\":%b}", isPublic);
                return request.getUrl().endsWith(String.format("/users/%s/playlists/%s/followers",
                        owner, playlistId)) &&
                        JSONsContainSameData(expectedBody, body) &&
                        "PUT".equals(request.getMethod());
            }
        }))).thenReturn(response);

        PlaylistFollowPrivacy pfp = new PlaylistFollowPrivacy();
        pfp.is_public = isPublic;
        final Result result = mSpotifyService.followPlaylist(owner, playlistId, pfp);
        this.compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldReorderPlaylistsTracks() throws Exception
    {
        final Type modelType = new TypeToken<SnapshotId>() {
        }.getType();
        final String body = TestUtils.readTestData("snapshot-response.json");
        final SnapshotId fixture = mGson.fromJson(body, modelType);

        final Response response = TestUtils.getResponseFromModel(fixture, modelType);

        final String owner = "thelinmichael";
        final String playlistId = "4JPlPnLULieb2WPFKlLiRq";
        final int rangeStart = 2;
        final int rangeLength = 2;
        final int insertBefore = 0;

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                final Request request = (Request) argument;

                final OutputStream outputStream = new ByteArrayOutputStream();
                final TypedOutput output = request.getBody();
                String body = null;
                try {
                    output.writeTo(outputStream);
                    body = outputStream.toString();
                } catch (IOException e) {
                    fail("Could not read body");
                }

                final String expectedBody = String.format("{\"range_start\":%d,\"range_length\":%d,\"insert_before\":%d}",
                        rangeStart, rangeLength, insertBefore);
                return request.getUrl().endsWith(String.format("/users/%s/playlists/%s/tracks",
                        owner, playlistId)) &&
                        JSONsContainSameData(expectedBody, body) &&
                        "PUT".equals(request.getMethod());
            }
        }))).thenReturn(response);

        final Map<String, Object> options = new HashMap<String, Object>();
        options.put("range_start", rangeStart);
        options.put("range_length", rangeLength);
        options.put("insert_before", insertBefore);

        final SnapshotId result = mSpotifyService.reorderPlaylistTracks(owner, playlistId, options);
        this.compareJSONWithoutNulls(body, result);
    }

    /**
     * Compares the mapping fixture <-> object, ignoring NULL fields
     * This is useful to prevent issues with entities such as "Image" in
     * which width and height are not always present, and they result in
     * null values in the Image object
     *
     * @param fixture The JSON to test against
     * @param model   The object to be serialized
     */
    private <T> void compareJSONWithoutNulls(String fixture, T model) {
        JsonParser parser = new JsonParser();

        // Parsing fixture twice gets rid of nulls
        JsonElement fixtureJsonElement = parser.parse(fixture);
        String fixtureWithoutNulls = mGson.toJson(fixtureJsonElement);
        fixtureJsonElement = parser.parse(fixtureWithoutNulls);

        JsonElement modelJsonElement = mGson.toJsonTree(model);

        // We compare JsonElements from fixture
        // with the one created from model. If they're different
        // it means the model is borked
        assertEquals(fixtureJsonElement, modelJsonElement);
    }

    /**
     * Compares two JSON strings if they contain the same data even if the order
     * of the keys differs.
     *
     * @param expected The JSON to test against
     * @param actual   The tested JSON
     * @return true if JSONs contain the same data, false otherwise.
     */
    private boolean JSONsContainSameData(String expected, String actual) {
        JsonParser parser = new JsonParser();
        JsonElement expectedJsonElement = parser.parse(expected);
        JsonElement actualJsonElement = parser.parse(actual);

        return expectedJsonElement.equals(actualJsonElement);
    }
}
