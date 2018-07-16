package com.akshayvermadtugmail.dtusmartattender;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.akshayvermadtugmail.dtusmartattender.R.id.s;

public class new_roll extends AppCompatActivity implements GestureDetector.OnGestureListener  {
    TextView textView,textView2,textView3,textView4,textView5,textView6,textView7;
    int n,i,j;
    private GestureDetector gestureDetector;
    Button button,button2;
    private SQLiteDatabase db2,db_batch,db3,db_net;
    Cursor c;
    int b,a;
    final Context context = this;
    String s1,s2,s3,s4,s5,s6,s7,sx;
    int nn=1,id_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_roll);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        this.gestureDetector=new GestureDetector(this,this);

        textView=(TextView)findViewById(R.id.y_name);
        textView2=(TextView)findViewById(R.id.b_name);
        textView3=(TextView)findViewById(R.id.s_name);
        textView4=(TextView)findViewById(R.id.roll_no);
        textView5=(TextView)findViewById(R.id.lrns);
        textView6=(TextView)findViewById(s);
        textView7=(TextView)findViewById(R.id.textView13);

        Intent intent6 = this.getIntent();
        s1=intent6.getExtras().getString("go");
        s2=intent6.getExtras().getString("go2");
        s3=intent6.getExtras().getString("go3");
        s4=intent6.getExtras().getString("go4");
        s5=intent6.getExtras().getString("go5");
        s6=intent6.getExtras().getString("go6");
        s7=intent6.getExtras().getString("go7");
        sx=intent6.getExtras().getString("go8");

        textView.setText(s1);
        textView2.setText(s2);

        String s8=s3+s7;
        textView3.setText(s8);
        n = Integer.parseInt(s4);
        i = Integer.parseInt(s5);
        id_no=i;
        textView7.setText(s6);
        b = Integer.parseInt(sx);

        j=i;
        textView4.setText(""+j);

        next();
        save_data();
        createDatabase();
        openDatabase2();
        openDatabase3();

        boolean bo=does_record_exists();
        if (bo){
            insertIntoDB2();
        }
        createDatabase_net();

        for(a=1;a<=b;a++){
            insertInto_db_net();
        }

        insertIntoDB3();
    }


    public void save_data(){
        button=(Button)findViewById(R.id.button9);
        final String new_table_name = s1+s2+s3+s7;

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Toast.makeText(new_roll.this,"Record has been saved",Toast.LENGTH_SHORT).show();
                        Intent intent_recents = new Intent("com.akshayvermadtugmail.dtusmartattender.View_recents");
                        intent_recents.putExtra("table_name",new_table_name);
                        intent_recents.putExtra("date_column",s6);
                        intent_recents.putExtra("amount_column",sx);
                        startActivity(intent_recents);
                    }
                }

        );
    }

    public void next(){
        button2=(Button)findViewById(R.id.button15);
        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(j>(n+i-1))
                        {
                            Toast.makeText(new_roll.this, "You have reached the End", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            LayoutInflater li = LayoutInflater.from(context);
                            View promptsView = li.inflate(R.layout.layout_next, null);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                            alertDialogBuilder.setView(promptsView);

                            alertDialogBuilder
                                    .setCancelable(false)
                                    .setPositiveButton("YES",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    Toast.makeText(new_roll.this, "Roll no." + "\t" + j + "\t" + "\t" + " skipped", Toast.LENGTH_SHORT).show();
                                                    if(j>=(n+i-1))
                                                    {
                                                        j++;
                                                        textView4.setText("End");
                                                    }
                                                    else
                                                    {
                                                        j++;
                                                        String xx = Integer.toString(j);
                                                        textView4.setText(xx);
                                                    }
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
                    }
                }

        );
    }


    @Override
    public boolean onDown(MotionEvent e) {

           return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        if(j==(n+i-1))
        {
            j++;
            textView4.setText("End");
            textView5.setText(""+ (n+i-1));
            textView6.setText("Present");
            textView6.setTextColor(Color.BLUE);
            for(a=1;a<=b;a++){
                insertIntoDB();
            }
            insertInto_db_net_column();
            return true;
        }

        else if (j<(n+i-1))
        {
            j++;
            textView4.setText("" +j);
            textView5.setText(""+ (j-1) );
            textView6.setText("Present");
            textView6.setTextColor(Color.BLUE);
            for(a=1;a<=b;a++){
                insertIntoDB();
            }
            insertInto_db_net_column();
            return true;
        }

        else
            return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {}

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if(j==(n+i-1))
        {
            j++;
            textView4.setText("End");
            textView5.setText(""+ (n+i-1));
            textView6.setText("Absent");
            textView6.setTextColor(Color.RED);
            for(a=1;a<=b;a++){
                insertIntoDB();
            }
            insertInto_db_net_column();
            return true;

        }



        else if (j<(n+i-1))
        {
            j++;
            textView4.setText("" +j);
            textView5.setText(""+ (j-1) );
            textView6.setText("Absent");
            textView6.setTextColor(Color.RED);
            for(a=1;a<=b;a++){
                insertIntoDB();
            }
            insertInto_db_net_column();
            return true;
        }

        else
            return false;
    }

        @Override
    public boolean onTouchEvent(MotionEvent event) {
            this.gestureDetector.onTouchEvent(event);
            return j > (n + i - 1) || super.onTouchEvent(event);
        }


    protected void createDatabase(){
        Intent intent6 = this.getIntent();
        s1=intent6.getExtras().getString("go");
        s2=intent6.getExtras().getString("go2");
        s3=intent6.getExtras().getString("go3");
        s7=intent6.getExtras().getString("go7");
        String new_table_name = s1+s2+s3+s7;
        db2=openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS " + new_table_name  + " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR,address VARCHAR,date BLOB);");

    }



    protected void createDatabase_net(){
        Intent intent6 = this.getIntent();
        s1=intent6.getExtras().getString("go");
        s2=intent6.getExtras().getString("go2");
        s3=intent6.getExtras().getString("go3");
        s7=intent6.getExtras().getString("go7");
        String new_table_name = s1+s2+s3+s7;
        db_net = openOrCreateDatabase("db_net", Context.MODE_PRIVATE, null);
        db_net.execSQL("CREATE TABLE IF NOT EXISTS " + new_table_name + " (id INTEGER PRIMARY KEY AUTOINCREMENT);" );
    }


    protected void insertIntoDB(){
        Intent intent6 = this.getIntent();
        s1=intent6.getExtras().getString("go");
        s2=intent6.getExtras().getString("go2");
        s3=intent6.getExtras().getString("go3");
        s7=intent6.getExtras().getString("go7");
        String new_table_name = s1+s2+s3+s7;
        String name = textView5.getText().toString().trim();
        String add = textView6.getText().toString().trim();
        if(name.equals("") || add.equals("")){
            Toast.makeText(getApplicationContext(),"Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }
        String query = "INSERT INTO " +  new_table_name + " (name,address,date) VALUES('"+name+"', '"+add+"', '"+s6+"');";
        db2.execSQL(query);
    }


    protected void insertInto_db_net(){

        Intent intent6 = this.getIntent();
        s1=intent6.getExtras().getString("go");
        s2=intent6.getExtras().getString("go2");
        s3=intent6.getExtras().getString("go3");
        s7=intent6.getExtras().getString("go7");
        String new_table_name = s1+s2+s3+s7;
        String s_r=s6.replace("/","_"); ;
        String sss= s_r+"__"+nn;
        String query= "ALTER TABLE "
                + new_table_name + " ADD COLUMN  '"+ sss +"' VARCHAR;";
        db_net.execSQL(query);
        nn++;
    }

    protected void insertInto_db_net_column(){
        Intent intent6 = this.getIntent();
        s1=intent6.getExtras().getString("go");
        s2=intent6.getExtras().getString("go2");
        s3=intent6.getExtras().getString("go3");
        s7=intent6.getExtras().getString("go7");
        String new_table_name = s1+s2+s3+s7;
        String name = textView5.getText().toString().trim();
        String add = textView6.getText().toString().trim();

        for(int ll=1;ll<=b;ll++){
            String s_r=s6.replace("/","_"); ;
            String sss= s_r+"__"+ll;
            //String ssss= s_r+"_"+1;
            String col_check= "SELECT * FROM " +  new_table_name ;
            Cursor cx2 = db_net.rawQuery(col_check, null);
            cx2.moveToFirst();
            int countz=cx2.getColumnCount();

            if(cx2.getColumnName(countz-(b+1)).equals("id")&& ll==1){
                String query2 = "INSERT INTO " + new_table_name + " (id,'" + sss + "') VALUES('" + id_no + "','" + add + "');";
                db_net.execSQL(query2);
            }

            else {
                ContentValues data = new ContentValues();
                String where = "id ='"+id_no+"'" ;
                data.put("'"+sss+"'",add);
                db_net.update(new_table_name,data, where, null);
            }

        }

        id_no++;
    }

    protected void openDatabase2() {
        db_batch = openOrCreateDatabase("batch_name", Context.MODE_PRIVATE, null);
    }

    protected void insertIntoDB2(){
        Intent intent6 = this.getIntent();
        s1=intent6.getExtras().getString("go");
        s2=intent6.getExtras().getString("go2");
        s3=intent6.getExtras().getString("go3");
        s7=intent6.getExtras().getString("go7");
        //date down
        String s4=intent6.getExtras().getString("go6");

        String query = "INSERT INTO batch_name (name,address,section,sectionx,date) VALUES('"+s1+"', '"+s2+"', '"+s3+"', '"+s7+"', '"+s4+"');";
        db_batch.execSQL(query);

    }

    protected void openDatabase3() {
        db3 = openOrCreateDatabase("student_total", Context.MODE_PRIVATE, null);
    }

    protected void insertIntoDB3(){
        Intent intent6 = this.getIntent();
        String s1=intent6.getExtras().getString("go");
        String s2=intent6.getExtras().getString("go2");
        String s3=intent6.getExtras().getString("go3");
        String s4=intent6.getExtras().getString("go7");
        String name=s1+s2+s3+s4;

        String total=intent6.getExtras().getString("go4");
        int n =Integer.parseInt(total);
        String initial=intent6.getExtras().getString("go5");
        int i=Integer.parseInt(initial);


        String query = "INSERT INTO student_total (name,s,initial) VALUES('"+name+"','"+n+"', '"+i+"');";
        db3.execSQL(query);

    }

    private boolean does_record_exists(){
        openDatabase2();
        Intent intent6 = this.getIntent();
        s1=intent6.getExtras().getString("go");
        s2=intent6.getExtras().getString("go2");
        s3=intent6.getExtras().getString("go3");
        s4=intent6.getExtras().getString("go7");
        boolean check_flag=true;

        String query2 = "SELECT * FROM batch_name ORDER BY date DESC";
        c = db_batch.rawQuery(query2, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            do {
                String name = c.getString(1);
                String add = c.getString(2);
                String date = c.getString(3);
                String datex = c.getString(4);

                if (name.equals(s1)&&add.equals(s2)&&date.equals(s3) &&datex.equals(s4))
                {
                    check_flag=false;
                }

            } while (c.moveToNext());
        }
        c.close();
        return check_flag;
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

        Intent intent6 = this.getIntent();
        s1=intent6.getExtras().getString("go");
        s2=intent6.getExtras().getString("go2");
        s3=intent6.getExtras().getString("go3");
        s7=intent6.getExtras().getString("go7");
        final String new_table_name = s1+s2+s3+s7;

        final SQLiteDatabase sdb5;
        sdb5=openOrCreateDatabase("db_net", Context.MODE_WORLD_WRITEABLE, null);

        LayoutInflater lii = LayoutInflater.from(context);
        View promptsView = lii.inflate(R.layout.dialog_warning, null);

        AlertDialog.Builder alertDialogBuilderx = new AlertDialog.Builder(context);
        alertDialogBuilderx.setView(promptsView);

        alertDialogBuilderx
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                sdb5.execSQL("DROP TABLE IF EXISTS " +  new_table_name);
                                Intent setIntent = new Intent("com.akshayvermadtugmail.dtusmartattender.new_record");
                                startActivity(setIntent);
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialogx = alertDialogBuilderx.create();
        alertDialogx.show();


    }

}
