package com.gymkhana.iitbapp.items;

/**
 * Created by bijoy on 9/2/15.
 */
public class CategoryItem {
    public Integer id, feed_id;
    public String term, scheme, label;

    public CategoryItem(Integer id, Integer feed_id, String term, String scheme, String label) {
        this.id = id;
        this.feed_id = feed_id;
        this.term = term;
        this.scheme = scheme;
        this.label = label;
    }
}
