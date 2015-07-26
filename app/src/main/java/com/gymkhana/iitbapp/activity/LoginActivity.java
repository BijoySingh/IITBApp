package com.gymkhana.iitbapp.activity;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gymkhana.iitbapp.MainActivity;
import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.util.Constants;
import com.gymkhana.iitbapp.util.Functions;
import com.gymkhana.iitbapp.util.AuthFunctions;
import com.gymkhana.iitbapp.util.ServerUrls;
import com.gymkhana.iitbapp.util.SharedPreferenceManager;

public class LoginActivity extends ActionBarActivity {

    public static boolean loginEnabled = true;
    public static ProgressBar progressBar;
    private Context mContext;
    private Integer mMode = Constants.LOGIN_LDAP_LOGIN;
    private EditText mInput, mPassword;
    private ImageView mLeftImage, mCenterImage, mRightImage;
    private TextView mLeftText, mCenterText, mRightText;

    public static void enableLogin() {
        loginEnabled = true;

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

    }

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
            mInput.setText(SharedPreferenceManager.load(mContext, SharedPreferenceManager.Tags.USERNAME));
            mPassword.setHint(getString(R.string.login_input_password));
            mPassword.setText(SharedPreferenceManager.load(mContext, SharedPreferenceManager.Tags.PASSWORD));

            mLeftImage.setImageResource(R.drawable.link_icon);
            mLeftImage.setOnClickListener(getOnClickListener(Constants.ButtonTypes.IITB_HOME));

            mCenterImage.setImageResource(R.drawable.login_icon);
            mCenterImage.setOnClickListener(getOnClickListener(Constants.ButtonTypes.LDAP_LOGIN));

            mRightImage.setImageResource(R.drawable.offline_icon);
            mRightImage.setOnClickListener(getOnClickListener(Constants.ButtonTypes.OFFLINE));

            mLeftText.setText(getString(R.string.login_iitb_home));
            mCenterText.setText(getString(R.string.login_login));
            mRightText.setText(getString(R.string.login_offline));

        } else if (mMode == Constants.LOGIN_REQUEST_CODE) {
            mInput.setHint(getString(R.string.login_input_ldap));
            mPassword.setVisibility(View.GONE);

            mLeftImage.setImageResource(R.drawable.link_icon);
            mLeftImage.setOnClickListener(getOnClickListener(Constants.ButtonTypes.IITB_HOME));

            mCenterImage.setImageResource(R.drawable.login_icon);
            mCenterImage.setOnClickListener(getOnClickListener(Constants.ButtonTypes.REQUEST_CODE));

            mRightImage.setImageResource(R.drawable.offline_icon);
            mRightImage.setOnClickListener(getOnClickListener(Constants.ButtonTypes.OFFLINE));

            mLeftText.setText(getString(R.string.login_iitb_home));
            mCenterText.setText(getString(R.string.login_request_code));
            mRightText.setText(getString(R.string.login_offline));

        } else if (mMode == Constants.LOGIN_LOG_INTO) {
            mInput.setHint(getString(R.string.login_input_request));
            mPassword.setVisibility(View.GONE);

            mLeftImage.setImageResource(R.drawable.back_icon);
            mLeftImage.setOnClickListener(getOnClickListener(Constants.ButtonTypes.CHANGE_LDAP));

            mCenterImage.setImageResource(R.drawable.login_icon);
            mCenterImage.setOnClickListener(getOnClickListener(Constants.ButtonTypes.LOGIN));

            mRightImage.setImageResource(R.drawable.resend_icon);
            mRightImage.setOnClickListener(getOnClickListener(Constants.ButtonTypes.RESEND_CODE));

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
        if (buttonType == Constants.ButtonTypes.CHANGE_LDAP) {

        } else if (buttonType == Constants.ButtonTypes.IITB_HOME) {
            Functions.openWebsite(mContext, ServerUrls.getInstance().IITB_HOME);

        } else if (buttonType == Constants.ButtonTypes.LDAP_LOGIN) {
            if (loginEnabled) {
                disableLogin();
                AuthFunctions.sendLoginQuery(
                        mContext,
                        mInput.getText().toString(),
                        mPassword.getText().toString()
                );
            }
        } else if (buttonType == Constants.ButtonTypes.LOGIN) {

        } else if (buttonType == Constants.ButtonTypes.OFFLINE) {
            AuthFunctions.userOffline(mContext);
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();

        } else if (buttonType == Constants.ButtonTypes.REQUEST_CODE) {

        } else if (buttonType == Constants.ButtonTypes.RESEND_CODE) {

        }
    }

    protected void disableLogin() {
        loginEnabled = false;

        if (progressBar == null) {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
        }

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressBar = null;
    }
}
