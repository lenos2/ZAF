
package com.kapture.zaf.pojos;

@SuppressWarnings("unused")
public class Sale {

    private int mNumber;
    private String mTicketType;
    private Double mTotal;
    private String mUserId;

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public String getTicketType() {
        return mTicketType;
    }

    public void setTicketType(String ticketType) {
        mTicketType = ticketType;
    }

    public Double getTotal() {
        return mTotal;
    }

    public void setTotal(Double total) {
        mTotal = total;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
