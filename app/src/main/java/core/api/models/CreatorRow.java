package core.api.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CreatorRow implements Parcelable {
    private final String id;
    private final String fullName;
    private final String thumbnailUrl;

    public CreatorRow(String id, String fullName, String thumbnailUrl) {
        this.id = id;
        this.fullName = fullName;
        this.thumbnailUrl = thumbnailUrl;
    }

    public CreatorRow(Parcel in) {
        this.id = in.readString();
        this.fullName = in.readString();
        this.thumbnailUrl = in.readString();
    }

    public static final Parcelable.Creator<CreatorRow> CREATOR = new Parcelable.Creator<CreatorRow>() {
        @Override
        public CreatorRow createFromParcel(Parcel in) {
            return new CreatorRow(in);
        }

        @Override
        public CreatorRow[] newArray(int size) {
            return new CreatorRow[size];
        }
    };

    public String getId() {
        return this.id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(this.id);
        dest.writeString(this.fullName);
        dest.writeString(this.thumbnailUrl);
    }
}
