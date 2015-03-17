package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#image-object">Image object model</a>
 */
public class Image implements Parcelable {
    public Integer width;
    public Integer height;
    public String url;

    public Image(Parcel in) {
        readFromParcel(in);
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Image> CREATOR = new
            Parcelable.Creator<Image>() {
                public Image createFromParcel(Parcel in) {
                    return new Image(in);
                }

                public Image[] newArray(int size) {
                    return new Image[size];
                }
            };

    public void readFromParcel(Parcel in) {
        width = in.readInt();
        height = in.readInt();
        url = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( width );
        dest.writeInt( height );
        dest.writeString( url );
    }
}
