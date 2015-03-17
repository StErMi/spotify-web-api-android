package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by emanuele on 3/16/15.
 */
public class SavedTrackPager extends Pager<SavedTrack> implements Parcelable {

    public SavedTrackPager(Parcel in) {
        readFromParcel(in);
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<SavedTrackPager> CREATOR = new
            Parcelable.Creator<SavedTrackPager>() {
                public SavedTrackPager createFromParcel(Parcel in) {
                    return new SavedTrackPager(in);
                }

                public SavedTrackPager[] newArray(int size) {
                    return new SavedTrackPager[size];
                }
            };

    public void readFromParcel(Parcel in) {
        super.readFromParcel(in);
        in.readTypedList(items, SavedTrack.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(items);
    }

}
