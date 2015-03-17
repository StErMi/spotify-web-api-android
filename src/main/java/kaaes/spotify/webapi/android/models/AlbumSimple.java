package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;

public class AlbumSimple implements Parcelable {
    public String album_type;
    public List<String> available_markets;
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public List<Image> images;
    public String name;
    public String type;
    public String uri;

    public AlbumSimple() {
    }

    public AlbumSimple(Parcel in) {
        readFromParcel(in);
    }

    public List<Image> getImages() {
        return images;
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<AlbumSimple> CREATOR = new
            Parcelable.Creator<AlbumSimple>() {
                public AlbumSimple createFromParcel(Parcel in) {
                    return new AlbumSimple(in);
                }

                public AlbumSimple[] newArray(int size) {
                    return new AlbumSimple[size];
                }
            };

    public void readFromParcel(Parcel in) {
        name = in.readString();
        in.readTypedList(images, Image.CREATOR);

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( name );
        dest.writeTypedList( images );
    }
}
