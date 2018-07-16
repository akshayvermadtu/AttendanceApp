package com.akshayvermadtugmail.dtusmartattender;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class per_student_analysis extends AppCompatActivity {
    ProgressBar p;
    TextView textView,textView2,textView3,textView4,textView5;
    private SQLiteDatabase db2,student_total;
    private Cursor c,c2;
    Float f;
    int i,x;
    Button b1,b2;
    final Context context = this;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_student_analysis);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        p=(ProgressBar)findViewById(R.id.progressBar3);
        textView=(TextView)findViewById(R.id.textView27);
        textView2=(TextView)findViewById(R.id.textView34);
        textView3=(TextView)findViewById(R.id.textView36);
        textView4=(TextView)findViewById(R.id.textView37);
        textView5=(TextView)findViewById(R.id.textView39);
        result = (TextView) findViewById(R.id.textView40);

        Intent intent_pass2 = this.getIntent();
        String t1_net = intent_pass2.getExtras().getString("go_a");
        String t2_net = intent_pass2.getExtras().getString("go_b");
        String t3_net = intent_pass2.getExtras().getString("go_c");
        String t_net = intent_pass2.getExtras().getString("go_d");
        String t = intent_pass2.getExtras().getString("go_e");

        String an ="other";
        String an_net=t_net+an;

        textView.setText(t1_net);
        textView2.setText(t2_net);

        textView3.setText(t3_net);
        textView4.setText(t3_net+"%");

        f=Float.valueOf(t3_net);
        i=Math.round(f);

        String color = colorDecToHex(34,139,34);

        final float[] roundedCorners = new float[] { 2, 2, 2, 2, 2, 2, 2, 2 };
        ShapeDrawable pgDrawable = new ShapeDrawable(new RoundRectShape(roundedCorners,null,null));

        pgDrawable.getPaint().setColor(Color.parseColor(color));



        ClipDrawable progress = new ClipDrawable(pgDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        p.setProgressDrawable(progress);


        ObjectAnimator animation = ObjectAnimator.ofInt(p, "progress", p.getProgress(), i);
        animation.setDuration(3000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        openDatabase2();
        add_link();
        noti();

        Boolean b;
        b=doesDatabaseExist();

        if(b)
        {
            opendb();
            final String SELECT_SQL2 = "SELECT * FROM " + an_net;
            c2 = student_total.rawQuery(SELECT_SQL2, null);
            c2.moveToFirst();
            showRecords2();
        }

        else
        {
            result.setText("Not available");
        }


        final String SELECT_SQL = "SELECT * FROM " + t_net + " ORDER BY date DESC" ;
        c = db2.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        showRecords();

    }

    protected void openDatabase2() {
        db2 = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
    }

    protected void showRecords() {
        Intent intent_pass2 = this.getIntent();
        String t2_net = intent_pass2.getExtras().getString("go_b");

        if (c.moveToFirst()) {
            do {
                String name = c.getString(1);
                if (name.equals(t2_net))
                {
                    String add = c.getString(2);
                    String date = c.getString(3);
                    textView5.append( "\t" +  "\t"  + "\t" + "\t" +  "\t" + "\t" + date + "\t" + "\t" +  "\t" + add +"\n" +"\n");
                }

            } while (c.moveToNext());
        }
        c.close();

    }

    public void add_link(){
        b1=(Button)findViewById(R.id.button17);
        b1.setOnClickListener(
                new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.dialog_layout, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setView(promptsView);

                        final EditText userInput = (EditText) promptsView
                                .findViewById(R.id.editTextDialogUserInput);

                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                // get user input and set it to result
                                                // edit text
                                                result.setText(userInput.getText());
                                                insertIntodb();
                                            }
                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();
                                            }
                                        });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
                }
        );
    }

    public void noti(){
        b2=(Button)findViewById(R.id.button18);
        b2.setOnClickListener(
                new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        String s = result.getText().toString();
                        if (!s.equals("No ID available")) {
                            Toast.makeText(per_student_analysis.this, "Sending mail", Toast.LENGTH_LONG).show();
                            sendEmail();
                        }

                        else
                            Toast.makeText(per_student_analysis.this, "Add student's ID first", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public static String colorDecToHex(int p_red, int p_green, int p_blue)
    {
        String red = Integer.toHexString(p_red);
        String green = Integer.toHexString(p_green);
        String blue = Integer.toHexString(p_blue);

        if (red.length() == 1)
        {
            red = "0" + red;
        }
        if (green.length() == 1)
        {
            green = "0" + green;
        }
        if (blue.length() == 1)
        {
            blue = "0" + blue;
        }

        String colorHex = "#" + red + green + blue;
        return colorHex;
    }

    protected void opendb() {
        student_total = openOrCreateDatabase("student_id", Context.MODE_PRIVATE, null);
    }

    protected void insertIntodb(){
        Intent intent_pass2 = this.getIntent();
        String t_net = intent_pass2.getExtras().getString("go_d");
        String an ="other";
        String an_net=t_net+an;
        String t2_net = intent_pass2.getExtras().getString("go_b");
        String s = result.getText().toString();
        String query = "INSERT INTO " + an_net + " (roll,id) VALUES('"+t2_net+"','"+s+"');";
        student_total.execSQL(query);
    }

    protected void showRecords2() {

        Intent intent_pass2 = this.getIntent();
        String t2_net = intent_pass2.getExtras().getString("go_b");

        if (c2.moveToFirst()) {
            do {
                String first=c2.getString(0);
                if (first.equals(t2_net))
                {
                    String add = c2.getString(1);
                    result.setText(add);
                }

            } while (c2.moveToNext());
        }
        c2.close();
    }

    private boolean doesDatabaseExist() {
        SQLiteDatabase checkDB;
        boolean checkFlag = true;
        try{
            checkDB = SQLiteDatabase.openDatabase("data/data/com.akshayvermadtugmail.dtusmartattender/databases/student_id", null,
                    SQLiteDatabase.OPEN_READWRITE);
            checkDB.close();
        }
        catch(SQLiteException sqlException){
            checkFlag=false;
        }
        return checkFlag;
    }

    protected void sendEmail() {

        String rec=textView5.getText().toString();
        Intent intent_pass2 = this.getIntent();
        String t1_net = intent_pass2.getExtras().getString("go_a");
        String t2_net = intent_pass2.getExtras().getString("go_b");
        String t3_net = intent_pass2.getExtras().getString("go_c");

        String s = result.getText().toString();
       // String message = "Your Batch :-" + t1_net +"\t" +"Roll no. :-" + t2_net + "\t" + "Attendance %" + t3_net +"\t"+ "Record History" + rec;

        String message2 = "";
        message2 += "\n" + "Your Batch :-" +"\t" + t1_net;
        message2 += "\n" + "Roll no. :-" +"\t"  + t2_net;
        message2 += "\n" +  "Attendance %"+ "\t"  + t3_net;
        message2 += "\n" +  "Record History" + "\t" + rec;


        String[] TO = {s};
       // String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse(s));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"DTU Smart Attender :- Attendance details");
        emailIntent.putExtra(Intent.EXTRA_TEXT,message2);


        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(per_student_analysis.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
