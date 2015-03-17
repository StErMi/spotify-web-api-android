package kaaes.spotify.webapi.android;

import java.util.Map;

import kaaes.spotify.webapi.android.annotations.DELETEWITHBODY;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Albums;
import kaaes.spotify.webapi.android.models.AlbumsPager;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Artists;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.CategoriesPager;
import kaaes.spotify.webapi.android.models.Category;
import kaaes.spotify.webapi.android.models.FeaturedPlaylists;
import kaaes.spotify.webapi.android.models.NewReleases;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.PlaylistFollowPrivacy;
import kaaes.spotify.webapi.android.models.PlaylistTrack;
import kaaes.spotify.webapi.android.models.PlaylistsPager;
import kaaes.spotify.webapi.android.models.Result;
import kaaes.spotify.webapi.android.models.SavedTrack;
import kaaes.spotify.webapi.android.models.SavedTrackPager;
import kaaes.spotify.webapi.android.models.SnapshotId;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import kaaes.spotify.webapi.android.models.TracksPager;
import kaaes.spotify.webapi.android.models.TracksToRemove;
import kaaes.spotify.webapi.android.models.TracksToRemoveWithPosition;
import kaaes.spotify.webapi.android.models.User;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public interface SpotifyService {

    /**
     * The maximum number of objects to return..
     */
    public static final String LIMIT = "limit";

    /**
     * The index of the first playlist to return. Default: 0 (the first object).
     * Use with limit to get the next set of objects (albums, playlists, etc).
     */
    public static final String OFFSET = "offset";

    /**
     * A comma-separated list of keywords that will be used to filter the response.
     * Valid values are: {@code album}, {@code single}, {@code appears_on}, {@code compilation}
     */
    public static final String ALBUM_TYPE = "album_type";

    /**
     * The country: an ISO 3166-1 alpha-2 country code.
     * Limit the response to one particular geographical market.
     * Synonym to {@link #COUNTRY}
     */
    public static final String MARKET = "market";

    /**
     * Same as {@link #MARKET}
     */
    public static final String COUNTRY = "country";

    /**
     * The desired language, consisting of a lowercase ISO 639 language code
     * * and an uppercase ISO 3166-1 alpha-2 country code, joined by an underscore.
     * For example: es_MX, meaning "Spanish (Mexico)".
     */
    public static final String LOCALE = "locale";

    /************
     * Profiles *
     ************/

    /**
     * Get the currently logged in user profile information.
     * The contents of the User object may differ depending on application's scope.
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-current-users-profile/">Get Current User's Profile</a>
     */
    @GET("/me")
    public void getMe(Callback<User> callback);

    /**
     * Get the currently logged in user profile information.
     * The contents of the User object may differ depending on application's scope.
     * @return The current user
     * @see <a href="https://developer.spotify.com/web-api/get-current-users-profile/">Get Current User's Profile</a>
     */
    @GET("/me")
    public User getMe();


    /**
     * Get a user's public profile information.
     * @param userId   The user's User ID
     * @param callback Callback method
     * @see <a href"https://developer.spotify.com/web-api/get-users-profile/">Get User's Public Profile</a>
     */
    @GET("/user/{id}")
    public void getUser(@Path("id") String userId, Callback<User> callback);

    /**
     * Get a user's public profile information.
     * @param userId The user's User ID
     * @return The user's public profile information.
     * @see <a href"https://developer.spotify.com/web-api/get-users-profile/">Get User's Public Profile</a>
     */
    @GET("/user/{id}")
    public User getUser(@Path("id") String userId);


    /*************
     * Playlists *
     *************/

    /**
     * Get a list of the playlists owned or followed by a Spotify user.
     * @param userId   The user's Spotify user ID.
     * @param options  Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-list-users-playlists/">endpoint documentation</a>
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-list-users-playlists/">Get a List of a User’s Playlists</a>
     */
    @GET("/users/{id}/playlists")
    public void getPlaylists(@Path("id") String userId, @QueryMap Map<String, Object> options, Callback<Pager<Playlist>> callback);

    /**
     * Get a list of the playlists owned or followed by a Spotify user.
     * @param userId  The user's Spotify user ID.
     * @param options Optional parameters. For list of supported parameters see
     *                <a href="https://developer.spotify.com/web-api/get-list-users-playlists/">endpoint documentation</a>
     * @return List of user's playlists wrapped in a {@code Pager} object
     * @see <a href="https://developer.spotify.com/web-api/get-list-users-playlists/">Get a List of a User’s Playlists</a>
     */
    @GET("/users/{id}/playlists")
    public Pager<Playlist> getPlaylists(@Path("id") String userId, @QueryMap Map<String, Object> options);

    /**
     * Get a list of the playlists owned or followed by a Spotify user.
     * @param userId   The user's Spotify user ID.
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-list-users-playlists/">Get a List of a User’s Playlists</a>
     */
    @GET("/users/{id}/playlists")
    public void getPlaylists(@Path("id") String userId, Callback<Pager<Playlist>> callback);

    /**
     * Get a list of the playlists owned or followed by a Spotify user.
     * @param userId The user's Spotify user ID.
     * @return List of user's playlists wrapped in a {@code Pager} object
     * @see <a href="https://developer.spotify.com/web-api/get-list-users-playlists/">Get a List of a User’s Playlists</a>
     */
    @GET("/users/{id}/playlists")
    public Pager<Playlist> getPlaylists(@Path("id") String userId);


    /**
     * Get a playlist owned by a Spotify user.
     * @param userId     The user's Spotify user ID.
     * @param playlistId The Spotify ID for the playlist.
     * @param callback   Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-playlist/">Get a Playlist</a>
     */
    @GET("/users/{user_id}/playlists/{playlist_id}")
    public void getPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, Callback<Playlist> callback);

    /**
     * Get a playlist owned by a Spotify user.
     * @param userId     The user's Spotify user ID.
     * @param playlistId The Spotify ID for the playlist.
     * @return Requested Playlist.
     * @see <a href="https://developer.spotify.com/web-api/get-playlist/">Get a Playlist</a>
     */
    @GET("/users/{user_id}/playlists/{playlist_id}")
    public Playlist getPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId);


    /**
     * Get full details of the tracks of a playlist owned by a Spotify user.
     * @param userId     The user's Spotify user ID.
     * @param playlistId The Spotify ID for the playlist.
     * @param options    Optional parameters. For list of supported parameters see
     *                   <a href="https://developer.spotify.com/web-api/get-playlists-tracks/">endpoint documentation</a>
     * @param callback   Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-playlists-tracks/">Get a Playlist’s Tracks</a>
     */
    @GET("/users/{user_id}/playlists/{playlist_id}/tracks")
    public void getPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @QueryMap Map<String, Object> options, Callback<Pager<PlaylistTrack>> callback);

    /**
     * Get full details of the tracks of a playlist owned by a Spotify user.
     * @param userId     The user's Spotify user ID.
     * @param playlistId The Spotify ID for the playlist.
     * @param options    Optional parameters. For list of supported parameters see
     *                   <a href="https://developer.spotify.com/web-api/get-playlists-tracks/">endpoint documentation</a>
     * @see <a href="https://developer.spotify.com/web-api/get-playlists-tracks/">Get a Playlist’s Tracks</a>
     */
    @GET("/users/{user_id}/playlists/{playlist_id}/tracks")
    public Pager<PlaylistTrack> getPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @QueryMap Map<String, Object> options);

    /**
     * Get full details of the tracks of a playlist owned by a Spotify user.
     * @param userId     The user's Spotify user ID.
     * @param playlistId The Spotify ID for the playlist.
     * @param callback   Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-playlists-tracks/">Get a Playlist’s Tracks</a>
     */
    @GET("/users/{user_id}/playlists/{playlist_id}/tracks")
    public void getPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId, Callback<Pager<PlaylistTrack>> callback);

    /**
     * Get full details of the tracks of a playlist owned by a Spotify user.
     * @param userId     The user's Spotify user ID.
     * @param playlistId The Spotify ID for the playlist.
     * @see <a href="https://developer.spotify.com/web-api/get-playlists-tracks/">Get a Playlist’s Tracks</a>
     */
    @GET("/users/{user_id}/playlists/{playlist_id}/tracks")
    public Pager<PlaylistTrack> getPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId);

    /**
     * Create a playlist
     * @param userId   The playlist's owner's User ID
     * @param body     The body parameters
     * @param callback Callback method
     * @return The created playlist
     * @see <a href="https://developer.spotify.com/web-api/create-playlist/">Create a Playlist</a>
     */
    @POST("/users/{user_id}/playlists")
    public Playlist createPlaylist(@Path("user_id") String userId, @Body Map<String, Object> body, Callback<Playlist> callback);

    /**
     * Create a playlist
     * @param userId  The playlist's owner's User ID
     * @param options The body parameters
     * @return The created playlist
     * @see <a href="https://developer.spotify.com/web-api/create-playlist/">Create a Playlist</a>
     */
    @POST("/users/{user_id}/playlists")
    public Playlist createPlaylist(@Path("user_id") String userId, @Body Map<String, Object> options);

    /**
     * Add tracks to a playlist
     * @param userId          The owner of the playlist
     * @param playlistId      The playlist's ID
     * @param queryParameters Query parameters
     * @param body            The body parameters
     * @return A snapshot ID (the version of the playlist)
     * @see <a href="https://developer.spotify.com/web-api/add-tracks-to-playlist/">Add Tracks to a Playlist</a>
     */
    @POST("/users/{user_id}/playlists/{playlist_id}/tracks")
    public SnapshotId addTracksToPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @QueryMap Map<String, Object> queryParameters, @Body Map<String, Object> body);

    /**
     * Add tracks to a playlist
     * @param userId          The owner of the playlist
     * @param playlistId      The playlist's Id
     * @param queryParameters Query parameters
     * @param body            The body parameters
     * @param callback        Callback method
     * @see <a href="https://developer.spotify.com/web-api/add-tracks-to-playlist/">Add Tracks to a Playlist</a>
     */
    @POST("/users/{user_id}/playlists/{playlist_id}/tracks")
    public void addTracksToPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @QueryMap Map<String, Object> queryParameters, @Body Map<String, Object> body, Callback<Pager<PlaylistTrack>> callback);

    /**
     * Remove one or more tracks from a user’s playlist.
     * @param userId         The owner of the playlist
     * @param playlistId     The playlist's Id
     * @param tracksToRemove A list of tracks to remove
     * @param callback       Callback method
     * @see <a href="https://developer.spotify.com/web-api/remove-tracks-playlist/">Remove Tracks from a Playlist</a>
     */
    @DELETEWITHBODY("/users/{user_id}/playlists/{playlist_id}/tracks")
    public void removeTracksFromPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body TracksToRemove tracksToRemove, Callback<SnapshotId> callback);

    /**
     * Remove one or more tracks from a user’s playlist.
     * @param userId         The owner of the playlist
     * @param playlistId     The playlist's Id
     * @param tracksToRemove A list of tracks to remove
     * @return A snapshot ID (the version of the playlist)
     * @see <a href="https://developer.spotify.com/web-api/remove-tracks-playlist/">Remove Tracks from a Playlist</a>
     */
    @DELETEWITHBODY("/users/{user_id}/playlists/{playlist_id}/tracks")
    public SnapshotId removeTracksFromPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body TracksToRemove tracksToRemove);

    /**
     * Remove one or more tracks from a user’s playlist.
     * @param userId                     The owner of the playlist
     * @param playlistId                 The playlist's Id
     * @param tracksToRemoveWithPosition A list of tracks to remove, together with their specific positions
     * @param callback                   Callback method
     * @see <a href="https://developer.spotify.com/web-api/remove-tracks-playlist/">Remove Tracks from a Playlist</a>
     */
    @DELETEWITHBODY("/users/{user_id}/playlists/{playlist_id}/tracks")
    public void removeTracksFromPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body TracksToRemoveWithPosition tracksToRemoveWithPosition, Callback<SnapshotId> callback);

    /**
     * Remove one or more tracks from a user’s playlist.
     * @param userId                     The owner of the playlist
     * @param playlistId                 The playlist's Id
     * @param tracksToRemoveWithPosition A list of tracks to remove, together with their specific positions
     * @return A snapshot ID (the version of the playlist)
     * @see <a href="https://developer.spotify.com/web-api/remove-tracks-playlist/">Remove Tracks from a Playlist</a>
     */
    @DELETEWITHBODY("/users/{user_id}/playlists/{playlist_id}/tracks")
    public SnapshotId removeTracksFromPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body TracksToRemoveWithPosition tracksToRemoveWithPosition);

    /**
     * Replace all the tracks in a playlist, overwriting its existing tracks. This powerful request can be useful for
     * replacing tracks, re-ordering existing tracks, or clearing the playlist.
     * @param userId     The owner of the playlist
     * @param playlistId The playlist's Id
     * @param trackUris  A list of comma-separated track uris
     * @param callback   Callback method
     * @see <a href="https://developer.spotify.com/web-api/replace-playlists-tracks/">Replace a Playlist’s Tracks</a>
     */
    @PUT("/users/{user_id}/playlists/{playlist_id}/tracks")
    public void replaceTracksInPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("uris") String trackUris, Callback<Result> callback);

    /**
     * Replace all the tracks in a playlist, overwriting its existing tracks. This powerful request can be useful for
     * replacing tracks, re-ordering existing tracks, or clearing the playlist.
     * @param userId     The owner of the playlist
     * @param playlistId The playlist's Id
     * @param trackUris  A list of comma-separated track uris
     * @return An empty result
     * @see <a href="https://developer.spotify.com/web-api/replace-playlists-tracks/">Replace a Playlist’s Tracks</a>
     */
    @PUT("/users/{user_id}/playlists/{playlist_id}/tracks")
    public Result replaceTracksInPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("uris") String trackUris);

    /**
     * Change a playlist’s name and public/private state. (The user must, of course, own the playlist.)
     * @param userId     The Spotify user ID of the user who owns the playlist.
     * @param playlistId The playlist's Id
     * @param body       The body parameters. For list of supported parameters see <a href="https://developer.spotify.com/web-api/change-playlist-details/">endpoint documentation</a>
     * @return An empty result
     * @see <a href="https://developer.spotify.com/web-api/change-playlist-details/">Change a Playlist's Details</a>
     */
    @PUT("/users/{user_id}/playlists/{playlist_id}")
    public Result changePlaylistDetails(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body Map<String, Object> body);

    /**
     * Change a playlist’s name and public/private state. (The user must, of course, own the playlist.)
     * @param userId     The Spotify user ID of the user who owns the playlist.
     * @param playlistId The playlist's Id
     * @param body       The body parameters. For list of supported parameters see <a href="https://developer.spotify.com/web-api/change-playlist-details/">endpoint documentation</a>
     * @param callback   Callback method
     * @see <a href="https://developer.spotify.com/web-api/change-playlist-details/">Change a Playlist's Details</a>
     */
    @PUT("/users/{user_id}/playlists/{playlist_id}")
    public void changePlaylistDetails(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body Map<String, Object> body, Callback<Result> callback);

    /**
     * Add the current user as a follower of a playlist.
     * @param userId     The Spotify user ID of the user who owns the playlist.
     * @param playlistId The Spotify ID of the playlist
     * @param callback   Callback method
     * @see <a href="https://developer.spotify.com/web-api/follow-playlist/">Follow a Playlist</a>
     */
    @PUT("/users/{user_id}/playlists/{playlist_id}/followers")
    public void followPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, Callback<Result> callback);

    /**
     * Add the current user as a follower of a playlist.
     * @param userId     The Spotify user ID of the user who owns the playlist.
     * @param playlistId The Spotify ID of the playlist
     * @return An empty result
     * @see <a href="https://developer.spotify.com/web-api/follow-playlist/">Follow a Playlist</a>
     */
    @PUT("/users/{user_id}/playlists/{playlist_id}/followers")
    public Result followPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId);

    /**
     * Add the current user as a follower of a playlist.
     * @param userId                The Spotify user ID of the user who owns the playlist.
     * @param playlistId            The Spotify ID of the playlist
     * @param playlistFollowPrivacy The privacy state of the playlist
     * @param callback              Callback method
     * @see <a href="https://developer.spotify.com/web-api/follow-playlist/">Follow a Playlist</a>
     */
    @PUT("/users/{user_id}/playlists/{playlist_id}/followers")
    public void followPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body PlaylistFollowPrivacy playlistFollowPrivacy, Callback<Result> callback);

    /**
     * Add the current user as a follower of a playlist.
     * @param userId                The Spotify user ID of the user who owns the playlist.
     * @param playlistId            The Spotify ID of the playlist
     * @param playlistFollowPrivacy The privacy state of the playlist
     * @return An empty result
     * @see <a href="https://developer.spotify.com/web-api/follow-playlist/">Follow a Playlist</a>
     */
    @PUT("/users/{user_id}/playlists/{playlist_id}/followers")
    public Result followPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body PlaylistFollowPrivacy playlistFollowPrivacy);

    /**
     * Unfollow a Playlist
     * @param userId      The Spotify user ID of the user who owns the playlist.
     * @param playlistId  The Spotify ID of the playlist
     * @param callback    Callback method
     * @see <a href="https://developer.spotify.com/web-api/unfollow-playlist/">Unfollow a Playlist</a>
     */
    @DELETE("/users/{user_id}/playlists/{playlist_id}/followers")
    public void unfollowPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, Callback<Result> callback);

    /**
     * Unfollow a Playlist
     * @param userId      The Spotify user ID of the user who owns the playlist.
     * @param playlistId  The Spotify ID of the playlist
     * @return An empty result
     * @see <a href="https://developer.spotify.com/web-api/unfollow-playlist/">Unfollow a Playlist</a>
     */
    @DELETE("/users/{user_id}/playlists/{playlist_id}/followers")
    public Result unfollowPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId);

    /**
     * Reorder a Playlist's tracks
     * @param userId The Spotify user ID of the user who owns the playlist.
     * @param playlistId The Spotify ID of the playlist
     * @param body The body parameters. For list of supported parameters see <a href="https://developer.spotify.com/web-api/reorder-playlists-tracks/">endpoint documentation</a>
     * @return A snapshot ID (the version of the playlist)
     * @see <a href="https://developer.spotify.com/web-api/reorder-playlists-tracks/">Reorder a Playlist</a>
     */
    @PUT("/users/{user_id}/playlists/{playlist_id}/tracks")
    public SnapshotId reorderPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body Map<String, Object> body);

    /**
     * Reorder a Playlist's tracks
     * @param userId The Spotify user ID of the user who owns the playlist.
     * @param playlistId The Spotify ID of the playlist
     * @param body The body parameters. For list of supported parameters see <a href="https://developer.spotify.com/web-api/reorder-playlists-tracks/">endpoint documentation</a>
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/reorder-playlists-tracks/">Reorder a Playlist</a>
     */
    @PUT("/users/{user_id}/playlists/{playlist_id}/tracks")
    public void reorderPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body Map<String, Object> body, Callback<SnapshotId> callback);


    /**********
     * Albums *
     **********/


    /**
     * Get Spotify catalog information for a single album.
     * @param albumId  The Spotify ID for the album.
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-album/">Get an Album</a>
     */
    @GET("/albums/{id}")
    public void getAlbum(@Path("id") String albumId, Callback<Album> callback);

    /**
     * Get Spotify catalog information for a single album.
     * @param albumId The Spotify ID for the album.
     * @return Requested album information
     * @see <a href="https://developer.spotify.com/web-api/get-album/">Get an Album</a>
     */
    @GET("/albums/{id}")
    public Album getAlbum(@Path("id") String albumId);

    /**
     * Get Spotify catalog information for a single album.
     * @param albumId  The Spotify ID for the album.
     * @param options  Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-album/">endpoint documentation</a>
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-album/">Get an Album</a>
     */
    @GET("/albums/{id}")
    public void getAlbum(@Path("id") String albumId, @QueryMap Map<String, Object> options, Callback<Album> callback);

    /**
     * Get Spotify catalog information for a single album.
     * @param albumId The Spotify ID for the album.
     * @param options Optional parameters. For list of supported parameters see
     *                <a href="https://developer.spotify.com/web-api/get-album/">endpoint documentation</a>
     * @return Requested album information
     * @see <a href="https://developer.spotify.com/web-api/get-album/">Get an Album</a>
     */
    @GET("/albums/{id}")
    public Album getAlbum(@Path("id") String albumId, @QueryMap Map<String, Object> options);

    /**
     * Get Spotify catalog information for multiple albums identified by their Spotify IDs.
     *
     * @param albumIds A comma-separated list of the Spotify IDs for the albums
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-several-albums/">Get Several Albums</a>
     */
    @GET("/albums")
    public void getAlbums(@Query("ids") String albumIds, Callback<Albums> callback);

    /**
     * Get Spotify catalog information for multiple albums identified by their Spotify IDs.
     * @param albumIds A comma-separated list of the Spotify IDs for the albums
     * @return Object whose key is "albums" and whose value is an array of album objects.
     * @see <a href="https://developer.spotify.com/web-api/get-several-albums/">Get Several Albums</a>
     */
    @GET("/albums")
    public Albums getAlbums(@Query("ids") String albumIds);

    /**
     * Get Spotify catalog information for multiple albums identified by their Spotify IDs.
     *
     * @param albumIds A comma-separated list of the Spotify IDs for the albums
     * @param options Optional parameters. For list of supported parameters see
     *                <a href="https://developer.spotify.com/web-api/get-several-albums/">endpoint documentation</a>
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-several-albums/">Get Several Albums</a>
     */
    @GET("/albums")
    public void getAlbums(@Query("ids") String albumIds, @QueryMap Map<String, Object> options, Callback<Albums> callback);

    /**
     * Get Spotify catalog information for multiple albums identified by their Spotify IDs.
     * @param albumIds A comma-separated list of the Spotify IDs for the albums
     * @param options Optional parameters. For list of supported parameters see
     *                <a href="https://developer.spotify.com/web-api/get-several-albums/">endpoint documentation</a>
     * @return Object whose key is "albums" and whose value is an array of album objects.
     * @see <a href="https://developer.spotify.com/web-api/get-several-albums/">Get Several Albums</a>
     */
    @GET("/albums")
    public Albums getAlbums(@Query("ids") String albumIds, @QueryMap Map<String, Object> options);

    /**
     * Get Spotify catalog information about an album’s tracks.
     * @param albumId The Spotify ID for the album.
     * @return List of simplified album objects wrapped in a Pager object
     * @see <a href="https://developer.spotify.com/web-api/get-albums-tracks/">Get an Album’s Tracks</a>
     */
    @GET("/albums/{id}/tracks")
    public Pager<Track> getAlbumTracks(@Path("id") String albumId);

    /**
     * Get Spotify catalog information about an album’s tracks.
     * @param albumId The Spotify ID for the album.
     * @see <a href="https://developer.spotify.com/web-api/get-albums-tracks/">Get an Album’s Tracks</a>
     */
    @GET("/albums/{id}/tracks")
    public void getAlbumTracks(@Path("id") String albumId, Callback<Pager<Track>> callback);

    /**
     * Get Spotify catalog information about an album’s tracks.
     * @param albumId  The Spotify ID for the album.
     * @param options  Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-albums-tracks/">endpoint documentation</a>
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-albums-tracks/">Get an Album’s Tracks</a>
     */
    @GET("/albums/{id}/tracks")
    public void getAlbumTracks(@Path("id") String albumId, @QueryMap Map<String, Object> options, Callback<Pager<Track>> callback);

    /**
     * Get Spotify catalog information about an album’s tracks.
     * @param albumId The Spotify ID for the album.
     * @param options Optional parameters. For list of supported parameters see
     *                <a href="https://developer.spotify.com/web-api/get-albums-tracks/">endpoint documentation</a>
     * @return List of simplified album objects wrapped in a Pager object
     * @see <a href="https://developer.spotify.com/web-api/get-albums-tracks/">Get an Album’s Tracks</a>
     */
    @GET("/albums/{id}/tracks")
    public Pager<Track> getAlbumTracks(@Path("id") String albumId, @QueryMap Map<String, Object> options);


    /***********
     * Artists *
     ***********/


    /**
     * Get Spotify catalog information for a single artist identified by their unique Spotify ID.
     * @param artistId The Spotify ID for the artist.
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-artist/">Get an Artist</a>
     */
    @GET("/artists/{id}")
    public void getArtist(@Path("id") String artistId, Callback<Artist> callback);

    /**
     * Get Spotify catalog information for a single artist identified by their unique Spotify ID.
     * @param artistId The Spotify ID for the artist.
     * @return Requested artist information
     * @see <a href="https://developer.spotify.com/web-api/get-artist/">Get an Artist</a>
     */
    @GET("/artists/{id}")
    public Artist getArtist(@Path("id") String artistId);

    /**
     * Get Spotify catalog information for several artists based on their Spotify IDs.
     * @param artistIds A comma-separated list of the Spotify IDs for the artists
     * @param callback  Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-several-artists/">Get Several Artists</a>
     */
    @GET("/artists")
    public void getArtists(@Query("ids") String artistIds, Callback<Artists> callback);

    /**
     * Get Spotify catalog information for several artists based on their Spotify IDs.
     * @param artistIds A comma-separated list of the Spotify IDs for the artists
     * @return An object whose key is "artists" and whose value is an array of artist objects.
     * @see <a href="https://developer.spotify.com/web-api/get-several-artists/">Get Several Artists</a>
     */
    @GET("/artists")
    public Artists getArtists(@Query("ids") String artistIds);

    /**
     * Get Spotify catalog information about an artist’s albums.
     * @param artistId The Spotify ID for the artist.
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-artists-albums/">Get an Artist's Albums</a>
     */
    @GET("/artists/{id}/albums")
    public void getArtistAlbums(@Path("id") String artistId, Callback<Pager<Album>> callback);

    /**
     * Get Spotify catalog information about an artist’s albums.
     * @param artistId The Spotify ID for the artist.
     * @return An array of simplified album objects wrapped in a paging object.
     * @see <a href="https://developer.spotify.com/web-api/get-artists-albums/">Get an Artist's Albums</a>
     */
    @GET("/artists/{id}/albums")
    public Pager<Album> getArtistAlbums(@Path("id") String artistId);

    /**
     * Get Spotify catalog information about an artist’s albums.
     * @param artistId The Spotify ID for the artist.
     * @param options  Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-artists-albums/">endpoint documentation</a>
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-artists-albums/">Get an Artist's Albums</a>
     */
    @GET("/artists/{id}/albums")
    public void getArtistAlbums(@Path("id") String artistId, @QueryMap Map<String, Object> options, Callback<Pager<Album>> callback);

    /**
     * Get Spotify catalog information about an artist’s albums.
     * @param artistId The Spotify ID for the artist.
     * @param options  Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-artists-albums/">endpoint documentation</a>
     * @return An array of simplified album objects wrapped in a paging object.
     * @see <a href="https://developer.spotify.com/web-api/get-artists-albums/">Get an Artist's Albums</a>
     */
    @GET("/artists/{id}/albums")
    public Pager<Album> getArtistAlbums(@Path("id") String artistId, @QueryMap Map<String, Object> options);

    /**
     * Get Spotify catalog information about an artist’s top tracks by country.
     * @param artistId The Spotify ID for the artist.
     * @param options  Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-artists-top-tracks/">endpoint documentation</a>
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-artists-top-tracks/">Get an Artist’s Top Tracks</a>
     */
    @GET("/artists/{id}/top-tracks")
    public void getArtistTopTrack(@Path("id") String artistId, @QueryMap Map<String, Object> options, Callback<Pager<Track>> callback);

    /**
     * Get Spotify catalog information about an artist’s top tracks by country.
     * @param artistId The Spotify ID for the artist.
     * @param options  Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-artists-top-tracks/">endpoint documentation</a>
     * @return An object whose key is "tracks" and whose value is an array of track objects.
     * @see <a href="https://developer.spotify.com/web-api/get-artists-top-tracks/">Get an Artist’s Top Tracks</a>
     */
    @GET("/artists/{id}/top-tracks")
    public Pager<Track> getArtistTopTrack(@Path("id") String artistId, @QueryMap Map<String, Object> options);

    /**
     * Get Spotify catalog information about an artist’s top tracks by country.
     * @param artistId The Spotify ID for the artist.
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-artists-top-tracks/">Get an Artist’s Top Tracks</a>
     */
    @GET("/artists/{id}/top-tracks")
    public void getArtistTopTrack(@Path("id") String artistId, Callback<Pager<Track>> callback);

    /**
     * Get Spotify catalog information about an artist’s top tracks by country.
     * @param artistId The Spotify ID for the artist.
     * @return An object whose key is "tracks" and whose value is an array of track objects.
     * @see <a href="https://developer.spotify.com/web-api/get-artists-top-tracks/">Get an Artist’s Top Tracks</a>
     */
    @GET("/artists/{id}/top-tracks")
    public Pager<Track> getArtistTopTrack(@Path("id") String artistId);

    /**
     * Get Spotify catalog information about artists similar to a given artist.
     * @param artistId The Spotify ID for the artist.
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/get-related-artists/">Get an Artist’s Related Artists</a>
     */
    @GET("/artists/{id}/related-artists")
    public void getRelatedArtists(@Path("id") String artistId, Callback<Pager<Artist>> callback);

    /**
     * Get Spotify catalog information about artists similar to a given artist.
     * @param artistId The Spotify ID for the artist.
     * @return An object whose key is "artists" and whose value is an array of artist objects.
     * @see <a href="https://developer.spotify.com/web-api/get-related-artists/">Get an Artist’s Related Artists</a>
     */
    @GET("/artists/{id}/related-artists")
    public Pager<Artist> getRelatedArtists(@Path("id") String artistId);


    /**
     * Tracks
     */


    /**
     * Get Spotify catalog information for a single track identified by their unique Spotify ID.
     * @param trackId The Spotify ID for the track.
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-track/">Get a Track</a>
     */
    @GET("/tracks/{id}")
    public void getTrack(@Path("id") String trackId, Callback<Track> callback);

    /**
     * Get Spotify catalog information for a single track identified by their unique Spotify ID.
     * @param trackId The Spotify ID for the track.
     * @return Requested track information
     * @see <a href="https://developer.spotify.com/web-api/get-track/">Get a Track</a>
     */
    @GET("/tracks/{id}")
    public Track getTrack(@Path("id") String trackId);

    /**
     * Get Spotify catalog information for a single track identified by their unique Spotify ID.
     * @param trackId  The Spotify ID for the track.
     * @param options  Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-track/">endpoint documentation</a>
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-track/">Get a Track</a>
     */
    @GET("/tracks/{id}")
    public void getTrack(@Path("id") String trackId, @QueryMap Map<String, Object> options, Callback<Track> callback);

    /**
     * Get Spotify catalog information for a single track identified by their unique Spotify ID.
     * @param trackId The Spotify ID for the track.
     * @param options Optional parameters. For list of supported parameters see
     *                <a href="https://developer.spotify.com/web-api/get-track/">endpoint documentation</a>
     * @return Requested track information
     * @see <a href="https://developer.spotify.com/web-api/get-track/">Get a Track</a>
     */
    @GET("/tracks/{id}")
    public Track getTrack(@Path("id") String trackId, @QueryMap Map<String, Object> options);

    /**
     * Get Several Tracks
     * @param trackIds A comma-separated list of the Spotify IDs for the tracks
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-several-tracks/">Get Several Tracks</a>
     */
    @GET("/tracks")
    public void getTracks(@Query("ids") String trackIds, Callback<Tracks> callback);

    /**
     * Get Several Tracks
     * @param trackIds A comma-separated list of the Spotify IDs for the tracks
     * @return An object whose key is "tracks" and whose value is an array of track objects.
     * @see <a href="https://developer.spotify.com/web-api/get-several-tracks/">Get Several Tracks</a>
     */
    @GET("/tracks")
    public Tracks getTracks(@Query("ids") String trackIds);

    /**
     * Get Several Tracks
     * @param trackIds A comma-separated list of the Spotify IDs for the tracks
     * @param options  Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-several-tracks/">endpoint documentation</a>
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-several-tracks/">Get Several Tracks</a>
     */
    @GET("/tracks")
    public void getTracks(@Query("ids") String trackIds, @QueryMap Map<String, Object> options, Callback<Tracks> callback);

    /**
     * Get Several Tracks
     * @param trackIds A comma-separated list of the Spotify IDs for the tracks
     * @param options  Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-several-tracks/">endpoint documentation</a>
     * @return An object whose key is "tracks" and whose value is an array of track objects.
     * @see <a href="https://developer.spotify.com/web-api/get-several-tracks/">Get Several Tracks</a>
     */
    @GET("/tracks")
    public Tracks getTracks(@Query("ids") String trackIds, @QueryMap Map<String, Object> options);


    /**
     * Browse
     */

    /**
     * Get a list of Spotify featured playlists (shown, for example, on a Spotify player’s “Browse” tab).
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-list-featured-playlists/">Get a List of Featured Playlists</a>
     */
    @GET("/browse/featured-playlists")
    public void getFeaturedPlaylists(Callback<FeaturedPlaylists> callback);

    /**
     * Get a list of Spotify featured playlists (shown, for example, on a Spotify player’s “Browse” tab).
     * @return A FeaturedPlaylists object with the featured playlists
     * @see <a href="https://developer.spotify.com/web-api/get-list-featured-playlists/">Get a List of Featured Playlists</a>
     */
    @GET("/browse/featured-playlists")
    public FeaturedPlaylists getFeaturedPlaylists();

    /**
     * Get a list of Spotify featured playlists (shown, for example, on a Spotify player’s “Browse” tab).
     * @param options Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-list-featured-playlists/">endpoint documentation</a>
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-list-featured-playlists/">Get a List of Featured Playlists</a>
     */
    @GET("/browse/featured-playlists")
    public void getFeaturedPlaylists(@QueryMap Map<String, Object> options, Callback<FeaturedPlaylists> callback);

    /**
     * Get a list of Spotify featured playlists (shown, for example, on a Spotify player’s “Browse” tab).
     * @param options Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-list-featured-playlists/">endpoint documentation</a>
     * @return n FeaturedPlaylists object with the featured playlists
     * @see <a href="https://developer.spotify.com/web-api/get-list-featured-playlists/">Get a List of Featured Playlists</a>
     */
    @GET("/browse/featured-playlists")
    public FeaturedPlaylists getFeaturedPlaylists(@QueryMap Map<String, Object> options);

    /**
     * Get a list of new album releases featured in Spotify (shown, for example, on a Spotify player’s “Browse” tab).
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-list-new-releases/">Get a List of New Releases</a>
     */
    @GET("/browse/new-releases")
    public void getNewReleases(Callback<NewReleases> callback);

    /**
     * Get a list of new album releases featured in Spotify (shown, for example, on a Spotify player’s “Browse” tab).
     * @return A NewReleases object with the new album releases
     * @see <a href="https://developer.spotify.com/web-api/get-list-new-releases/">Get a List of New Releases</a>
     */
    @GET("/browse/new-releases")
    public NewReleases getNewReleases();

    /**
     * Get a list of new album releases featured in Spotify (shown, for example, on a Spotify player’s “Browse” tab).
     * @param options Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-list-new-releases/">endpoint documentation</a>
     * @param callback Callback method
     * @see <a href="https://developer.spotify.com/web-api/get-list-new-releases/">Get a List of New Releases</a>
     */
    @GET("/browse/new-releases")
    public void getNewReleases(@QueryMap Map<String, Object> options, Callback<NewReleases> callback);

    /**
     * Get a list of new album releases featured in Spotify (shown, for example, on a Spotify player’s “Browse” tab).
     * @param options Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-list-new-releases/">endpoint documentation</a>
     * @return A NewReleases object with the new album releases
     * @see <a href="https://developer.spotify.com/web-api/get-list-new-releases/">Get a List of New Releases</a>
     */
    @GET("/browse/new-releases")
    public NewReleases getNewReleases(@QueryMap Map<String, Object> options);

    /**
     * Retrieve Spotify categories. Categories used to tag items in
     * Spotify (on, for example, the Spotify player’s “Browse” tab).
     *
     * @param options  Optional parameters.
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/get-list-categories/">Get a List of Categories</a>
     */
    @GET("/browse/categories")
    public void getCategories(@QueryMap Map<String, Object> options, Callback<CategoriesPager> callback);

    /**
     * Retrieve Spotify categories. Categories used to tag items in
     * Spotify (on, for example, the Spotify player’s “Browse” tab).
     *
     * @param options Optional parameters.
     * @return A paging object containing categories.
     * @see <a href="https://developer.spotify.com/web-api/get-list-categories/">Get a List of Categories</a>
     */
    @GET("/browse/categories")
    public CategoriesPager getCategories(@QueryMap Map<String, Object> options);

    /**
     * Retrieve a Spotify category.
     *
     * @param categoryId The category's ID.
     * @param options    Optional parameters.
     * @param callback   Callback method.
     * @see <a href="https://developer.spotify.com/web-api/get-category/">Get a Spotify Category</a>
     */
    @GET("/browse/categories/{category_id}")
    public void getCategory(@Path("category_id") String categoryId, @QueryMap Map<String, Object> options, Callback<Category> callback);

    /**
     * Retrieve a Spotify category.
     *
     * @param categoryId The category's ID.
     * @param options    Optional parameters.
     * @return A Spotify category.
     * @see <a href="https://developer.spotify.com/web-api/get-category/">Get a Spotify Category</a>
     */
    @GET("/browse/categories/{category_id}")
    public Category getCategory(@Path("category_id") String categoryId, @QueryMap Map<String, Object> options);

    /**
     * Retrieve playlists for a Spotify Category.
     *
     * @param categoryId The category's ID.
     * @param options    Optional parameters.
     * @param callback   Callback method.
     * @see <a href="https://developer.spotify.com/web-api/get-categorys-playlists/">Get playlists for a Spotify Category</a>
     */
    @GET("/browse/categories/{category_id}/playlists")
    public void getPlaylistsForCategory(@Path("category_id") String categoryId, @QueryMap Map<String, Object> options, Callback<PlaylistsPager> callback);

    /**
     * Retrieve playlists for a Spotify Category.
     *
     * @param categoryId The category's ID.
     * @param options    Optional parameters.
     * @return Playlists for a Spotify Category.
     * @see <a href="https://developer.spotify.com/web-api/get-categorys-playlists/">Get playlists for a Spotify Category</a>
     */
    @GET("/browse/categories/{category_id}/playlists")
    public PlaylistsPager getPlaylistsForCategory(@Path("category_id") String categoryId, @QueryMap Map<String, Object> options);

    /**
     * Library / Your Music
     */

    /**
     * Get a list of the songs saved in the current Spotify user’s “Your Music” library.
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/get-users-saved-tracks/">Get a User’s Saved Tracks</a>
     */
    @GET("/me/tracks")
    public void getMySavedTracks(Callback<SavedTrackPager> callback);

    /**
     * Get a list of the songs saved in the current Spotify user’s “Your Music” library.
     * @return A paginated list of saved tracks
     * @see <a href="https://developer.spotify.com/web-api/get-users-saved-tracks/">Get a User’s Saved Tracks</a>
     */
    @GET("/me/tracks")
    public SavedTrackPager getMySavedTracks();

    /**
     * Get a list of the songs saved in the current Spotify user’s “Your Music” library.
     * @param options Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-users-saved-tracks/">endpoint documentation</a>
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/get-users-saved-tracks/">Get a User’s Saved Tracks</a>
     */
    @GET("/me/tracks")
    public void getMySavedTracks(@QueryMap Map<String, Object> options, Callback<SavedTrackPager> callback);

    /**
     * Get a list of the songs saved in the current Spotify user’s “Your Music” library.
     * @param options Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/get-users-saved-tracks/">endpoint documentation</a>
     * @return A paginated list of saved tracks
     * @see <a href="https://developer.spotify.com/web-api/get-users-saved-tracks/">Get a User’s Saved Tracks</a>
     */
    @GET("/me/tracks")
    public SavedTrackPager getMySavedTracks(@QueryMap Map<String, Object> options);

    /**
     * Check if one or more tracks is already saved in the current Spotify user’s “Your Music” library.
     * @param ids      A comma-separated list of the Spotify IDs for the tracks
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/check-users-saved-tracks/">Check User’s Saved Tracks</a>
     */
    @GET("/me/tracks/contains")
    public void containsMySavedTracks(@Query("ids") String ids, Callback<boolean[]> callback);

    /**
     * Check if one or more tracks is already saved in the current Spotify user’s “Your Music” library.
     * @param ids A comma-separated list of the Spotify IDs for the tracks
     * @return An array with boolean values that indicate whether the tracks are in the current Spotify user’s “Your Music” library.
     * @see <a href="https://developer.spotify.com/web-api/check-users-saved-tracks/">Check User’s Saved Tracks</a>
     */
    @GET("/me/tracks/contains")
    public Boolean[] containsMySavedTracks(@Query("ids") String ids);

    /**
     * Save one or more tracks to the current user’s “Your Music” library.
     * @param ids      A comma-separated list of the Spotify IDs for the tracks
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/save-tracks-user/">Save Tracks for User</a>
     */
    @PUT("/me/tracks")
    public void addToMySavedTracks(@Query("ids") String ids, Callback<Object> callback);

    /**
     * Save one or more tracks to the current user’s “Your Music” library.
     * @param ids A comma-separated list of the Spotify IDs for the tracks
     * @return An empty result
     * @see <a href="https://developer.spotify.com/web-api/save-tracks-user/">Save Tracks for User</a>
     */
    @PUT("/me/tracks")
    public Result addToMySavedTracks(@Query("ids") String ids);

    /**
     * Remove one or more tracks from the current user’s “Your Music” library.
     * @param ids      A comma-separated list of the Spotify IDs for the tracks
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/remove-tracks-user/">Remove User’s Saved Tracks</a>
     */
    @DELETE("/me/tracks")
    public void removeFromMySavedTracks(@Query("ids") String ids, Callback<Object> callback);

    /**
     * Remove one or more tracks from the current user’s “Your Music” library.
     * @param ids A comma-separated list of the Spotify IDs for the tracks
     * @return An empty result
     * @see <a href="https://developer.spotify.com/web-api/remove-tracks-user/">Remove User’s Saved Tracks</a>
     */
    @DELETE("/me/tracks")
    public Result removeFromMySavedTracks(@Query("ids") String ids);


    /**
     * Follow
     */


    /**
     * Add the current user as a follower of one or more Spotify users.
     * @param ids      A comma-separated list of the Spotify IDs for the users
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/follow-artists-users/">Follow Artists or Users</a>
     */
    @PUT("/me/following?type=user")
    public void followUsers(@Query("ids") String ids, Callback<Object> callback);

    /**
     * Add the current user as a follower of one or more Spotify users.
     * @param ids A comma-separated list of the Spotify IDs for the users
     * @return An empty result
     * @see <a href="https://developer.spotify.com/web-api/follow-artists-users/">Follow Artists or Users</a>
     */
    @PUT("/me/following?type=user")
    public Result followUsers(@Query("ids") String ids);

    /**
     * Add the current user as a follower of one or more artists.
     * @param ids      A comma-separated list of the Spotify IDs for the artists
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/follow-artists-users/">Follow Artists or Users</a>
     */
    @PUT("/me/following?type=artist")
    public void followArtists(@Query("ids") String ids, Callback<Object> callback);

    /**
     * Add the current user as a follower of one or more Spotify artists.
     * @param ids A comma-separated list of the Spotify IDs for the artists
     * @return An empty result
     * @see <a href="https://developer.spotify.com/web-api/follow-artists-users/">Follow Artists or Users</a>
     */
    @PUT("/me/following?type=artist")
    public Result followArtists(@Query("ids") String ids);

    /**
     * Remove the current user as a follower of one or more Spotify users.
     * @param ids      A comma-separated list of the Spotify IDs for the users
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/unfollow-artists-users/">Unfollow Artists or Users</a>
     */
    @DELETE("/me/following?type=user")
    public void unfollowUsers(@Query("ids") String ids, Callback<Object> callback);

    /**
     * Remove the current user as a follower of one or more Spotify users.
     * @param ids A comma-separated list of the Spotify IDs for the users
     * @return An empty result
     * @see <a href="https://developer.spotify.com/web-api/unfollow-artists-users/">Unfollow Artists or Users</a>
     */
    @DELETE("/me/following?type=user")
    public Result unfollowUsers(@Query("ids") String ids);

    /**
     * Remove the current user as a follower of one or more Spotify artists.
     * @param ids      A comma-separated list of the Spotify IDs for the artists
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/unfollow-artists-users/">Unfollow Artists or Users</a>
     */
    @DELETE("/me/following?type=artist")
    public void unfollowArtists(@Query("ids") String ids, Callback<Object> callback);

    /**
     * Remove the current user as a follower of one or more Spotify artists.
     * @param ids A comma-separated list of the Spotify IDs for the artists
     * @return An empty result
     * @see <a href="https://developer.spotify.com/web-api/unfollow-artists-users/">Unfollow Artists or Users</a>
     */
    @DELETE("/me/following?type=artist")
    public Result unfollowArtists(@Query("ids") String ids);

    /**
     * Check to see if the current user is following one or more other Spotify users.
     * @param ids      A comma-separated list of the Spotify IDs for the users
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/check-current-user-follows/">Check if Current User Follows Artists or Users</a>
     */
    @GET("/me/following/contains?type=user")
    public void isFollowingUsers(@Query("ids") String ids, Callback<boolean[]> callback);

    /**
     * Check to see if the current user is following one or more other Spotify users.
     * @param ids A comma-separated list of the Spotify IDs for the users
     * @return An array with boolean values indicating whether the users are followed
     * @see <a href="https://developer.spotify.com/web-api/check-current-user-follows/">Check if Current User Follows Artists or Users</a>
     */
    @GET("/me/following/contains?type=user")
    public Boolean[] isFollowingUsers(@Query("ids") String ids);

    /**
     * Check to see if the current user is following one or more other Spotify artists.
     * @param ids      A comma-separated list of the Spotify IDs for the artists
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/check-current-user-follows/">Check if Current User Follows Artists or Users</a>
     */
    @GET("/me/following/contains?type=artist")
    public void isFollowingArtists(@Query("ids") String ids, Callback<boolean[]> callback);

    /**
     * Check to see if the current user is following one or more other Spotify artists.
     * @param ids A comma-separated list of the Spotify IDs for the artists
     * @return An array with boolean values indicating whether the artists are followed
     * @see <a href="https://developer.spotify.com/web-api/check-current-user-follows/">Check if Current User Follows Artists or Users</a>
     */
    @GET("/me/following/contains?type=artist")
    public Boolean[] isFollowingArtists(@Query("ids") String ids);

    /**
     * Check to see if one or more Spotify users are following a specified playlist.
     * @param userId     The Spotify user ID of the person who owns the playlist.
     * @param playlistId The Spotify ID of the playlist.
     * @param ids        A comma-separated list of the Spotify IDs for the users
     * @return An array with boolean values indicating whether the playlist is followed by the users
     * @see <a href="https://developer.spotify.com/web-api/check-user-following-playlist/">Check if Users Follow a Playlist</a>
     */
    @GET("/users/{user_id}/playlists/{playlist_id}/followers/contains")
    public Boolean[] areFollowingPlaylist(@Path("user_id") String userId,
                                          @Path("playlist_id") String playlistId,
                                          @Query("ids") String ids);

    /**
     * Check to see if one or more Spotify users are following a specified playlist.
     * @param userId     The Spotify user ID of the person who owns the playlist.
     * @param playlistId The Spotify ID of the playlist.
     * @param ids        A comma-separated list of the Spotify IDs for the users
     * @param callback   Callback method.
     * @see <a href="https://developer.spotify.com/web-api/check-user-following-playlist/">Check if Users Follow a Playlist</a>
     */
    @GET("/users/{user_id}/playlists/{playlist_id}/followers/contains")
    public void areFollowingPlaylist(@Path("user_id") String userId,
                                     @Path("playlist_id") String playlistId,
                                     @Query("ids") String ids, Callback<boolean[]> callback);


    /**
     * Search
     */


    /**
     * Get Spotify catalog information about tracks that match a keyword string.
     * @param q        The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=track")
    public void searchTracks(@Query("q") String q, Callback<TracksPager> callback);

    /**
     * Get Spotify catalog information about tracks that match a keyword string.
     * @param q  The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @return A paginated list of results
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=track")
    public TracksPager searchTracks(@Query("q") String q);

    /**
     * Get Spotify catalog information about tracks that match a keyword string.
     * @param q       The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @param options Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/search-item/">endpoint documentation</a>
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=track")
    public void searchTracks(@Query("q") String q, @QueryMap Map<String, Object> options, Callback<TracksPager> callback);

    /**
     * Get Spotify catalog information about tracks that match a keyword string.
     * @param q         The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @param options   Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/search-item/">endpoint documentation</a>
     * @return A paginated list of results
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=track")
    public TracksPager searchTracks(@Query("q") String q, @QueryMap Map<String, Object> options);

    /**
     * Get Spotify catalog information about artists that match a keyword string.
     * @param q        The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=artist")
    public void searchArtists(@Query("q") String q, Callback<ArtistsPager> callback);

    /**
     * Get Spotify catalog information about artists that match a keyword string.
     * @param q The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @return A paginated list of results
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=artist")
    public ArtistsPager searchArtists(@Query("q") String q);

    /**
     * Get Spotify catalog information about artists that match a keyword string.
     * @param q       The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @param options Optional parameters. For list of supported parameters see
     *                <a href="https://developer.spotify.com/web-api/search-item/">endpoint documentation</a>
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=artist")
    public void searchArtists(@Query("q") String q, @QueryMap Map<String, Object> options, Callback<ArtistsPager> callback);

    /**
     * Get Spotify catalog information about artists that match a keyword string.
     * @param q       The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @param options Optional parameters. For list of supported parameters see
     *                <a href="https://developer.spotify.com/web-api/search-item/">endpoint documentation</a>
     * @return A paginated list of results
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=artist")
    public ArtistsPager searchArtists(@Query("q") String q, @QueryMap Map<String, Object> options);

    /**
     * Get Spotify catalog information about albums that match a keyword string.
     * @param q        The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=album")
    public void searchAlbums(@Query("q") String q, Callback<AlbumsPager> callback);

    /**
     * Get Spotify catalog information about albums that match a keyword string.
     * @param q The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @return A paginated list of results
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=album")
    public AlbumsPager searchAlbums(@Query("q") String q);

    /**
     * Get Spotify catalog information about albums that match a keyword string.
     * @param q       The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @param options Optional parameters. For list of supported parameters see
     *                <a href="https://developer.spotify.com/web-api/search-item/">endpoint documentation</a>
     * @param callback Callback method.
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=album")
    public void searchAlbums(@Query("q") String q, @QueryMap Map<String, Object> options, Callback<AlbumsPager> callback);

    /**
     * Get Spotify catalog information about albums that match a keyword string.
     * @param q       The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @param options Optional parameters. For list of supported parameters see
     *                <a href="https://developer.spotify.com/web-api/search-item/">endpoint documentation</a>
     * @return A paginated list of results
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=album")
    public AlbumsPager searchAlbums(@Query("q") String q, @QueryMap Map<String, Object> options);

    /**
     * Get Spotify catalog information about playlists that match a keyword string.
     * @param q         The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @return A paginated list of results
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=playlist")
    public void searchPlaylists(@Query("q") String q, Callback<PlaylistsPager> callback);

    /**
     * Get Spotify catalog information about playlists that match a keyword string.
     * @param q         The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @return A paginated list of results
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=playlist")
    public PlaylistsPager searchPlaylists(@Query("q") String q);

    /**
     * Get Spotify catalog information about playlists that match a keyword string.
     * @param q         The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @param options   Optional parameters. For list of supported parameters see
     *                 <a href="https://developer.spotify.com/web-api/search-item/">endpoint documentation</a>
     * @param callback  Callback method.
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=playlist")
    public void searchPlaylists(@Query("q") String q, @QueryMap Map<String, Object> options, Callback<PlaylistsPager> callback);

    /**
     * Get Spotify catalog information about playlists that match a keyword string.
     * @param q       The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * @param options Optional parameters. For list of supported parameters see
     *                <a href="https://developer.spotify.com/web-api/search-item/">endpoint documentation</a>
     * @return A paginated list of results
     * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item</a>
     */
    @GET("/search?type=playlist")
    public PlaylistsPager searchPlaylists(@Query("q") String q, @QueryMap Map<String, Object> options);
}
