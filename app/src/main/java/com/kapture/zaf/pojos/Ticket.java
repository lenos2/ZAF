
package com.kapture.zaf.pojos;


import java.io.Serializable;

@SuppressWarnings("unused")
public class Ticket implements Serializable {

    private String mDescription;
    private String mImage;
    private String mName;
    private Double mPrice;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Double getPrice() {
        return mPrice;
    }

    public void setPrice(Double price) {
        mPrice = price;
    }

}
