package core.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CharacterRow implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String thumbnailUrl;

    public CharacterRow(String id, String name, String description, String thumbnailUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }

    protected CharacterRow(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        thumbnailUrl = in.readString();
    }

    public static final Creator<CharacterRow> CREATOR = new Creator<CharacterRow>() {
        @Override
        public CharacterRow createFromParcel(Parcel in) {
            return new CharacterRow(in);
        }

        @Override
        public CharacterRow[] newArray(int size) {
            return new CharacterRow[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(thumbnailUrl);
    }
}