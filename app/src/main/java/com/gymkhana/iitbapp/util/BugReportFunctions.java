package com.gymkhana.iitbapp.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.widget.EditText;

import com.gymkhana.iitbapp.R;

/**
 * Created by bijoy on 7/30/15.
 */
public class BugReportFunctions {

    private static final String PHONE_MODEL = "Model : ";
    private static final String ANDROID_VERSION = "Version : ";
    private static final String MANUFACTURER = "Manufacturer : ";
    private static final String DEVICE = "Device : ";

    private static final String PhoneModel = Build.MODEL;
    private static final String AndroidVersion = Build.VERSION.RELEASE;
    private static final String Manufacturer = Build.MANUFACTURER;
    private static final String Device = Build.DEVICE;

    public static void showDialog(Context context) {

        final EditText edittext = new EditText(context);
        edittext.setHint("Write your bug here...");
        edittext.setMinLines(4);

        AlertDialog alert = new AlertDialog.Builder(context)
                .setMessage(
                        PHONE_MODEL + PhoneModel + "\n" +
                                ANDROID_VERSION + AndroidVersion + "\n" +
                                MANUFACTURER + Manufacturer + "\n"
                )
                .setTitle(context.getString(R.string.settings_report_bug))
                .setView(edittext)
                .setPositiveButton(context.getString(R.string.send),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })
                .show();
    }

    public static void sendReport() {

    }
}
