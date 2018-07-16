package com.akshayvermadtugmail.dtusmartattender;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

public class Recent_batch_records extends AppCompatActivity {
    private SQLiteDatabase db2;
    private Cursor c;
    TextView textView,textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_batch_records);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView2=(TextView)findViewById(R.id.textView19);
        textView=(TextView)findViewById(R.id.recent_batch_record);

        openDatabase2();

        Intent intent_pass = this.getIntent();
        String t_ = intent_pass.getExtras().getString("go");
        String t_1 = intent_pass.getExtras().getString("go_1");
        String t_2 = intent_pass.getExtras().getString("go_2");
        String t_3 = intent_pass.getExtras().getString("go_3");
        String t_ne =t_+"\t"+t_1+"\t"+t_2+"\t"+t_3;
        textView2.setText(t_ne);


        String t_net =t_+t_1+t_2+t_3;

        final String SELECT_SQL = "SELECT * FROM " + t_net + " ORDER BY date DESC" ;
        c = db2.rawQuery(SELECT_SQL, null);
        c.moveToFirst();

       showRecords();

    }


    protected void openDatabase2() {
        db2 = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
    }

    protected void showRecords() {



        if (c.moveToFirst()) {
            do {
                String name = c.getString(1);
                String add = c.getString(2);
                String section = c.getString(3);
                textView.append(name + "\t"  +"-" + "\t" + add + "\t" + "-" + "\t" + section + "\n" + "\n");
                textView.setGravity(Gravity.CENTER);
            } while (c.moveToNext());
        }
        c.close();
    }

}
