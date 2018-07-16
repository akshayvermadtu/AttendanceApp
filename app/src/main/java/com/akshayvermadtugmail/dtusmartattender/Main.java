package com.akshayvermadtugmail.dtusmartattender;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Main extends AppCompatActivity {
    Button c,d,e,f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        next_new();
        next_recents();
        next_analyse();
        add_link();
    }


    public void next_new(){
        c=(Button)findViewById(R.id.button3);
        c.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent("com.akshayvermadtugmail.dtusmartattender.new_record");
                        startActivity(intent2);
                    }
                }
        );
    }

    public void next_recents(){
        d=(Button)findViewById(R.id.button4);
        d.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent4 = new Intent("com.akshayvermadtugmail.dtusmartattender.recents");
                        startActivity(intent4);
                    }
                }
        );
    }

    public void next_analyse(){
        e=(Button)findViewById(R.id.button5);
        e.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent5 = new Intent("com.akshayvermadtugmail.dtusmartattender.Analyse");
                        startActivity(intent5);
                    }
                }
        );
    }


    public void add_link(){
        f=(Button)findViewById(R.id.button6);
        f.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent6 = new Intent("com.akshayvermadtugmail.dtusmartattender.Link");
                        startActivity(intent6);
                    }
                }
        );
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Intent intent6 = new Intent("com.akshayvermadtugmail.dtusmartattender.advanced_settings");
                startActivity(intent6);
                break;

            default:
                break;
        }

        return true;
    }


}
