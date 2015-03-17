package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

public class TrackSimple implements Parcelable {

    public List<ArtistSimple> artists;
    public List<String> available_markets;
    public Boolean is_playable;
    public LinkedTrack linked_from;
    public int disc_number;
    public long duration_ms;
    public boolean explicit;
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public String name;
    public String preview_url;
    public int track_number;
    public String type;
    public String uri;

    public TrackSimple() {
    }

    public TrackSimple(Parcel in) {
        readFromParcel(in);
    }

    public String getName() {
        return name;
    }

    public String getArtistJoinedName() {
        StringBuilder artistJName = new StringBuilder();
        for( int index = 0; index < artists.size(); index++ ) {
            artistJName.append(artists.get(index).getName());
            if( index < artists.size()-1 )
                artistJName.append(", ");

        }
        return artistJName.toString();
    }

    public String getID() {
        if(TextUtils.isEmpty(uri)) {
            return null;
        } else {
            String[] splitted = uri.split(":");
            return splitted[splitted.length-1];
        }
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<TrackSimple> CREATOR = new
            Parcelable.Creator<TrackSimple>() {
                public TrackSimple createFromParcel(Parcel in) {
                    return new TrackSimple(in);
                }

                public TrackSimple[] newArray(int size) {
                    return new TrackSimple[size];
                }
            };

    public void readFromParcel(Parcel in) {
        uri = in.readString();
        name = in.readString();
        duration_ms = in.readLong();
        in.readTypedList(artists, ArtistSimple.CREATOR);

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( uri );
        dest.writeString(name);
        dest.writeLong(duration_ms);
        dest.writeTypedList( artists );
    }
}
