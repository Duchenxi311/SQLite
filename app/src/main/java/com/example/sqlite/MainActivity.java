package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnRead;
    private Button btnWrite;
    private Button btnUpdate;
    private Button btnRemove;

    private EditText edName;
    private EditText edEmail;
    private EditText edNumber;
    private EditText edRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRead=(Button)findViewById(R.id.btnRead);
        btnWrite=(Button)findViewById(R.id.btnWrite);
        btnUpdate=(Button)findViewById(R.id.btnUpdate);
        btnRemove=(Button)findViewById(R.id.btnRemove);

        edName=(EditText) findViewById(R.id.edName);
        edEmail=(EditText) findViewById(R.id.edEmail);
        edNumber=(EditText) findViewById(R.id.edNumber);
        edRecord=(EditText) findViewById(R.id.edRecord);

        MyDB myDB = new MyDB(this);
        myDB.getWritableDatabase();
    }
}
