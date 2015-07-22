package com.iitblive.iitblive.items;

/**
 * Created by Bijoy on 5/28/2015.
 */
public class InformationItem {
    public Integer img_resource;
    public String title, description;
    public String phone, website, email, facebook;

    public InformationItem(Integer img_resource, String title, String description, String phone, String website, String email, String facebook) {
        this.img_resource = img_resource;
        this.title = title;
        this.description = description;
        this.phone = phone;
        this.website = website;
        this.email = email;
        this.facebook = facebook;
    }

    public InformationItem() {
    }
}
