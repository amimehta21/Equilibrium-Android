package vayu.tech.equilibrium;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private View progressOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailEditText = (EditText) findViewById(R.id.LoginActivity_enterEmail);
        mEmailEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEmailEditText, InputMethodManager.SHOW_IMPLICIT);

        mPasswordEditText = (EditText) findViewById(R.id.LoginActivity_enterPassword);
        mLoginButton = (Button) findViewById(R.id.LoginActivity_loginButton);
        progressOverlay = findViewById(R.id.progress_overlay);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startLoginProcess(); MODIFIED FOR DEMO
                forceLogin();
            }
        });
    }

    // ADDED FOR DEMO
    private void forceLogin() {
        if (mEmailEditText.getText().toString().equals("coach@vayu.tech") && mPasswordEditText.getText().toString().equals("admin123")) {
            User user = new User(0, "Sample User #1");
            Intent intent = new Intent(LoginActivity.this, SelectAthleteActivity.class);
            SimpleCoach coach = new SimpleCoach(null, user);
            intent.putExtra(Keys.EXTRA_SIMPLE_COACH, coach);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect username or password!", Toast.LENGTH_SHORT).show();
        }
    }

    private void startLoginProcess() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEmailEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mPasswordEditText.getWindowToken(), 0);
        mLoginButton.setEnabled(false);
        progressOverlay.bringToFront();
        // Show progress overlay (with animation):
        GlobalUtils.animateView(progressOverlay, View.VISIBLE, 0.4f, 200);

        startDownload();
    }

    // pre: none
    // post: starts a download process in a background thread to download the announcements
    private void startDownload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                verifyLogin();
            }
        }).start();
    }

    private void verifyLogin() {
        String user = mEmailEditText.getText().toString();
        String pass = mPasswordEditText.getText().toString();

        // download the contents of the text file at the link
        HashMap<String, String> parameters = new HashMap<String,String>();
        parameters.put("user", user);
        parameters.put("pass", pass);
        String link = LinkUtils.buildURL("auth", parameters);
        String file = StringUtils.getUrlContents(link);
        receivedMessage(file);
    }

    private void receivedMessage(String message) {
        System.out.println(message);
        if (message.contains("You don't have the permission to log in or your account is no longer active")) {
            loginFailed(message.replace("\"", ""));
        } else {
            // message must be a JSON file
            parseJSON(message);
        }
    }

    private void parseJSON(String message) {
        try {
            JSONObject j = new JSONObject(message.trim());

            User user = new User(Integer.parseInt(j.getString(Keys.USER_ID)),
                    j.getString(Keys.NAME),
                    j.getString(Keys.EMAIL),
                    j.getString(Keys.PASSWORD),
                    GlobalUtils.boolFrom(j.getString(Keys.IS_ACCT_ACTIVE)),
                    GlobalUtils.boolFrom(j.getString(Keys.CAN_LOGIN)),
                    GlobalUtils.boolFrom(j.getString(Keys.IS_USR_MANAGER)),
                    GlobalUtils.boolFrom(j.getString(Keys.IS_ATHLETE)),
                    GlobalUtils.boolFrom(j.getString(Keys.IS_DATA_VIS)),
                    GlobalUtils.boolFrom(j.getString(Keys.AGR_TO_POLICY)),
                    GlobalUtils.boolFrom(j.getString(Keys.IS_LIC_ALLOCATOR)),
                    Integer.parseInt(j.getString(Keys.ORG_ID)),
                    GlobalUtils.boolFrom(j.getString(Keys.IS_PURCHASER)),
                    GlobalUtils.boolFrom(j.getString(Keys.IS_DATA_COLLECTOR)),
                    j.getString(Keys.CREATION_DATE));
            this.loginSuccess(user);

        } catch (JSONException e) {
            System.out.println("Parsing JSON Failed!");
            loginFailed(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private void loginFailed(final String message) {
        runOnUiThread(new Runnable() {
            public void run() {
                endLoginProcess();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginSuccess(final User user) {
        runOnUiThread(new Runnable() {
            public void run() {
                endLoginProcess();
                if (user.isAthlete()) {
                    // TODO: GO TO ATHLETE VIEW
                    Toast.makeText(getApplicationContext(), "go to athlete view now", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, SelectAthleteActivity.class);
                    SimpleCoach coach = new SimpleCoach(null, user);
                    intent.putExtra(Keys.EXTRA_SIMPLE_COACH, coach);
                    startActivity(intent);
                }
            }
        });
    }

    private void endLoginProcess() {
        // resume view
        mLoginButton.setEnabled(true);
        GlobalUtils.animateView(progressOverlay, View.GONE, 0, 200);
    }

}
