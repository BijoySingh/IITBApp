package com.iitblive.iitblive.items;

import java.util.List;

/**
 * Created by Bijoy on 5/25/2015.
 */
public class EventListViewItem {
  public Integer id;
  public String type, title, description, category;
  public String source_name, source_designation, timestamp;
  public String event_timestamp, event_location;
  public Integer likes, views;
  public List<String> image_links;
  public TimestampItem article_time, event_time;
}
