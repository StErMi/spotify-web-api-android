package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#album-object-full">Album object model</a>
 */
public class Album extends AlbumSimple implements Parcelable {
    public List<ArtistSimple> artists;
    public List<Copyright> copyrights;
    public Map<String, String> external_ids;
    public List<String> genres;
    public Integer popularity;
    public String release_date;
    public String release_date_precision;
    public Pager<TrackSimple> tracks;

    public Album(Parcel in) {
        readFromParcel(in);
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Album> CREATOR = new
            Parcelable.Creator<Album>() {
                public Album createFromParcel(Parcel in) {
                    return new Album(in);
                }

                public Album[] newArray(int size) {
                    return new Album[size];
                }
            };

    public void readFromParcel(Parcel in) {
        super.readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}