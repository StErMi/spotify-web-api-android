package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#saved-track-object">Saved track object model</a>
 */
public class SavedTrack implements Parcelable {
    public String added_at;
    public Track track;

    public SavedTrack(Parcel in) {
        readFromParcel(in);
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<SavedTrack> CREATOR = new
            Parcelable.Creator<SavedTrack>() {
                public SavedTrack createFromParcel(Parcel in) {
                    return new SavedTrack(in);
                }

                public SavedTrack[] newArray(int size) {
                    return new SavedTrack[size];
                }
            };

    public void readFromParcel(Parcel in) {
        track = in.readParcelable( Track.class.getClassLoader() );

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(track, flags);
    }

}
