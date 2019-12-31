package com.example.groupproject;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Element element = new Element();
        element.setTitle("About");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.logo3)
                .setDescription("This is a demo version")
                .addItem(new Element().setTitle("Version 1.0"))
                .addItem(element)
                .addGroup("Connect with us")
                .addEmail("dripgangpromo@gmail.com")
                .addFacebook("Drip Gang Promo")
                .addItem(createCopyright())
                .create();

            setContentView(aboutPage);

    }

    private Element createCopyright() {
       final  Element copyright= new Element();
       final String copyrightString  = String.format("Copyright  %d by BSC", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setIcon(R.mipmap.ic_launcher);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(About.this,copyrightString,Toast.LENGTH_SHORT).show();
            }
        });
        return copyright;
    }
}
