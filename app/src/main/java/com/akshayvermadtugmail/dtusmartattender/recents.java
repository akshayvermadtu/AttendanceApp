package com.akshayvermadtugmail.dtusmartattender;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class recents extends AppCompatActivity {
    private SQLiteDatabase db_batch;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recents);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Boolean b;
        b=doesDatabaseExist();

        if(b)
        {
            openDatabase();

            String query2 = "SELECT * FROM batch_name ORDER BY date DESC";
            c = db_batch.rawQuery(query2, null);
            c.moveToFirst();

            showRecords();
        }

        else
        {
            Toast.makeText(this,"no record available",Toast.LENGTH_LONG).show();
            LinearLayout ll = (LinearLayout)findViewById(R.id.button_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                    (       LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
            lp.gravity = Gravity.CENTER;
            ImageView imageView = new ImageView(this);
            int xxx=10;
            imageView.setId(xxx);
            imageView.setBackground(getResources().getDrawable(R.drawable.na_icon));
            ll.addView(imageView, lp);
        }

    }

    private boolean doesDatabaseExist() {
        SQLiteDatabase checkDB;
        boolean checkFlag = true;
        try{
            checkDB = SQLiteDatabase.openDatabase("data/data/com.akshayvermadtugmail.dtusmartattender/databases/batch_name", null,
                    SQLiteDatabase.OPEN_READWRITE);
            checkDB.close();
        }
        catch(SQLiteException sqlException){
            checkFlag=false;
        }
        return checkFlag;
    }

    protected void openDatabase() {
        db_batch = openOrCreateDatabase("batch_name", Context.MODE_PRIVATE, null);
    }


    protected void showRecords() {

        int i=0;

        LinearLayout ll = (LinearLayout)findViewById(R.id.button_layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                (       LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        if (c.moveToFirst()) {
            do {
                final String name = c.getString(1);
                final String add = c.getString(2);
                final String section = c.getString(3);
                final String sectionx = c.getString(4);

                String net=name+"\t"+add+"\t"+ "\t"+ section+"\t"+ "\t"+ sectionx;


                Button btn = new Button(this);
                btn.setId(i+15);

                btn.setText(net);
                ll.addView(btn, lp);

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        //Toast.makeText(recents.this, "Btn1", Toast.LENGTH_SHORT).show();

                        Intent intent_pass = new Intent("com.akshayvermadtugmail.dtusmartattender.Recent_batch_records");
                        intent_pass.putExtra("go",name);
                        intent_pass.putExtra("go_1",add);
                        intent_pass.putExtra("go_2",section);
                        intent_pass.putExtra("go_3",sectionx);
                        startActivity(intent_pass);
                    }
                });

                i++;
            } while (c.moveToNext());
        }
        c.close();

    }

}
