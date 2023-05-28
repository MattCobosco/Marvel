package core.api.models.DTOs;

import com.google.gson.annotations.SerializedName;

public class CreatorResourceDto extends ResourceDto {
    @SerializedName("role")
    private String role;

    public String getRole() {
        return this.role;
    }
}
