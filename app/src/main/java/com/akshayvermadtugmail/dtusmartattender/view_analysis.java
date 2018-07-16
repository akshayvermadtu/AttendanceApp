package com.akshayvermadtugmail.dtusmartattender;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class view_analysis extends AppCompatActivity {
    private SQLiteDatabase db2,db3;
    private Cursor c;
    TextView textView;
    Button button;
    float i,j;
    float k;
    int n;
    int initial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_analysis);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView=(TextView)findViewById(R.id.textView24);

        openDatabase2();
        openDatabase3();


        Intent intent_pass = this.getIntent();
        String t_ = intent_pass.getExtras().getString("go");
        String t_1 = intent_pass.getExtras().getString("go_1");
        String t_2 = intent_pass.getExtras().getString("go_2");
        String t_3 = intent_pass.getExtras().getString("go_3");

        String t_ne=t_1+"\t"+t_2+"\t"+t_3;

        String t_net=t_1+t_2+t_3;
        String t_n=t_+t_1+t_2+t_3;


        textView.setText(t_ne);

        final String SELECT_SQL2 = "SELECT * FROM student_total" ;
        Cursor c2 = db3.rawQuery(SELECT_SQL2, null);
        c2.moveToFirst();

        if (c2.moveToFirst()) {
            do {
                String name = c2.getString(0);
                if (name.equals(t_n))
                {
                    n = c2.getInt(1);
                    initial = c2.getInt(2);
                    break;
                }
            } while (c2.moveToNext());
        }
        c2.close();


        final String SELECT_SQL = "SELECT * FROM " + t_n + " ORDER BY date DESC" ;
        c = db2.rawQuery(SELECT_SQL, null);
        c.moveToFirst();

        showRecords();
        done();
        dbstudent_id();
    }

    protected void openDatabase2() {
        db2 = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
    }

    protected void openDatabase3() {
        db3 = openOrCreateDatabase("student_total", Context.MODE_PRIVATE, null);
    }

    protected void showRecords() {

        final Intent intent_pass = this.getIntent();
        String t_ = intent_pass.getExtras().getString("go");
        String t_1 = intent_pass.getExtras().getString("go_1");
        String t_2 = intent_pass.getExtras().getString("go_2");
        String t_3 = intent_pass.getExtras().getString("go_3");
        final String t_net=t_+t_1+t_2+t_3;
        final String t_net2=t_1+"\t"+t_2+"\t"+t_3;

        LinearLayout ll = (LinearLayout)findViewById(R.id.button_layout2);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                (       LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        for (int a=initial;a<(n+initial);a++) {
            i=0;
            j=0;
            final String b=Integer.toString(a);
            if (c.moveToFirst()) {
                    do {
                        String name = c.getString(1);
                         String add = c.getString(2);
                        if (name.equals(b)) {
                            i++;
                            if (add.equals("Present")) {
                                j++;
                            }
                        }

                    } while (c.moveToNext());
            }
            k = (j/i)*100;
            float z=Math.round(k);
            final String percentage = Float.toString(z);
            Button btn = new Button(this);
            btn.setId(a);
            btn.setText(b+ "\t" + "\t" + "\t" + "\t" +"\t" + "\t" + "\t" + "\t" + "\t"+ "\t" + "\t" + percentage );
            ll.addView(btn, lp);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Intent intent_pass2 = new Intent("com.akshayvermadtugmail.dtusmartattender.per_student_analysis");
                    intent_pass2.putExtra("go_a",t_net2);
                    intent_pass2.putExtra("go_b",b);
                    intent_pass2.putExtra("go_c",percentage);
                    intent_pass2.putExtra("go_d",t_net);
                    intent_pass2.putExtra("go_e",n+initial);
                    startActivity(intent_pass2);
                }
            });

        }
        c.close();
    }

    public void done(){
        button=(Button)findViewById(R.id.button8);
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

    protected void dbstudent_id(){
        final Intent intent_pass = this.getIntent();
        String t_ = intent_pass.getExtras().getString("go");
        String t_1 = intent_pass.getExtras().getString("go_1");
        String t_2 = intent_pass.getExtras().getString("go_2");
        String t_3 = intent_pass.getExtras().getString("go_3");
        final String t_net=t_+t_1+t_2+t_3;
        String an ="other";
        String an_net=t_net+an;
        SQLiteDatabase student_id_db = openOrCreateDatabase("student_id", Context.MODE_PRIVATE, null);
        student_id_db.execSQL("CREATE TABLE IF NOT EXISTS " + an_net +"(roll VARCHAR,id VARCHAR);");
    }


}
