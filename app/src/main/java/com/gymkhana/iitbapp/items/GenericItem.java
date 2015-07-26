package com.gymkhana.iitbapp.items;

/**
 * Created by Bijoy on 1/15/2015.
 * It is the item used  to create
 * the list views for all the lists
 */
public class GenericItem {
    public static final Integer IS_URGENT = 1;
    public Integer img_res, img_right_res, tag = 0;
    public String title, subtitle;
    public Boolean show_subtext = false, show_right_icon = false;

    public GenericItem(Integer img_res, String title, Integer tag) {
        this.title = title;
        this.img_res = img_res;
        this.tag = tag;
    }

    public GenericItem(Integer img_res, String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
        this.img_res = img_res;
        this.show_subtext = true;
    }

    public GenericItem(Integer img_res, String title, String subtitle, Integer img_right_res) {
        this.title = title;
        this.subtitle = subtitle;
        this.img_res = img_res;
        this.show_subtext = true;
        this.img_right_res = img_right_res;
        this.show_right_icon = true;
    }

}
