package com.akshayvermadtugmail.dtusmartattender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Link extends AppCompatActivity {
    Button b,b2;
    EditText editText;
    TextView editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editText=(EditText)findViewById(R.id.editText);
        editText2=(TextView) findViewById(R.id.editText4);
        noti();
        read();
        read2();
        noti2();
    }

    public void noti(){
        b=(Button)findViewById(R.id.button);
        b.setOnClickListener(
                new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        String Eid = editText.getText().toString();
                        try {
                            FileOutputStream fileOutputStream = openFileOutput("Eid2.txt", MODE_PRIVATE);
                            fileOutputStream.write(Eid.getBytes());
                        }
                        catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (editText!=null && Eid.length()>0) {

                            Toast.makeText(getApplicationContext(), "E-mail Id has been saved", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent("com.akshayvermadtugmail.dtusmartattender.Send_record");
                            intent.putExtra("go_1a",Eid);
                            startActivity(intent);
                        }
                        else
                        {Toast.makeText(getApplicationContext(), "Please enter a valid E-mail ID", Toast.LENGTH_SHORT).show();}

                    }
                }
        );
    }

    public void read(){
        try {
            FileInputStream fileInputStream= openFileInput("Eid2.txt");
            InputStreamReader inputStreamReader= new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer=new StringBuffer();
            String Eidprint;
            while ((Eidprint=bufferedReader.readLine())!=null){
                stringBuffer.append(Eidprint);
            }
            editText.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read2(){
        try {
            FileInputStream fileInputStream= openFileInput("Eid.txt");
            InputStreamReader inputStreamReader= new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer=new StringBuffer();
            String Eidprint;
            while ((Eidprint=bufferedReader.readLine())!=null){
                stringBuffer.append(Eidprint);
            }
            editText2.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void noti2(){
        b=(Button)findViewById(R.id.button13);
        b.setOnClickListener(
                new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        String Eid = editText2.getText().toString();
                        Intent intent = new Intent("com.akshayvermadtugmail.dtusmartattender.Send_record");
                        intent.putExtra("go_1a",Eid);
                        startActivity(intent);
                    }
                }
        );
    }

}
