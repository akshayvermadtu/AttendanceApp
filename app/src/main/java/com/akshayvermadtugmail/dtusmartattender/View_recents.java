package com.akshayvermadtugmail.dtusmartattender;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class View_recents extends AppCompatActivity {

    private SQLiteDatabase db2, db_net;
    private Cursor c;
    TextView textView;
    Button button, button2, button3;
    String table_name, ss,bb;
    int b,count_rows_no=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recents);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        openDatabase();
        openDatabase_db_net();

        textView = (TextView) findViewById(R.id.textView17);

        Intent intent_recents = this.getIntent();
        table_name = intent_recents.getExtras().getString("table_name");
        bb = intent_recents.getExtras().getString("amount_column");
        ss = intent_recents.getExtras().getString("date_column");

        final String SELECT_SQL = "SELECT * FROM " + table_name + " WHERE date LIKE  '" + ss + "' ";
        c = db2.rawQuery(SELECT_SQL, null);
        c.moveToFirst();

        final String SELECT_SQL2 = "SELECT * FROM " + table_name;
        Cursor c2 = db_net.rawQuery(SELECT_SQL2, null);
        c2.moveToFirst();
        b = c2.getColumnCount();

        String SELECT_SQL3 ="SELECT Count(*) FROM " + table_name;
        Cursor count_rows = db_net.rawQuery(SELECT_SQL3, null);
        count_rows.moveToFirst();

        if (count_rows.moveToFirst()) {
            do {
               count_rows_no++;
            } while (count_rows.moveToNext());
        }
        count_rows.close();

        showRecords();
        //write_excel();
        edit();
        done();
        show_absentees();
    }

    protected void openDatabase() {
        db2 = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
    }

    protected void openDatabase_db_net() {
        db_net = openOrCreateDatabase("db_net", Context.MODE_PRIVATE, null);
    }


    protected void showRecords() {

        if (c.moveToFirst()) {
            do {
                String name = c.getString(1);
                String add = c.getString(2);
                String date = c.getString(3);
                textView.append(name + "\t"  + "-" + "\t" + "\t" + add + "\t" + "-"  + "\t" + date + "\n" + "\n");
                textView.setGravity(Gravity.CENTER);
            } while (c.moveToNext());
        }
        c.close();

    }

    public void done() {
        button = (Button) findViewById(R.id.button7);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_main_return = new Intent("com.akshayvermadtugmail.dtusmartattender.Main");
                        startActivity(intent_main_return);
                    }
                }
        );
    }

    public void edit() {
        button = (Button) findViewById(R.id.button16);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_main_return = new Intent("com.akshayvermadtugmail.dtusmartattender.Edit_records");
                        intent_main_return.putExtra("table", table_name);
                        intent_main_return.putExtra("table_date", ss);
                        intent_main_return.putExtra("amount_column", bb);
                        startActivity(intent_main_return);
                    }
                }
        );
    }

    public void show_absentees() {
        button3 = (Button) findViewById(R.id.button10);
        button3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_show_a_return = new Intent("com.akshayvermadtugmail.dtusmartattender.show_absentees");
                        intent_show_a_return.putExtra("table", table_name);
                        intent_show_a_return.putExtra("table_date", ss);
                        intent_show_a_return.putExtra("amount_column", bb);
                        startActivity(intent_show_a_return);
                    }
                }
        );
    }



}




