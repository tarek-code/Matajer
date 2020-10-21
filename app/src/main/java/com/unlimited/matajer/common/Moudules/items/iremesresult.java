
package com.unlimited.matajer.common.Moudules.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class iremesresult {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("has_favorite")
    @Expose
    private Boolean hasFavorite;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHasFavorite() {
        return hasFavorite;
    }

    public void setHasFavorite(Boolean hasFavorite) {
        this.hasFavorite = hasFavorite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
