package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

public class ArtistSimple implements Parcelable {
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public String name;
    public String type;
    public String uri;

    public ArtistSimple() {
    }

    public ArtistSimple(Parcel in) {
        readFromParcel(in);
    }


    public String getName() {
        return name;
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ArtistSimple> CREATOR = new
            Parcelable.Creator<ArtistSimple>() {
                public ArtistSimple createFromParcel(Parcel in) {
                    return new ArtistSimple(in);
                }

                public ArtistSimple[] newArray(int size) {
                    return new ArtistSimple[size];
                }
            };

    public void readFromParcel(Parcel in) {
        name = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
