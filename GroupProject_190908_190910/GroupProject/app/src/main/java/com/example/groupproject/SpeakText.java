package com.example.groupproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class SpeakText extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextToSpeech mTTS;
    private EditText mEditText;
    private SeekBar seekBarPitch;
    private SeekBar seekBarSpeed;
    private Button speakBtn;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speech_activity);
        drawer= findViewById(R.id.drawerlayout1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView= findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle( this, drawer,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.inbox);
        }

        speakBtn= findViewById(R.id.speak_btn);
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int speech = mTTS.setLanguage(Locale.ENGLISH);

                    if (speech == TextToSpeech.LANG_MISSING_DATA || speech ==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS","Language not supported");
                    }
                    else{
                        speakBtn.setEnabled(true);
                    }
                }
                else{
                    Log.e("TTS","Initiatialization failed");
                }
            }
        });

        mEditText = findViewById(R.id.speechtext);
        seekBarPitch= findViewById(R.id.seekbar_pitch);
        seekBarSpeed = findViewById(R.id.seekbar_speed);

        speakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

    }

    private void speak() {
        String text = mEditText.getText().toString();
        float pitch = (float) seekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;

        float speed = (float) seekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH,null);

    }

    @Override
    protected void onDestroy() {
        if (mTTS != null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.getHome:
                Intent intent = new Intent(SpeakText.this, MainActivity.class);
                startActivity(intent);
                break;


            case R.id.TextToSpeech:
                Intent intent2 = new Intent(SpeakText.this, SpeakText.class);
                startActivity(intent2);
                break;

            case R.id.inbox:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ViewTask())
                        .commit();
                break;

            case R.id.webview:
                Intent intent4 = new Intent(SpeakText.this, WebViewActivity.class);
                startActivity(intent4);
                break;

            case R.id.about:
                Intent intent3 = new Intent(SpeakText.this, About.class);
                startActivity(intent3);
                break;

            case R.id.share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.send:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:djdashize@gmail.com"));
                startActivity(emailIntent);
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
