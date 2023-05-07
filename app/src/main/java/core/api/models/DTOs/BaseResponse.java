package core.api.models.DTOs;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
    @SerializedName("code") private int code;
    @SerializedName("status") private String status;
    @SerializedName("copyright") private String copyright;
    @SerializedName("attributionText") private String attributionText;
    @SerializedName("attributionHTML") private String getAttributionHtml;
    @SerializedName("etag") private String etag;

    @SerializedName("data") private T response;

    public BaseResponse() {
    }

    public BaseResponse(final BaseResponse marvelResponse) {
        this.code = marvelResponse.code;
        this.status = marvelResponse.status;
        this.copyright = marvelResponse.copyright;
        this.attributionText = marvelResponse.attributionText;
        this.getAttributionHtml = marvelResponse.getAttributionHtml;
        this.etag = marvelResponse.etag;
    }

    public int getCode() {
        return this.code;
    }

    public String getStatus() {
        return this.status;
    }

    public String getCopyright() {
        return this.copyright;
    }

    public String getAttributionText() {
        return this.attributionText;
    }

    public String getGetAttributionHtml() {
        return this.getAttributionHtml;
    }

    public T getResponse() {
        return this.response;
    }

    public String getEtag() {
        return this.etag;
    }

    public void setResponse(final T response) {
        this.response = response;
    }
}
