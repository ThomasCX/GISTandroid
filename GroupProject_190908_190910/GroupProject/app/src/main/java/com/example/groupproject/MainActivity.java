package com.example.groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG ="MainActivity";

    private FloatingActionButton fab;
    private DrawerLayout drawer;
    final ArrayList<String> list = new ArrayList<>();
    private int priorityCount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer= findViewById(R.id.drawerlayout);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, new ViewTask());
            fragmentTransaction.commit();
        }

        NavigationView navigationView= findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle( this, drawer,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.inbox);
        }


        fab = findViewById(R.id.fab);


        fab.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AddTask())
                        .commit();

            }
        });


    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.getHome:
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;


            case R.id.TextToSpeech:
                Intent intent2 = new Intent(MainActivity.this, SpeakText.class);
                startActivity(intent2);
                break;

            case R.id.inbox:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ViewTask())
                        .commit();
                break;


            case R.id.webview:
                Intent intent4 = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(intent4);
                break;

            case R.id.about:
                Intent intent3 = new Intent(MainActivity.this, About.class);
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

    public void onSelectPriority(View view) {
        if (((RadioButton) findViewById(R.id.rButton1)).isChecked()) {
            priorityCount = 1;
        } else if (((RadioButton) findViewById(R.id.rButton2)).isChecked()) {
            priorityCount = 2;
        } else if (((RadioButton) findViewById(R.id.rButton3)).isChecked()) {
            priorityCount = 3;
        }
    }


}
