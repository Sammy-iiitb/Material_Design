package com.plivo.castleblack;

import android.content.Context;
import android.media.AudioManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.transition.Slide;
import android.util.Pair;
import android.widget.Button;
import android.widget.ToggleButton;

import com.melnykov.fab.FloatingActionButton;
import com.plivo.endpoint.Endpoint;
import com.plivo.endpoint.EventListener;
import com.plivo.endpoint.Incoming;
import com.plivo.endpoint.Outgoing;

import butterknife.InjectView;


public class CallingScreen extends AppCompatActivity implements EventListener {


    Outgoing outgoing = DataHolder.getOutgoing();

    private AudioManager myAudioManager;
    boolean isOn = false;
    View view;

    @InjectView(R.id.speaker) FloatingActionButton speaker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calling_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void hangupNow(View view) {
        Log.v("PlivoOutbound", "Hanging up...");
        outgoing.hangup();
        Intent intent = new Intent(view.getContext(), NavigationActivity.class);
        view.getContext().startActivity(intent);
    }

    public void speakerOn(View view) {
        Log.v("PlivoOutbound", "Speaker btn pressed...");
        myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (!isOn) {
            myAudioManager.setSpeakerphoneOn(true);
            //speaker.setImageResource(R.drawable.ic_volume_mute_black_24dp);
            isOn = true;
        }
        else {
            myAudioManager.setSpeakerphoneOn(false);
            //speaker.setImageResource(R.drawable.ic_volume_up_black_24dp);
            isOn=false;
        }

    }

    public void onLoginFailed() {
    }

    public void onLogin() {
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
        Log.v("PlivoOutbound", "outgoing call answered");
    }

    public void onOutgoingCallHangup(Outgoing outgoing) {
        Log.v("PlivoOutbound", "outgoing call hanged up");
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);

    }
    public void onOutgoingCallRejected(Outgoing outgoing) {
        Log.v("PlivoOutbound", "outgoing call rejected");
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);

    }
    public void onOutgoingCallInvalid(Outgoing outgoing) {

    }

}
