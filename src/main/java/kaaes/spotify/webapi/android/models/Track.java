package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#track-object-full">Track object model</a>
 */
public class Track extends TrackSimple {
    public AlbumSimple album;
    public Map<String, String> external_ids;
    public Integer popularity;

    public Track(Parcel in) {
        readFromParcel(in);
    }

    public AlbumSimple getAlbum() {
        return album;
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Track> CREATOR = new
            Parcelable.Creator<Track>() {
                public Track createFromParcel(Parcel in) {
                    return new Track(in);
                }

                public Track[] newArray(int size) {
                    return new Track[size];
                }
            };

    public void readFromParcel(Parcel in) {
        super.readFromParcel(in);
        album = in.readParcelable( AlbumSimple.class.getClassLoader() );

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(album, flags);
    }
}