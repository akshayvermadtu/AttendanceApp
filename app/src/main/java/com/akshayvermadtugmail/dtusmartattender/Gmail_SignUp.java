package com.akshayvermadtugmail.dtusmartattender;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.akshayvermadtugmail.dtusmartattender.R.id.editText3;

public class Gmail_SignUp extends AppCompatActivity {
    private static Button b;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail__sign_up);
        editText=(EditText)findViewById(editText3);
        next_main();
        read();
    }

    public void next_main() {
        b=(Button)findViewById(R.id.button14);
        b.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String Eid = editText.getText().toString();
                        try {
                            FileOutputStream fileOutputStream = openFileOutput("Eid.txt", MODE_PRIVATE);
                            fileOutputStream.write(Eid.getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                            if (editText!=null && Eid.length()>0) {

                                    Toast.makeText(getApplicationContext(), "E-mail Id has been saved", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent("com.akshayvermadtugmail.dtusmartattender.Main");
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
            FileInputStream fileInputStream= openFileInput("Eid.txt");
            InputStreamReader inputStreamReader= new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuilder stringBuffer=new StringBuilder();
            String Eidprint;
            while ((Eidprint=bufferedReader.readLine())!=null){
                 stringBuffer.append(Eidprint);
            }
            editText.setText(stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

