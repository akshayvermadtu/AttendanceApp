package com.akshayvermadtugmail.dtusmartattender;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import static java.lang.String.valueOf;

public class edit_roll_list extends AppCompatActivity {
    private SQLiteDatabase db_net,dbx;
    private Cursor c;
    TextView textView;
    final Context context = this;
    String r_s;
    View promptsView;
    String t_net;
    RadioButton radioSexButton;
    RadioGroup radioSexGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_roll_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView=(TextView)findViewById(R.id.textView59);
        openDatabase();
        openDatabase2();

        Intent intent_pass = this.getIntent();
        String t_ = intent_pass.getExtras().getString("go");
        String t_1 = intent_pass.getExtras().getString("go_1");
        String t_2 = intent_pass.getExtras().getString("go_2");
        String t_3 = intent_pass.getExtras().getString("go_3");
        String t_ne =t_+"\t"+t_1+"\t"+t_2+"\t"+t_3;
        textView.setText(t_ne);


        String t_net =t_+t_1+t_2+t_3;

        final String SELECT_SQL = "SELECT * FROM " + t_net + " ORDER BY date DESC" ;
        c = dbx.rawQuery(SELECT_SQL, null);
        c.moveToFirst();

        showRecords();

    }


    protected void openDatabase() {
        dbx = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
    }

    protected void openDatabase2() {
        db_net = openOrCreateDatabase("db_net", Context.MODE_PRIVATE, null);
    }

    protected void showRecords() {

        Intent intent_pass = this.getIntent();
        String t_ = intent_pass.getExtras().getString("go");
        String t_1 = intent_pass.getExtras().getString("go_1");
        String t_2 = intent_pass.getExtras().getString("go_2");
        String t_3 = intent_pass.getExtras().getString("go_3");
        t_net =t_+t_1+t_2+t_3;

        LinearLayout ll = (LinearLayout)findViewById(R.id.edit_button_layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                (       LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

            if (c.moveToFirst()) {
                do {
                    final int a=0;
                    String name = c.getString(1);
                    String add = c.getString(2);
                    final String section = c.getString(3);
                    final Button btn = new Button(this);
                    btn.setId(a+100);
                    btn.setText(name  +"-"   + add + "-"   + section);
                    ll.addView(btn, lp);

                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(final View view) {

                            LayoutInflater lil = LayoutInflater.from(context);
                            promptsView = lil.inflate(R.layout.dialog_edit, null);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                            alertDialogBuilder.setView(promptsView);

                            radioSexGroup = (RadioGroup)promptsView.findViewById(R.id.radioSex);

                            r_s= (String) btn.getText();

                            final String da= new StringBuilder(r_s).reverse().toString();
                            final String daa=da.substring(0,10);
                            final String datee=new StringBuilder(daa).reverse().toString();


                            String roll = null;
                            char[] charArray = r_s.toCharArray();


                            if (Objects.equals(valueOf(charArray[1]),"-")){
                                roll = valueOf(charArray[0]);
                            }

                            else if (Objects.equals(valueOf(charArray[2]),"-")){
                                roll = valueOf(charArray[0])+ valueOf(charArray[1]);
                            }

                            else if (Objects.equals(valueOf(charArray[3]),"-")){
                                roll = valueOf(charArray[0])+ valueOf(charArray[1]) + valueOf(charArray[2]);
                            }

                            else if (Objects.equals(valueOf(charArray[4]),"-")){
                                roll = valueOf(charArray[0])+ valueOf(charArray[1]) + valueOf(charArray[2])+ valueOf(charArray[3]);
                            }

                            final TextView userInput = (TextView) promptsView
                                    .findViewById(R.id.textView_roll_no);
                            userInput.setText(r_s);

                            final ContentValues data = new ContentValues();
                            final ContentValues data2 = new ContentValues();
                            final String p="Present";
                            final String a="Absent";

                            final String s_r = datee.replace("/","_");

                            final String where2 = "id ='"+roll+"' " ;
                            final String where = "name ='"+roll+"' AND date ='"+ datee +"'" ;

                            alertDialogBuilder
                                    .setCancelable(false)
                                    .setPositiveButton("Save",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    int selectedId = radioSexGroup.getCheckedRadioButtonId();

                                                    switch(selectedId) {
                                                        case R.id.radioP:

                                                            data.put("address",p);
                                                            dbx.update(t_net,data, where, null);

                                                            for(int ll=1;ll<=1;ll++){
                                                                String sss= s_r +"__"+ll;
                                                                data2.put("'"+sss+"'",p);
                                                                db_net.update(t_net,data2, where2, null);
                                                            }

                                                            break;

                                                        case R.id.radioA:

                                                            data.put("address",a);
                                                            dbx.update(t_net,data, where, null);

                                                            for(int ll=1;ll<=1;ll++){
                                                                String sss= s_r+"__"+ll;
                                                                data2.put("'"+sss+"'",a);
                                                                db_net.update(t_net,data2, where2, null);
                                                            }

                                                            break;
                                                    }


                                                    Toast.makeText(getBaseContext(),"Record saved\nReopen to see changes", Toast.LENGTH_LONG).show();
                                                }
                                            })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();


                        }
                    });



                } while (c.moveToNext());
            }
            c.close();
    }



}
