package com.gymkhana.iitbapp.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gymkhana.iitbapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bijoy on 7/30/15.
 */
public class BugReportFunctions {

    private static final String PHONE_MODEL = "Model : ";
    private static final String ANDROID_VERSION = "Version : ";
    private static final String MANUFACTURER = "Manufacturer : ";
    private static final String DEVICE = "Device : ";

    private static final String mPhoneModel = Build.MODEL;
    private static final String mAndroidVersion = Build.VERSION.RELEASE;
    private static final String mManufacturer = Build.MANUFACTURER;
    private static final String mDevice = Build.DEVICE;

    public static void showDialog(final Context context) {

        final EditText edittext = new EditText(context);
        edittext.setHint("Write your bug here...");
        edittext.setMinLines(4);

        AlertDialog alert = new AlertDialog.Builder(context)
                .setMessage(
                        PHONE_MODEL + mPhoneModel + "\n" +
                                ANDROID_VERSION + mAndroidVersion + "\n" +
                                MANUFACTURER + mManufacturer + "\n"
                )
                .setTitle(context.getString(R.string.settings_report_bug))
                .setView(edittext)
                .setPositiveButton(context.getString(R.string.send),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                sendReport(context, edittext.getText().toString());
                            }
                        })
                .show();
    }

    public static void sendReport(final Context context, String report) {
        JSONObject params = getParams(context, report);
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, ServerUrls.getInstance().BUG, params, new Response
                        .Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {
                        Functions.makeToast(context, "Thanks! Your bug has been recorded!");
                        try {
                            ;
                        } catch (Exception e) {
                            e.printStackTrace();
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
                params.put("token-auth", SharedPreferenceManager.load(context, SharedPreferenceManager.Tags.USER_TOKEN));
                return params;
            }
        };
        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public static JSONObject getParams(Context context, String report) {
        Map<String, String> map = new HashMap<>();
        map.put("phone_model", mPhoneModel);
        map.put("android_version", mAndroidVersion);
        map.put("manufacturer", mManufacturer);
        map.put("device_id", mDevice);
        map.put("user", SharedPreferenceManager.load(context, SharedPreferenceManager.Tags.USER_ID));
        map.put("description", report);

        return new JSONObject(map);
    }
}
