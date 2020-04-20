package com.example.scrollview_sinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edtID, edtName,edtNS;
    Button btnInsert, btnUpdate, btnDelete, btnLoad;
    TextView tvData;
    myDBhelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbhelper = new myDBhelper(MainActivity.this);
        anhXa();
        //        GetData();
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long resultAdd = dbhelper.Insert(Integer.parseInt(getValueString(edtID))
                        ,getValueString(edtName)
                        ,Integer.parseInt(getValueString(edtNS)));
                if(resultAdd==-1){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Insert Success", Toast.LENGTH_SHORT).show();
//                    GetData();
                    clear();

                }
            }
        });
        /////

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long resultUpdate = dbhelper.Update(Integer.parseInt(getValueString(edtID))
                        ,getValueString(edtName)
                        ,Integer.parseInt(getValueString(edtNS)));
                if (resultUpdate==0){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }else if(resultUpdate==1){
                    Toast.makeText(MainActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
//                    GetData();
                    clear();
                }else {
                    Toast.makeText(MainActivity.this, "Error, multiple records update", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /////

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getValueString(edtID).length()==0){
                    Toast.makeText(MainActivity.this, "Error! ID null", Toast.LENGTH_SHORT).show();
                }else{
                    long resultDelete = dbhelper.Delete(Integer.parseInt(getValueString(edtID)));
                    if(resultDelete==0){
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
//                        GetData();
                        clear();
                    }
                }
            }
        });
        /////

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData();
            }
        });
    }
    public void GetData(){
        StringBuffer stringBuffer = new StringBuffer();
        Cursor cursor = dbhelper.getAllRecord();

        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            stringBuffer.append(cursor.getInt(cursor.getColumnIndex(myDBhelper.getID())));
            stringBuffer.append(" -- ");
            stringBuffer.append(cursor.getInt(cursor.getColumnIndex(myDBhelper.getNAME())));
            stringBuffer.append(" -- ");
            stringBuffer.append(cursor.getInt(cursor.getColumnIndex(myDBhelper.getYEAROB())));
            stringBuffer.append("\n");
        }
        tvData.setText(stringBuffer);
    }
    public void anhXa(){
        edtID = (EditText) findViewById(R.id.msv);
        edtName = (EditText) findViewById(R.id.hoTen);
        edtNS = (EditText) findViewById(R.id.namSinh);
        /////
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        /////
        tvData = (TextView) findViewById(R.id.textView);
    }
    public void clear(){
        edtID.setText("");
        edtNS.setText("");
        edtName.setText("");
    }
    private String getValueString(EditText edt){
        return edt.getText().toString().trim();
    }
    protected void onStart(){
        super.onStart();
        dbhelper.openDB();
    }
    protected void onStop(){
        super.onStop();
        dbhelper.closeDB();
    }
}
