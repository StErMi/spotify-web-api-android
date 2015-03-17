package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#artist-object-full">Artist object model</a>
 */
public class Artist extends ArtistSimple {
    public Followers followers;
    public List<String> genres;
    public List<Image> images;
    public Integer popularity;

    public Artist(Parcel in) {
        readFromParcel(in);
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Artist> CREATOR = new
            Parcelable.Creator<Artist>() {
                public Artist createFromParcel(Parcel in) {
                    return new Artist(in);
                }

                public Artist[] newArray(int size) {
                    return new Artist[size];
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