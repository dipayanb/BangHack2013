package com.hackback.core;

/**
 * @author Winash
 */
public class CrawlResult {

    public String id;

    public String justDialId;

    public String companyName;

    public String address;

    public String city;

    public String pinCode;

    public String landLine;

    public String mobile;

    public String website;

    public String email;

    public String lat;

    public String lng;

    public String avgRating;

    public String totalRatings;

    public String queryId;

    public String languageCode;

    public String toIndex;

    @Override
    public String toString() {
        return "CrawlResult{" +
                "id='" + id + '\'' +
                ", justDialId='" + justDialId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", landLine='" + landLine + '\'' +
                ", mobile='" + mobile + '\'' +
                ", website='" + website + '\'' +
                ", email='" + email + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", avgRating='" + avgRating + '\'' +
                ", totalRatings='" + totalRatings + '\'' +
                ", queryId='" + queryId + '\'' +
                ", languageCode='" + languageCode + '\'' +
                '}';
    }
}
