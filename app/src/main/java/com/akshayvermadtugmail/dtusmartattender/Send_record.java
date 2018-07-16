package com.akshayvermadtugmail.dtusmartattender;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.poi.hpsf.Util;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;

public class Send_record extends AppCompatActivity {
    private SQLiteDatabase db_batch,db_net,db3;
    private Cursor c;
    final Context context = this;
    String net, netx;
    float k;
    int b,initial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_record);
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
            openDatabase3();
            openDatabase2();

        }

        else
        {
            Toast.makeText(this,"no record available",Toast.LENGTH_LONG).show();
            LinearLayout ll = (LinearLayout)findViewById(R.id.button_layout4);
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


    protected void openDatabase2() {
        db_net= openOrCreateDatabase("db_net", Context.MODE_PRIVATE, null);
    }

    protected void openDatabase3() {
        db3 = openOrCreateDatabase("student_total", Context.MODE_PRIVATE, null);
    }


    protected void showRecords() {

        int i=0;
        Intent intent = this.getIntent();
        String s=intent.getExtras().getString("go_1a");

        LinearLayout ll = (LinearLayout)findViewById(R.id.button_layout4);
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

                net=name+"-"+add+"-"+section+"-"+sectionx;
                netx=add+section+sectionx;

                final Button btn = new Button(this);
                btn.setId(i+15);

                btn.setText(net);
                ll.addView(btn, lp);

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.dialog_layout2, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setView(promptsView);

                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                String btn_text = btn.getText().toString();
                                                String btn_text2=btn_text.replace("-","");
                                                String btn_text3=btn_text2.trim();
                                                int zz=get_table_details_initial(btn_text3);
                                                try {
                                                    write_excel(btn_text3,zz);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                startActivity(getSendEmailIntent(btn_text3,btn_text));
                                                // sendEmail(btn_text3);
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

    public Intent getSendEmailIntent(String fileName,String sub_name) {

        Intent intent = this.getIntent();
        String s=intent.getExtras().getString("go_1a");


        String[] TO = {s};

        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse(s));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"DTU Smart Attender:- Attendance of Batch "+"\t"+sub_name);
        emailIntent.putExtra(Intent.EXTRA_TEXT,"Open the attached file using Excel to see the saved records");

        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://" + CachedFileProvider.AUTHORITY + "/" + "'"+fileName+"'.csv"));

        return emailIntent;
    }

    protected void write_excel(String file_name,int ini) throws IOException {

        String SELECT_SQL2 = "SELECT * FROM " + file_name;
        Cursor c2 = db_net.rawQuery(SELECT_SQL2, null);
        c2.moveToFirst();
        b = c2.getColumnCount();

        Workbook wb = new HSSFWorkbook();
        Cell cell0=null,cell=null,cell2=null;
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("record");
        Row row = sheet1.createRow(0);

        sheet1.setColumnWidth(0, (15 * 120));
        for (int e=1;e<=b+1;e++){
            sheet1.setColumnWidth(e, (15 * 250));
        }

        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        CellStyle cs2 = wb.createCellStyle();
        cs2.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
        cs2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        CellStyle cs3 = wb.createCellStyle();
        cs3.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        cs3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        CellStyle cs4 = wb.createCellStyle();
        cs4.setFillForegroundColor(HSSFColor.ROSE.index);
        cs4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        CellStyle cs5 = wb.createCellStyle();
        cs5.setFillForegroundColor(HSSFColor.RED.index);
        cs5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        cell0 = row.createCell(0);
        String as = "Roll No.";
        cell0.setCellValue(as);
        cell0.setCellStyle(cs2);

        for (int y=1;y<=(b-1);y++){
            cell = row.createCell(y);
            String aa = c2.getColumnName(y);
            cell.setCellValue(aa);
            cell.setCellStyle(cs2);
        }

        cell = row.createCell(b);
        cell.setCellValue("Percentage");
        cell.setCellStyle(cs2);

        int q=1;
        int v;
        if (c2.moveToFirst()) {

            do {

                Row row_next = sheet1.createRow(q);

                    String roll = Integer.toString(ini);
                    cell2 = row_next.createCell(0);
                    cell2.setCellValue(roll);
                    cell2.setCellStyle(cs3);

                for(int f=1;f<=(b-1);f++){
                    String status = c2.getString(f);
                    cell2 = row_next.createCell(f);
                    cell2.setCellValue(status);
                    if (cell2.getStringCellValue().equals("Present")){
                        cell2.setCellStyle(cs);
                    }
                    else if (cell2.getStringCellValue().equals("Absent")){
                        cell2.setCellStyle(cs5);
                    }
                }

                v=0;
                for(int ff=1;ff<=(b-1);ff++){
                    String status = c2.getString(ff);

                    if (status.equals("Present")){
                        ++v;
                    }
                }

                int re = (v*100)/(b-1);
                cell2 = row_next.createCell(b);
                cell2.setCellValue(Integer.toString(re)+"%");
                cell2.setCellStyle(cs4);
                q++;
                ini++;
            }
            while (c2.moveToNext());
        }
        c2.close();

        File cacheFile = new File(context.getCacheDir()+ File.separator + "'"+file_name+"'.csv");
        cacheFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(cacheFile);
        wb.write(fos);
        fos.close();
    }




    public int get_table_details_initial(String table_name){
        final String SELECT_SQL2 = "SELECT * FROM student_total" ;
        Cursor cqf = db3.rawQuery(SELECT_SQL2, null);
        cqf.moveToFirst();

        if (cqf.moveToFirst()) {
            do {
                String name = cqf.getString(0);
                if (name.equals(table_name))
                {
                    initial = cqf.getInt(2);
                    break;
                }
            } while (cqf.moveToNext());
        }
        cqf.close();

        return initial;

    }

}
