package core.api.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SerieRow implements Parcelable {
    private final String id;
    private final String title;
    private final String description;
    private final String thumbnailUrl;

    public SerieRow(String id, String title, String description, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }

    public SerieRow(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.thumbnailUrl = in.readString();
    }

    public static final Parcelable.Creator<SerieRow> CREATOR = new Parcelable.Creator<SerieRow>() {
        @Override
        public SerieRow createFromParcel(Parcel in) {
            return new SerieRow(in);
        }

        @Override
        public SerieRow[] newArray(int size) {
            return new SerieRow[size];
        }
    };

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
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
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.thumbnailUrl);
    }
}
