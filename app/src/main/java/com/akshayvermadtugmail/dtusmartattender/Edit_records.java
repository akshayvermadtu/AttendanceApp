package com.akshayvermadtugmail.dtusmartattender;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Edit_records extends AppCompatActivity implements View.OnClickListener {
    Button button,button2,button3,b1,b2;
    EditText e1;
    private SQLiteDatabase dbx,db_net;
    Cursor cx,cx2;
    String s,bbb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_records);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        e1 = (EditText) findViewById(R.id.editText6);

        b1=(Button)findViewById(R.id.button22);
        b2=(Button)findViewById(R.id.button23);

        openDatabase();
        openDatabase2();

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

        button_click();

        edit_save();
        open_main();

    }

    protected void openDatabase() {
        dbx = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
    }

    protected void openDatabase2() {
        db_net = openOrCreateDatabase("db_net", Context.MODE_PRIVATE, null);
    }


    public void button_click(){
        button3=(Button)findViewById(R.id.button21);
        button3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showRecords();
                    }
                }
        );
    }

    protected void showRecords() {

        Intent intent_main_return = this.getIntent();
        final String table_name = intent_main_return.getExtras().getString("table");

        final String SELECT_SQL = "SELECT * FROM " +  table_name + " ORDER BY date DESC";

        cx2 = dbx.rawQuery(SELECT_SQL, null);
        cx2.moveToFirst();

        String s=e1.getText().toString();

        if (e1 != null && s.length()>0) {
            if (cx2.moveToFirst()) {
                do {
                    String name = cx2.getString(1);
                    if (name.equals(s)) {
                        String add = cx2.getString(2);
                        button3.setText(add);
                        break;
                    }
                } while (cx2.moveToNext());
            }
            cx2.close();

        }

        else
        {
            Toast.makeText(Edit_records.this,"Please type roll no.", Toast.LENGTH_SHORT).show();
        }
    }

    public void open_main(){
        button2=(Button)findViewById(R.id.button20);
        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent6 = new Intent("com.akshayvermadtugmail.dtusmartattender.Main");
                        startActivity(intent6);
                    }
                }
        );
    }

    public void edit_save(){
        button=(Button)findViewById(R.id.button19);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        e1.setText("");
                        button3.setText("Show current status");
                    }
                }
        );
    }


    public void onClick(View v) {
        // TODO Auto-generated method stub
        final ContentValues data = new ContentValues();
        final ContentValues data2 = new ContentValues();

        Intent intent_main_return = this.getIntent();
        final String table_name = intent_main_return.getExtras().getString("table");
        final String table_date = intent_main_return.getExtras().getString("table_date");
        bbb = intent_main_return.getExtras().getString("amount_column");
        String s_r= null;
        String p="Present";
        String a="Absent";
        s=e1.getText().toString();

        assert table_date != null;
        s_r = table_date.replace("/","_");
        int date_col_index = Integer.parseInt(bbb);
        String where2 = "id ='"+s+"' " ;
        String where = "name ='"+s+"' AND date ='"+table_date+"'" ;

        int id=v.getId();

        switch(id) {
            case R.id.button22:

                if(e1 != null && s.length()>0){
                    data.put("address",p);
                    dbx.update(table_name,data, where, null);

                    for(int ll=1;ll<=date_col_index;ll++){
                        String sss= s_r+"__"+ll;
                        data2.put("'"+sss+"'",p);
                        db_net.update(table_name,data2, where2, null);
                        }

                    Toast.makeText(getBaseContext(),"Roll no. "+s+" set to be Present", Toast.LENGTH_LONG).show();
                }

                else
                {
                    Toast.makeText(Edit_records.this,"Please type roll no.", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.button23:

                if(e1 != null && s.length()>0){
                    data.put("address",a);
                    dbx.update(table_name,data, where, null);

                    for(int ll=1;ll<=date_col_index;ll++){
                        String sss= s_r+"__"+ll;
                        data2.put("'"+sss+"'",a);
                        db_net.update(table_name,data2, where2, null);
                    }

                    Toast.makeText(getBaseContext(),"Roll no. "+s+" set to be Absent", Toast.LENGTH_LONG).show();
                }

                else
                {
                    Toast.makeText(Edit_records.this,"Please type roll no.", Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }

}
