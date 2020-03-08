package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edName;
    private EditText edEmail;
    private EditText edNumber;
    private TextView txtRecord;

    private Button write;
    private Button read;
    private Button update;
    private Button remove;

    MyDB myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edName=(EditText) findViewById(R.id.edName);
        edEmail=(EditText) findViewById(R.id.edEmail);
        edNumber=(EditText) findViewById(R.id.edNumber);
        txtRecord=(TextView) findViewById(R.id.txtRecord);

        write=(Button)findViewById(R.id.btnWrite);
        read=(Button)findViewById(R.id.btnRead);
        update=(Button)findViewById(R.id.btnUpdate);
        remove=(Button)findViewById(R.id.btnRemove);
        write.setOnClickListener(this);
        read.setOnClickListener(this);
        update.setOnClickListener(this);
        remove.setOnClickListener(this);

        myDB = new MyDB(this);
    }

    @Override
    public void onClick(View view) {
        String name;
        String email;
        String number;
        ContentValues values;
        SQLiteDatabase db;
        switch (view.getId()) {
            case R.id.btnWrite:
                name=edName.getText().toString();
                number=edNumber.getText().toString();
                email=edEmail.getText().toString();
                db=myDB.getReadableDatabase();
                values=new ContentValues();
                if(name.isEmpty()||number.isEmpty()||email.isEmpty()){
                    Toast.makeText(this,"Input is empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    Cursor cursor = db.query("info", new String[]{"number"}, "number = ?", new String[]{number}, null, null, null);
                    if(cursor.getCount()==0){
                        values.put("name",name);
                        values.put("email",email);
                        values.put("number",number);
                        db.insert("info",null,values);
                        Toast.makeText(this,"write success",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"the phone number already exists",Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                }
                db.close();
                break;
            case R.id.btnRead:
                db=myDB.getWritableDatabase();
                Cursor cursor1=db.query("info",null,null,null,null,null,null,null);
                if(cursor1.getCount()==0){
                    Toast.makeText(this,"no data",Toast.LENGTH_SHORT).show();
                }
                else{
                    cursor1.moveToFirst();
                    txtRecord.setText("name:"+cursor1.getString(1)+"  email:"+cursor1.getString(2)+"  number:"+cursor1.getString(3));
                }
                while (cursor1.moveToNext()){
                    txtRecord.append("\n"+"name:"+cursor1.getString(1)+"  email:"+cursor1.getString(2)+"  number:"+cursor1.getString(3));
                }
                cursor1.close();
                db.close();
                break;
            case R.id.btnUpdate:
                db=myDB.getWritableDatabase();
                Cursor cursor2 = db.query("info", new String[]{"number"}, "number = ?", new String[]{edNumber.getText().toString()}, null, null, null);
                if(cursor2.getCount()==0){
                    Toast.makeText(this,"the phone number does not exist",Toast.LENGTH_SHORT).show();
                }else{
                    values=new ContentValues();
                    values.put("name",edName.getText().toString());
                    values.put("email",edEmail.getText().toString());
                    db.update("info",values,"number=?",new String[]{edNumber.getText().toString()});
                    Toast.makeText(this,"update success",Toast.LENGTH_SHORT).show();
                }
                db.close();
                break;
            case R.id.btnRemove:
                db=myDB.getWritableDatabase();
                db.delete("info",null,null);
                Toast.makeText(this,"remove success",Toast.LENGTH_SHORT).show();
                db.close();
                txtRecord.setText("no records");
                break;
        }
    }
}