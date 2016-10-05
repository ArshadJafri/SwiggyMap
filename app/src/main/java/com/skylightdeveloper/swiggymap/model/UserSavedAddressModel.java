package com.skylightdeveloper.swiggymap.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Akash Wangalwar on 24-09-2016.
 */
public class UserSavedAddressModel extends RealmObject{

    @PrimaryKey
    private String id;
    private String streetAddress;
    private String flatNum;
    private String landmark;
    private String otherTypeTag;
    private boolean isWorkAdress;
    private boolean isHomeAdress;
    private boolean isOtherAdress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getFlatNum() {
        return flatNum;
    }

    public void setFlatNum(String flatNum) {
        this.flatNum = flatNum;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getOtherTypeTag() {
        return otherTypeTag;
    }

    public void setOtherTypeTag(String otherTypeTag) {
        this.otherTypeTag = otherTypeTag;
    }

    public boolean isWorkAdress() {
        return isWorkAdress;
    }

    public void setWorkAdress(boolean workAdress) {
        isWorkAdress = workAdress;
    }

    public boolean isHomeAdress() {
        return isHomeAdress;
    }

    public void setHomeAdress(boolean homeAdress) {
        isHomeAdress = homeAdress;
    }

    public boolean isOtherAdress() {
        return isOtherAdress;
    }

    public void setOtherAdress(boolean otherAdress) {
        isOtherAdress = otherAdress;
    }

}
