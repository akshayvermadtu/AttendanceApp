package com.akshayvermadtugmail.dtusmartattender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class advanced_settings extends AppCompatActivity {
    Button c,d,e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        delete_record();
        edit_record();
        about_app();
    }

    public void delete_record(){
        c=(Button)findViewById(R.id.button24);
        c.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent("com.akshayvermadtugmail.dtusmartattender.Check");
                        startActivity(intent2);
                    }
                }
        );
    }

    public void edit_record(){
        d=(Button)findViewById(R.id.button28);
        d.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent4 = new Intent("com.akshayvermadtugmail.dtusmartattender.list_edit");
                        startActivity(intent4);
                    }
                }
        );
    }

    public void about_app(){
        e=(Button)findViewById(R.id.button29);
        e.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent5 = new Intent("com.akshayvermadtugmail.dtusmartattender.about_app");
                        startActivity(intent5);
                    }
                }
        );
    }


}
