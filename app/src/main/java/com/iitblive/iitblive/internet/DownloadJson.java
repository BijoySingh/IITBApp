package com.iitblive.iitblive.internet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.iitblive.iitblive.util.Constants;
import com.iitblive.iitblive.util.DownloadJsonUtil;
import com.iitblive.iitblive.util.Functions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;


/**
 * Created by Bijoy on 5/25/2015.
 */

public class DownloadJson extends AsyncTask<String, Integer, String> {

  private String mLink, mMessage;
  private Context mContext;
  private Integer mDataType, mDataCount;
  private View mAdapterView;
  private boolean mIsGrid = true;
  public Integer mIconResource = null;

  private boolean storeInFile;
  private String storageFile;

  public DownloadJson(
      String link,
      Context context,
      Integer dataType,
      Integer dataCount,
      View adapterView) {
    this.mLink = link;
    this.mContext = context;
    this.mDataType = dataType;
    this.mDataCount = dataCount;
    this.mAdapterView = adapterView;
  }

  public void setStorageFile(String filename) {
    storageFile = filename;
    storeInFile = true;
  }


  @Override
  protected String doInBackground(String... params) {
    return getData();
  }

  public String getData() {
    HttpClient httpClient = new DefaultHttpClient();
    HttpContext localContext = new BasicHttpContext();

    HttpGet httpGet = new HttpGet(Constants.BASE_URL + mLink);
    httpGet.setHeader(Constants.SERVER_REFERER, Constants.BASE_URL + mLink);

    try {
      HttpResponse response = httpClient.execute(httpGet, localContext);
      try {
        int status = response.getStatusLine().getStatusCode();
        if (status == 200) {
          HttpEntity e = response.getEntity();
          String data = EntityUtils.toString(e);
          return data;
        } else {
          HttpEntity e = response.getEntity();
          String data = EntityUtils.toString(e);
          // Functions.makeToast(mContext, R.string.toast_no_connection);
        }
      } catch (Exception e) {
        // Functions.makeToast(mContext, R.string.toast_no_data);
      }
    } catch (ClientProtocolException e) {
      //  Functions.makeToast(mContext, R.string.toast_no_data);
    } catch (IOException e) {
      // Functions.makeToast(mContext, R.string.toast_no_data);
    } catch (Exception e) {
      // Functions.makeToast(mContext, R.string.toast_no_data);
    }
    return null;
  }

  protected void onPostExecute(String data) {
    if (data == null || data.contentEquals("")) {
      DownloadJsonUtil.onCreateEmptyList(mContext, mAdapterView);
      return;
    }

    if (storeInFile) {
      Functions.offlineDataWriter(mContext, storageFile, data);
    }

    try {
      if (mDataCount == Constants.DATA_COUNT_SINGLE) {
        JSONObject jsonObject = new JSONObject(data);
      } else if (mDataCount == Constants.DATA_COUNT_MULTIPLE) {
        if (mDataType == Constants.DATA_TYPE_NEWS ||
            mDataType == Constants.DATA_TYPE_EVENT ||
            mDataType == Constants.DATA_TYPE_ANY) {
          DownloadJsonUtil.onGetDataResult(data, mDataType, mContext, mAdapterView);
        } else if (mDataType == Constants.DATA_TYPE_INFORMATION) {
          DownloadJsonUtil.onGetInformationResult(data, mContext, mIconResource, mAdapterView);
        }
      }
    } catch (Exception e) {
      Log.d("EVENT_LIST_CREATION", e.toString());
    }
  }

}
