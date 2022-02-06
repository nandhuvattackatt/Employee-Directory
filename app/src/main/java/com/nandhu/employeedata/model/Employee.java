package com.nandhu.employeedata.model;

import android.text.TextUtils;

import org.json.JSONObject;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class Employee  extends RealmObject {


   private int id;
   private String name, userName, email, profileImage;
   private String street, suite, city, zipCode, latitude, longitude;
   private String phone, website;
   private String companyName, catchPhrase, bs;


    public Employee() {
    }


    public Employee(JSONObject jsonObject) {

        //Employee Details
        setId(jsonObject.optInt("id"));
        setName(jsonObject.optString("name"));
        setUserName(jsonObject.optString("username"));
        setEmail(jsonObject.optString("email"));
        if (TextUtils.isEmpty(jsonObject.optString("profile_image"))||jsonObject.optString("profile_image").equalsIgnoreCase("null")){

            setProfileImage("https://www.pngkey.com/png/detail/115-1150152_default-profile-picture-avatar-png-green.png");
        }else {
            setProfileImage(jsonObject.optString("profile_image"));
        }

        setPhone(jsonObject.optString("phone"));
        setWebsite(jsonObject.optString("website"));

        //Address
        JSONObject addressJSON=jsonObject.optJSONObject("address");
        if (addressJSON != null) {
            setStreet(addressJSON.optString("street"));
            setSuite(addressJSON.optString("suite"));
            setCity(addressJSON.optString("city"));
            setZipCode(addressJSON.optString("zipcode"));
        }


        //Geo Location
        JSONObject geoJSON= null;
        if (addressJSON != null) {
            geoJSON = addressJSON.optJSONObject("geo");
        }
        if (geoJSON != null) {
            setLatitude(geoJSON.optString("lat"));
            setLongitude(geoJSON.optString("lng"));
        }


        //Company Details
        JSONObject companyJSON=jsonObject.optJSONObject("company");
        if (companyJSON != null) {
            setCompanyName(companyJSON.optString("name"));
            setCatchPhrase(companyJSON.optString("catchPhrase"));
            setBs(companyJSON.optString("bs"));
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }
}
