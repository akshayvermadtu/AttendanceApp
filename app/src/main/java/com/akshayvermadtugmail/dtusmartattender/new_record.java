package com.akshayvermadtugmail.dtusmartattender;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class new_record extends AppCompatActivity {
    Spinner spinner,spinner2,spinner3,spinner4,spinner5;
    ArrayAdapter<CharSequence> arrayAdapter,arrayAdapter2,arrayAdapter3,arrayAdapter4,arrayAdapter5,arrayAdapter6,arrayAdapter7,arrayAdapter8,arrayAdapter9,arrayAdapter10;
    Button button,button2;
    int y,m,d;
    static final int Did=0;
    EditText e1,e2;
    final Context context = this;
    private SQLiteDatabase db;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        e1 = (EditText) findViewById(R.id.editText5);
        e2 = (EditText) findViewById(R.id.editText2);
        button2 = (Button) findViewById(R.id.button12);


        final Calendar calendar = Calendar.getInstance();
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);
        d = calendar.get(Calendar.DAY_OF_MONTH);


        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        spinner5 = (Spinner) findViewById(R.id.spinner5);

        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.Btech_1, android.R.layout.simple_spinner_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        arrayAdapter3 = ArrayAdapter.createFromResource(this, R.array.Btech_all, android.R.layout.simple_spinner_item);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        arrayAdapter4 = ArrayAdapter.createFromResource(this, R.array.Mtech_all, android.R.layout.simple_spinner_item);
        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        arrayAdapter5 = ArrayAdapter.createFromResource(this, R.array.Btech_1_sections_of_A, android.R.layout.simple_spinner_item);
        arrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        arrayAdapter6 = ArrayAdapter.createFromResource(this, R.array.Btech_1_sections_of_B, android.R.layout.simple_spinner_item);
        arrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        arrayAdapter7 = ArrayAdapter.createFromResource(this, R.array.Btech_all_sections, android.R.layout.simple_spinner_item);
        arrayAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        arrayAdapter8 = ArrayAdapter.createFromResource(this, R.array.Mtech_all_sections, android.R.layout.simple_spinner_item);
        arrayAdapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        arrayAdapter9 = ArrayAdapter.createFromResource(this, R.array.class_type, android.R.layout.simple_spinner_item);
        arrayAdapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        arrayAdapter10 = ArrayAdapter.createFromResource(this, R.array.frequency, android.R.layout.simple_spinner_item);
        arrayAdapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    spinner2.setAdapter(arrayAdapter2);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position == 0) {
                                spinner3.setAdapter(arrayAdapter5);


                            } else {
                                spinner3.setAdapter(arrayAdapter6);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } else if (position == 1 || position == 2 || position == 3) {
                    spinner2.setAdapter(arrayAdapter3);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                            spinner3.setAdapter(arrayAdapter7);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    spinner2.setAdapter(arrayAdapter4);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                            spinner3.setAdapter(arrayAdapter8);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner4.setAdapter(arrayAdapter9);
        spinner5.setAdapter(arrayAdapter10);

        roll_no_list();
        showDialog_button_click();
        createDatabase();
        createDatabase2();

    }

    public void roll_no_list(){
        Button g = (Button) findViewById(R.id.button2);
        g.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String spin1 = spinner.getSelectedItem().toString();
                        final String spin2 = spinner2.getSelectedItem().toString();
                        final String spin3 = spinner3.getSelectedItem().toString();
                        final String spin4 = spinner4.getSelectedItem().toString();
                        final String spin5 = spinner5.getSelectedItem().toString();
                        String table_name=spin1+spin2+spin3+spin4;

                        final String s1;
                        final String s2;
                        final String s3;

                        s1=e1.getText().toString();
                        s2=e2.getText().toString();
                        s3=button2.getText().toString();


                        if (e1 != null && e2!=null && s1.length()>0 && s2.length()>0 && !s3.equals("Tap to select Date")) {

                            Boolean bO;
                            Boolean b11=true;
                            bO=doesDatabaseExist();
                            if(bO)
                            {
                                openDatabase();

                                String query2 = "SELECT DISTINCT date FROM " + table_name;
                                c = db.rawQuery(query2, null);
                                c.moveToFirst();

                                if (c.moveToFirst()) {
                                    do {
                                        String date_check = c.getString(0);
                                        if (date_check.equals(s3))
                                        {
                                           b11=false;
                                        }
                                    } while (c.moveToNext());
                                }   c.close();

                                if (b11){


                                    LayoutInflater lil = LayoutInflater.from(context);
                                    View promptsView = lil.inflate(R.layout.dialog_stu_value, null);

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                    alertDialogBuilder.setView(promptsView);


                                    final TextView userInput = (TextView) promptsView
                                            .findViewById(R.id.textView_1_value);

                                    userInput.setText(s1);

                                    final TextView userInput2 = (TextView) promptsView
                                            .findViewById(R.id.textView_2_value);

                                    userInput2.setText(s2);

                                    int a, b, c;
                                    a = Integer.parseInt(s1);
                                    b = Integer.parseInt(s2);
                                    c = a + b - 1;
                                    String cc = Integer.toString(c);

                                    final TextView userInput3 = (TextView) promptsView
                                            .findViewById(R.id.textView_3_value);

                                    userInput3.setText(cc);

                                    alertDialogBuilder
                                            .setCancelable(false)
                                            .setPositiveButton("Next",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {


                                                            Intent intent6 = new Intent("com.akshayvermadtugmail.dtusmartattender.new_roll");
                                                            intent6.putExtra("go", spin1);
                                                            intent6.putExtra("go2", spin2);
                                                            intent6.putExtra("go3", spin3);
                                                            intent6.putExtra("go4", s1);
                                                            intent6.putExtra("go5", s2);
                                                            intent6.putExtra("go6", s3);
                                                            intent6.putExtra("go7", spin4);
                                                            intent6.putExtra("go8", spin5);
                                                            startActivity(intent6);


                                                        }
                                                    })
                                            .setNegativeButton("Edit",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();

                                }

                                else{
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                    builder1.setTitle("Warning");
                                    builder1.setMessage("Entry for this date has already been entered\nYou cannot use this date again ");
                                    builder1.setCancelable(true);

                                    builder1.setNegativeButton(
                                            "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();
                                }

                            }

                            else {

                                LayoutInflater lil = LayoutInflater.from(context);
                                View promptsView = lil.inflate(R.layout.dialog_stu_value, null);

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                alertDialogBuilder.setView(promptsView);


                                final TextView userInput = (TextView) promptsView
                                        .findViewById(R.id.textView_1_value);

                                userInput.setText(s1);

                                final TextView userInput2 = (TextView) promptsView
                                        .findViewById(R.id.textView_2_value);

                                userInput2.setText(s2);

                                int a, b, c;
                                a = Integer.parseInt(s1);
                                b = Integer.parseInt(s2);
                                c = a + b - 1;
                                String cc = Integer.toString(c);

                                final TextView userInput3 = (TextView) promptsView
                                        .findViewById(R.id.textView_3_value);

                                userInput3.setText(cc);

                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setPositiveButton("Next",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {


                                                        Intent intent6 = new Intent("com.akshayvermadtugmail.dtusmartattender.new_roll");
                                                        intent6.putExtra("go", spin1);
                                                        intent6.putExtra("go2", spin2);
                                                        intent6.putExtra("go3", spin3);
                                                        intent6.putExtra("go4", s1);
                                                        intent6.putExtra("go5", s2);
                                                        intent6.putExtra("go6", s3);
                                                        intent6.putExtra("go7", spin4);
                                                        intent6.putExtra("go8", spin5);
                                                        startActivity(intent6);


                                                    }
                                                })
                                        .setNegativeButton("Edit",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();



                            }

                        }

                        else
                        {
                            Toast.makeText(new_record.this,"Please fill all columns and Date",Toast.LENGTH_LONG).show();
                        }

                    }
                }

        );
    }

    public void showDialog_button_click(){
        button=(Button)findViewById(R.id.button12);
        button.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        showDialog(Did);
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if (id==Did){
            return new DatePickerDialog(this,dpickerListenner,y,m,d);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListenner =new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            y = year;
            m = monthOfYear+1;
            d = dayOfMonth;

            if (d<10&&m<10){
                button.setText( y+ "/" + "0" +  m + "/" +"0" + d);

            }
            else if (d<10&&m>=10){
                button.setText( y+ "/" +  m + "/" + "0" + d);
            }

            else if (d>=10&&m<10){
                button.setText(y+ "/" + "0" +  m + "/" + d);
            }

            else
            {
                button.setText(y+ "/" + m + "/" + d);
            }
        }

    };

    protected void createDatabase(){
        SQLiteDatabase db_batch = openOrCreateDatabase("batch_name", Context.MODE_PRIVATE, null);
       // db_batch.execSQL("CREATE TABLE IF NOT EXISTS batch(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR,address VARCHAR);");
        db_batch.execSQL("CREATE TABLE IF NOT EXISTS batch_name(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR,address VARCHAR,section VARCHAR,sectionx VARCHAR,date BLOB);");

    }

    protected void createDatabase2(){
        SQLiteDatabase student_total = openOrCreateDatabase("student_total", Context.MODE_PRIVATE, null);
        student_total.execSQL("CREATE TABLE IF NOT EXISTS student_total(name VARCHAR, s INTEGER,initial INTEGER);");
    }

    private boolean doesDatabaseExist() {
        SQLiteDatabase checkDB;
        final String spin1 = spinner.getSelectedItem().toString();
        final String spin2 = spinner2.getSelectedItem().toString();
        final String spin3 = spinner3.getSelectedItem().toString();
        final String spin4 = spinner4.getSelectedItem().toString();
        String table_name=spin1+spin2+spin3+spin4;
        boolean checkFlag = true;
        try{
            checkDB = SQLiteDatabase.openDatabase("data/data/com.akshayvermadtugmail.dtusmartattender/databases/PersonDB", null,
                    SQLiteDatabase.OPEN_READWRITE);

            openDatabase();
            String SELECT_SQL2 = "SELECT * FROM " + table_name;
            Cursor cursor = db.rawQuery(SELECT_SQL2, null);
            if(cursor!=null) {
                if(cursor.getCount()==0) {
                    cursor.close();
                    checkFlag=false;
                }
                cursor.close();
            }
            checkDB.close();
        }
        catch(SQLiteException sqlException){
            checkFlag=false;
        }
        return checkFlag;
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
    }

}
