
package com.kapture.zaf.pojos;


import java.io.Serializable;

@SuppressWarnings("unused")
public class GalleryImage implements Serializable{

    private String mDownloadLink;

    public String getDownloadLink() {
        return mDownloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        mDownloadLink = downloadLink;
    }

}
