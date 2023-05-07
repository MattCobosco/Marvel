package core.api.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CharacterRow implements Parcelable {
    private final String id;
    private final String name;
    private final String description;
    private final String thumbnailUrl;

    public CharacterRow(final String id, final String name, final String description, final String thumbnailUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }

    protected CharacterRow(final Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.thumbnailUrl = in.readString();
    }

    public static final Parcelable.Creator<CharacterRow> CREATOR = new Parcelable.Creator<CharacterRow>() {
        @Override
        public CharacterRow createFromParcel(final Parcel in) {
            return new CharacterRow(in);
        }

        @Override
        public CharacterRow[] newArray(final int size) {
            return new CharacterRow[size];
        }
    };

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
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
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.thumbnailUrl);
    }
}