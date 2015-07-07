package com.plivo.castleblack;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.melnykov.fab.FloatingActionButton;
import com.plivo.endpoint.Endpoint;
import com.plivo.endpoint.EventListener;
import com.plivo.endpoint.Incoming;
import com.plivo.endpoint.Outgoing;

import butterknife.InjectView;

public class Incoming_Call extends AppCompatActivity {


    public final static String EXTRA_MESSAGE = "com.plivo.example.MESSAGE";

    // Edit the variables below with your Plivo endpoint username and password

    Endpoint endpoint = DataHolder.getEndpoint();
    Incoming incoming = DataHolder.getIncoming();
    boolean isSpeakerOn;
    boolean isMuteOn;
    private AudioManager myAudioManager;
    @InjectView(R.id.speaker) FloatingActionButton speaker;

    @InjectView(R.id.hangup_call)
    FloatingActionButton hangup_button;

    @InjectView(R.id.accept_call)
    FloatingActionButton answer_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("PlivoInbound", "Trying to log in");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming__call);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_incoming__call, menu);
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
        Log.v("PlivoInbound", "Hanging up...");
        incoming.hangup();
        //Button hangup_button = ((Button)findViewById(R.id.hangup_call));
        //Button answer_button = ((Button)findViewById(R.id.accept_call));
        //hangup_button.show();
        //hangup_button.setClickable(false);

        //answer_button.show();
        //answer_button.setClickable(true);
        Intent intent = new Intent(this, CallingScreen.class);
        startActivity(intent);
    }

    public void answerNow(View view) {
        Log.v("PlivoInbound", "Answering");
        incoming.answer();
        //Button hangup_button = ((Button)findViewById(R.id.hangup_call));
        //Button answer_button = ((Button)findViewById(R.id.accept_call));
        //hangup_button.show();
        //hangup_button.setClickable(true);

        //answer_button.hide(false);
        //answer_button.setClickable(false);
    }



    public void speakerOn(View view) {
        Log.v("PlivoOutbound", "Speaker btn pressed...");
        myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (!isSpeakerOn) {
            myAudioManager.setSpeakerphoneOn(true);
            //speaker.setImageResource(R.drawable.ic_volume_mute_black_24dp);
            isSpeakerOn = true;
        }
        else {
            myAudioManager.setSpeakerphoneOn(false);
            //speaker.setImageResource(R.drawable.ic_volume_up_black_24dp);
            isSpeakerOn=false;
        }

    }

    public void muteOn(View view){
        if (!isMuteOn) {
            Log.v("PlivoPhone", "Mute");
            incoming.mute();
        }else{
            Log.v("PlivoPhone", "Unmute");
            incoming.unmute();
        }
    }
}
