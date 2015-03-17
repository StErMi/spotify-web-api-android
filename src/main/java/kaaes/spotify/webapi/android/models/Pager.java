package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#paging-object">Paging object model</a>
 *
 * @param <T>
 */
public class Pager<T> implements Parcelable {
    public String href;
    public ArrayList<T> items;
    public int limit;
    public String next;
    public int offset;
    public String previous;
    public int total;

    public Pager() {}

    public Pager(Parcel in) {
        readFromParcel(in);
    }

    public ArrayList<T> getItems() {
        return items;
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Pager> CREATOR = new
            Parcelable.Creator<Pager>() {
                public Pager createFromParcel(Parcel in) {
                    return new Pager(in);
                }

                public Pager[] newArray(int size) {
                    return new Pager[size];
                }
            };

    public void readFromParcel(Parcel in) {
        href = in.readString();
        limit = in.readInt();
        next = in.readString();
        offset = in.readInt();
        previous = in.readString();
        total = in.readInt();
        //items parsed from subclasses (see SavedTrackPager for istance)
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(href);
        dest.writeInt(limit);
        dest.writeString(next);
        dest.writeInt(offset);
        dest.writeString(previous);
        dest.writeInt(total);
        //items parsed from subclasses (see SavedTrackPager for istance)
    }
}
