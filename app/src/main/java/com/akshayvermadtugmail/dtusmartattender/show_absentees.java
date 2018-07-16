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

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class show_absentees extends AppCompatActivity {
    private SQLiteDatabase db2;
    private Cursor c;
    TextView textView;
    Button button,button2;
    String table_name,ss,bb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_absentees);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        openDatabase();

        textView = (TextView) findViewById(R.id.textView55);

        Intent intent_show_a_return = this.getIntent();
        table_name = intent_show_a_return.getExtras().getString("table");
        ss = intent_show_a_return.getExtras().getString("table_date");
        bb2 = intent_show_a_return.getExtras().getString("amount_column");

        final String SELECT_SQL = "SELECT * FROM " +  table_name + " WHERE date LIKE  '"+ss+"' ";

        c = db2.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        showRecords();

        edit();
        done();

    }

    protected void openDatabase() {
        db2 = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
    }

    protected void showRecords() {

        if (c.moveToFirst()) {
            do {
                String name = c.getString(1);
                String add = c.getString(2);
                if (add.equals("Absent")){
                    textView.append( "Roll No. :- "+name + "\n" + "\n");
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(COMPLEX_UNIT_SP, 24);
                }

            } while (c.moveToNext());
        }
        c.close();

    }

    public void edit(){
        button2=(Button)findViewById(R.id.button26);
        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_main_return = new Intent("com.akshayvermadtugmail.dtusmartattender.Edit_records");
                        intent_main_return.putExtra("table",table_name);
                        intent_main_return.putExtra("table_date",ss);
                        intent_main_return.putExtra("amount_column",bb2);
                        startActivity(intent_main_return);
                    }
                }
        );
    }

    public void done(){
        button=(Button)findViewById(R.id.button27);
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




}
