
package com.kapture.zaf.pojos;


import java.io.Serializable;

@SuppressWarnings("unused")
public class Event2 implements Serializable{

    private String mDescription;
    private String mImage;
    private String mName;
    private String mSummaryBody;
    private String mSummaryHeader;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String Description) {
        mDescription = Description;
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

    public String getSummaryBody() {
        return mSummaryBody;
    }

    public void setSummaryBody(String SummaryBody) {
        mSummaryBody = SummaryBody;
    }

    public String getSummaryHeader() {
        return mSummaryHeader;
    }

    public void setSummaryHeader(String SummaryHeader) {
        mSummaryHeader = SummaryHeader;
    }

}
