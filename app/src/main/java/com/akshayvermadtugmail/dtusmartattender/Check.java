package com.akshayvermadtugmail.dtusmartattender;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Check extends AppCompatActivity {
    private SQLiteDatabase db_batch;
    private Cursor c;
    final Context context = this;
    Button button;
    String table_n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
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
            LinearLayout ll = (LinearLayout)findViewById(R.id.button_layout_delete);
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

        delete_all();

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

        LinearLayout ll = (LinearLayout)findViewById(R.id.button_layout_delete);
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

                String net=name+"\t"+add+"\t"+section+"\t"+sectionx;
                table_n=name+add+section+sectionx;


                Button btn = new Button(this);
                btn.setId(i+15);

                btn.setText(net);
                ll.addView(btn, lp);

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        //Toast.makeText(recents.this, "Btn1", Toast.LENGTH_SHORT).show();
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.dialog__layout3, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setView(promptsView);

                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {

                                                SQLiteDatabase sdb;
                                                sdb=openOrCreateDatabase("PersonDB", Context.MODE_WORLD_WRITEABLE, null);
                                                sdb.execSQL("DROP TABLE IF EXISTS " + table_n);

                                                SQLiteDatabase sdb2;
                                                sdb2=openOrCreateDatabase("batch_name", Context.MODE_WORLD_WRITEABLE, null);
                                                sdb2.execSQL("DELETE FROM batch_name WHERE name='"+name+"' AND address='"+add+"' AND section='"+section+"' AND sectionx='"+sectionx+"' " );

                                                SQLiteDatabase sdb3;
                                                sdb3=openOrCreateDatabase("student_id", Context.MODE_WORLD_WRITEABLE, null);
                                                sdb3.execSQL("DROP TABLE IF EXISTS " + table_n);

                                                SQLiteDatabase sdb4;
                                                sdb4=openOrCreateDatabase("student_total", Context.MODE_WORLD_WRITEABLE, null);
                                                sdb4.execSQL("DROP TABLE IF EXISTS student_total");

                                                SQLiteDatabase sdb5;
                                                sdb5=openOrCreateDatabase("db_net", Context.MODE_WORLD_WRITEABLE, null);
                                                sdb5.execSQL("DROP TABLE IF EXISTS " + table_n);

                                                Toast.makeText(Check.this,table_n + " Record has been deleted",Toast.LENGTH_SHORT).show();
                                                Intent intent6 = new Intent("com.akshayvermadtugmail.dtusmartattender.Check");
                                                startActivity(intent6);

                                            }
                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();
                                            }
                                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                });

                i++;
            } while (c.moveToNext());
        }
        c.close();

    }

    public void delete_all(){
        button=(Button) findViewById(R.id.button25);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater lii = LayoutInflater.from(context);
                        View promptsView = lii.inflate(R.layout.dialog_all, null);

                        AlertDialog.Builder alertDialogBuilderx = new AlertDialog.Builder(context);
                        alertDialogBuilderx.setView(promptsView);

                        alertDialogBuilderx
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                // get user input and set it to result
                                                // edit text
                                                context.deleteDatabase("batch_name");
                                                context.deleteDatabase("student_total");
                                                context.deleteDatabase("PersonDB");
                                                context.deleteDatabase("student_id");
                                                context.deleteDatabase("db_net");
                                                Toast.makeText(Check.this,"All data has been deleted",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent("com.akshayvermadtugmail.dtusmartattender.Check");
                                                startActivity(intent);
                                            }
                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();
                                            }
                                        });

                        AlertDialog alertDialogx = alertDialogBuilderx.create();
                        alertDialogx.show();
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

        Intent setIntent = new Intent("com.akshayvermadtugmail.dtusmartattender.Main");
        startActivity(setIntent);
    }

}
