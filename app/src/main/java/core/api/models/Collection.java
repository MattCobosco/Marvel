package core.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Collection<T> {
    @SerializedName("offset") private int offset;
    @SerializedName("limit") private int limit;
    @SerializedName("total") private int total;
    @SerializedName("count") private int count;
    @SerializedName("results") private final List<T> results = new ArrayList<>();

    public int getOffset() {
        return this.offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public int getTotal() {
        return this.total;
    }

    public int getCount() {
        return this.count;
    }

    protected List<T> getResults() {
        return this.results;
    }
}
