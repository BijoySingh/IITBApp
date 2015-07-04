package com.iitblive.iitblive.activity;

/**
 * Created by Bijoy on 5/27/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iitblive.iitblive.MainActivity;
import com.iitblive.iitblive.R;
import com.iitblive.iitblive.internet.FormTask;
import com.iitblive.iitblive.util.Constants;
import com.iitblive.iitblive.util.Functions;
import com.iitblive.iitblive.util.LoginFunctions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends ActionBarActivity {

    private Context mContext;
    private Integer mMode = Constants.LOGIN_LDAP_LOGIN;
    private EditText mInput, mPassword;
    private ImageView mLeftImage, mCenterImage, mRightImage;
    private TextView mLeftText, mCenterText, mRightText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        mContext = this;

        mInput = (EditText) findViewById(R.id.input);
        mPassword = (EditText) findViewById(R.id.password);

        mLeftImage = (ImageView) findViewById(R.id.login_left_btn);
        mCenterImage = (ImageView) findViewById(R.id.login_center_btn);
        mRightImage = (ImageView) findViewById(R.id.login_right_btn);

        mLeftText = (TextView) findViewById(R.id.login_left_txt);
        mCenterText = (TextView) findViewById(R.id.login_center_txt);
        mRightText = (TextView) findViewById(R.id.login_right_txt);

        if (mMode == Constants.LOGIN_LDAP_LOGIN) {
            mInput.setHint(getString(R.string.login_input_ldap));
            mPassword.setHint(getString(R.string.login_input_password));

            mLeftImage.setImageResource(R.drawable.link_icon);
            mLeftImage.setOnClickListener(getOnClickListener(Constants.BUTTON_TYPE_IITB_HOME));

            mCenterImage.setImageResource(R.drawable.login_icon);
            mCenterImage.setOnClickListener(getOnClickListener(Constants.BUTTON_TYPE_LDAP_LOGIN));

            mRightImage.setImageResource(R.drawable.offline_icon);
            mRightImage.setOnClickListener(getOnClickListener(Constants.BUTTON_TYPE_OFFLINE));

            mLeftText.setText(getString(R.string.login_iitb_home));
            mCenterText.setText(getString(R.string.login_login));
            mRightText.setText(getString(R.string.login_offline));

        } else if (mMode == Constants.LOGIN_REQUEST_CODE) {
            mInput.setHint(getString(R.string.login_input_ldap));
            mPassword.setVisibility(View.GONE);

            mLeftImage.setImageResource(R.drawable.link_icon);
            mLeftImage.setOnClickListener(getOnClickListener(Constants.BUTTON_TYPE_IITB_HOME));

            mCenterImage.setImageResource(R.drawable.login_icon);
            mCenterImage.setOnClickListener(getOnClickListener(Constants.BUTTON_TYPE_REQUEST_CODE));

            mRightImage.setImageResource(R.drawable.offline_icon);
            mRightImage.setOnClickListener(getOnClickListener(Constants.BUTTON_TYPE_OFFLINE));

            mLeftText.setText(getString(R.string.login_iitb_home));
            mCenterText.setText(getString(R.string.login_request_code));
            mRightText.setText(getString(R.string.login_offline));

        } else if (mMode == Constants.LOGIN_LOG_INTO) {
            mInput.setHint(getString(R.string.login_input_request));
            mPassword.setVisibility(View.GONE);

            mLeftImage.setImageResource(R.drawable.back_icon);
            mLeftImage.setOnClickListener(getOnClickListener(Constants.BUTTON_TYPE_CHANGE_LDAP));

            mCenterImage.setImageResource(R.drawable.login_icon);
            mCenterImage.setOnClickListener(getOnClickListener(Constants.BUTTON_TYPE_LOGIN));

            mRightImage.setImageResource(R.drawable.resend_icon);
            mRightImage.setOnClickListener(getOnClickListener(Constants.BUTTON_TYPE_RESEND_CODE));

            mLeftText.setText(getString(R.string.login_change_ldap));
            mCenterText.setText(getString(R.string.login_login));
            mRightText.setText(getString(R.string.login_resend_code));
        }
        mInput.setSelected(false);

        Functions.setActionBar(this, R.color.loginpage_500, R.color.loginpage_700);
        Functions.hideActionBar(this);

    }

    private View.OnClickListener getOnClickListener(final int buttonType) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEvent(buttonType);
            }
        };
    }

    private void clickEvent(int buttonType) {
        if (buttonType == Constants.BUTTON_TYPE_CHANGE_LDAP) {

        } else if (buttonType == Constants.BUTTON_TYPE_IITB_HOME) {
            Functions.openWebsite(mContext, Constants.IITB_HOME_URL);

        } else if (buttonType == Constants.BUTTON_TYPE_LDAP_LOGIN) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair(
                    Constants.LDAP_AUTH_USERNAME_PARAM,
                    mInput.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair(
                    Constants.LDAP_AUTH_PASSWORD_PARAM,
                    mPassword.getText().toString()));

            new FormTask(
                    mContext,
                    Constants.LDAP_AUTH_URL,
                    FormTask.FORM_MODE_LDAP_LOGIN,
                    nameValuePairs).execute();
        } else if (buttonType == Constants.BUTTON_TYPE_LOGIN) {

        } else if (buttonType == Constants.BUTTON_TYPE_OFFLINE) {
            LoginFunctions.userOffline(mContext);
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();

        } else if (buttonType == Constants.BUTTON_TYPE_REQUEST_CODE) {

        } else if (buttonType == Constants.BUTTON_TYPE_RESEND_CODE) {

        }
    }
}
