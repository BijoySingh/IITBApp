package com.gymkhana.iitbapp.feed;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Base64;
import android.widget.GridView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.activity.RSSViewerActivity;
import com.gymkhana.iitbapp.fragment.HomeFragment;
import com.gymkhana.iitbapp.lvadapter.LVAdapterRSS;
import com.gymkhana.iitbapp.util.Constants;
import com.gymkhana.iitbapp.util.Functions;
import com.gymkhana.iitbapp.util.SharedPreferenceManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bijoy on 8/4/15.
 */
public class RSSFeedFetcher {
    private static final String LOG_KEY = "RSS_FEED";

    public static void getFeed(final Context context, final String link,
                               final String username, final String password,
                               final GridView gridView, final RSSFeedConstants.Feed feed) {
        StringRequest jsonRequest = new StringRequest
                (Request.Method.GET, link, new Response
                        .Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            RSSFeedChannel feedChannel = parseFeed(response);
                            gridView.setAdapter(new LVAdapterRSS(context, feedChannel.getFeed(3)));

                            Functions.offlineDataWriter(context, feed.filename(), response);
                            SharedPreferenceManager.save(context, feed.lastUpdated(), feedChannel.updated);
                        } catch (Exception e) {
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (username == null || password == null)
                    return params;

                String authString = username + ":" + password;
                byte[] authEncBytes = Base64.encode(authString.getBytes(), Base64.DEFAULT);
                String authStringEnc = new String(authEncBytes);
                params.put("Authorization", "Basic " + authStringEnc);
                return params;
            }
        };

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public static void getFeedForHome(final Context context, final String link, final String username, final String password,
                                      final HomeFragment homeFragment,
                                      final HomeFragment.NowCardMetaContent metaContent,
                                      final RSSFeedConstants.Feed feed) {
        StringRequest jsonRequest = new StringRequest
                (Request.Method.GET, link, new Response
                        .Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            RSSFeedChannel feedChannel = parseFeed(response);
                            homeFragment.addFeedCard(metaContent, feedChannel.getFeed(3));
                            Functions.offlineDataWriter(context, feed.filename(), response);
                            SharedPreferenceManager.save(context, feed.lastUpdated(), feedChannel.updated);
                        } catch (Exception e) {
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (username == null || password == null)
                    return params;

                String authString = username + ":" + password;
                byte[] authEncBytes = Base64.encode(authString.getBytes(), Base64.DEFAULT);
                String authStringEnc = new String(authEncBytes);
                params.put("Authorization", "Basic " + authStringEnc);
                return params;
            }
        };

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public static void getFeedForNotification(final Context context,
                                              final String link,
                                              final String username,
                                              final String password,
                                              final RSSFeedConstants.Feed feed) {
        StringRequest jsonRequest = new StringRequest
                (Request.Method.GET, link, new Response
                        .Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            RSSFeedChannel feedChannel = parseFeed(response);
                            String xml = Functions.offlineDataReader(context, feed.filename());
                            if (xml == null || xml.isEmpty()) {

                            } else {
                                RSSFeedChannel existingFeedChannel = parseFeed(response);
                                if (feedChannel.updated != existingFeedChannel.updated &&
                                        !existingFeedChannel.entries.isEmpty()) {

                                    RSSFeedItem feed = feedChannel.entries.get(0);

                                    Intent resultIntent = new Intent(context, RSSViewerActivity.class);
                                    resultIntent.putExtra(RSSViewerActivity.INTENT_RSS, feed);
                                    PendingIntent resultPendingIntent =
                                            PendingIntent.getActivity(
                                                    context,
                                                    0,
                                                    resultIntent,
                                                    PendingIntent.FLAG_UPDATE_CURRENT
                                            );

                                    // Show notification
                                    NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(context)
                                            .setSmallIcon(R.drawable.notification_icon)
                                            .setColor(feed.getAccentColor())
                                            .setCategory(Notification.CATEGORY_SOCIAL)
                                            .setContentTitle(Html.fromHtml(feed.title).toString())
                                            .setContentText(Html.fromHtml(feed.description).toString())
                                            .setContentIntent(resultPendingIntent)
                                            .setAutoCancel(true);

                                    NotificationManager mNotificationManager = (NotificationManager) context.getSystemService
                                            (Context.NOTIFICATION_SERVICE);
                                    mNotificationManager.notify(Constants.DATA_TYPE_RSS,
                                            mNotificationBuilder.build());
                                }
                            }

                            Functions.offlineDataWriter(context, feed.filename(), response);
                            SharedPreferenceManager.save(context, feed.lastUpdated(), feedChannel.updated);
                        } catch (Exception e) {
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (username == null || password == null)
                    return params;

                String authString = username + ":" + password;
                byte[] authEncBytes = Base64.encode(authString.getBytes(), Base64.DEFAULT);
                String authStringEnc = new String(authEncBytes);
                params.put("Authorization", "Basic " + authStringEnc);
                return params;
            }
        };

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public static RSSFeedChannel parseFeed(String response) {
        try {
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlFactoryObject.newPullParser();
            parser.setInput(new StringReader(response));
            return parseXML(parser);
        } catch (Exception e) {
            return new RSSFeedChannel();
        }
    }

    public static RSSFeedChannel parseXML(XmlPullParser myParser) {
        int event;
        String text = null;
        RSSFeedChannel feedChannel = new RSSFeedChannel();
        RSSFeedItem rssFeedItem = null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (name.equals("entry")) {
                            rssFeedItem = new RSSFeedItem();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (name.equals("updated")) {
                            if (rssFeedItem == null) {
                                feedChannel.updated = text;
                            }
                        }
                        if (name.equals("entry")) {
                            feedChannel.entries.add(rssFeedItem);
                        }
                        if (name.equals("title")) {
                            if (rssFeedItem == null) {
                                feedChannel.title = text;
                            } else {
                                rssFeedItem.title = Functions.correctUTFEncoding(text);
                            }
                        } else if (name.equals("content")) {
                            if (rssFeedItem != null) {
                                rssFeedItem.description = Functions.correctUTFEncoding(text);
                            }
                        } else if (name.equals("id")) {
                            if (rssFeedItem != null) {
                                rssFeedItem.guid = text;
                            }
                        } else if (name.equals("published")) {
                            if (rssFeedItem != null) {
                                rssFeedItem.publishDate = text;
                            }
                        } else if (name.equals("category")) {
                            if (rssFeedItem != null) {
                                rssFeedItem.category.add(text);
                            }
                        } else if (name.equals("name")) {
                            if (rssFeedItem != null) {
                                rssFeedItem.name = text;
                            }
                        }
                        break;
                }

                event = myParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return feedChannel;
    }

}
