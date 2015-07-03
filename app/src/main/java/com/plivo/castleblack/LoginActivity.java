package com.plivo.castleblack;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.plivo.endpoint.Endpoint;
import com.plivo.endpoint.EventListener;
import com.plivo.endpoint.Incoming;
import com.plivo.endpoint.Outgoing;


public class LoginActivity extends AppCompatActivity implements EventListener  {
    private static final String TAG = "LoginActivity";

    /*SharedPreferences sp=getSharedPreferences("LoginActivity", 0);
    SharedPreferences.Editor Ed=sp.edit();*/
    Endpoint endpoint = Endpoint.newInstance(true, this);


    @InjectView(R.id.input_userName) EditText _userName;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    //@InjectView(R.id.link_signup) TextView _signupLink;

    private ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                login();
            }
        });

    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            return;
        }

        _loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String username = _userName.getText().toString();
        String password = _passwordText.getText().toString();

        /*Ed.putString("End", username);
        Ed.putString("Pwd",password);
        Ed.putBoolean("isLogin", true);
        Ed.commit();*/
        Log.v("PlivoOutbound", username);
        Log.v("PlivoOutbound", password);

        endpoint.login(username, password);
        DataHolder.setEndpoint(endpoint);

    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }


    public void onLoginFailed() {
        //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        Log.v("PlivoOutbound", "Login failed");
        runOnUiThread(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                        _loginButton.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public boolean validate() {
        boolean valid = true;

        String userName = _userName.getText().toString();
        String password = _passwordText.getText().toString();

        if (userName.isEmpty()) {
            _userName.setError("enter user name");
            valid = false;
        } else {
            _userName.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("enter password");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void onLogin() {
        //Toast.makeText(getBaseContext(), "Login success", Toast.LENGTH_LONG).show();

        //_loginButton.setEnabled(true);
        Log.v("PlivoOutbound", "Logged in");
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);

    }

    public void onLogout() {
        Log.v("PlivoOutbound", "Logged out");
    }

    /**
     * This event will be fired when there is new incoming call.
     * @param incoming new Incoming call object.
     */
    public void onIncomingCall(Incoming incoming) {

    }
    public void onIncomingCallHangup(Incoming incoming) {

    }
    public void onIncomingCallRejected(Incoming incoming) {

    }

    /**
     * This event will be fired when outgoing call is initiated.
     * @param outgoing
     */
    public void onOutgoingCall(Outgoing outgoing) {

    }

    public void onOutgoingCallAnswered(Outgoing outgoing) {

    }

    public void onOutgoingCallHangup(Outgoing outgoing) {

    }
    public void onOutgoingCallRejected(Outgoing outgoing) {

    }
    public void onOutgoingCallInvalid(Outgoing outgoing) {

    }
}
