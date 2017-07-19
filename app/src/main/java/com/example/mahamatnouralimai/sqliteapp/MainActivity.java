package com.example.mahamatnouralimai.sqliteapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb ;

    EditText editname , editsurname, editmarks, editId ;
    Button buttonAddData , viewAll, button_update, buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editname = (EditText)findViewById(R.id.name_textView);
        editsurname = (EditText)findViewById(R.id.surname_textView);
        editmarks = (EditText)findViewById(R.id.marks_textView);
        editId = (EditText)findViewById(R.id.id_edittext);
        buttonAddData = (Button)findViewById(R.id.add_data_button);
        viewAll = (Button)findViewById(R.id.view_all_button);
        button_update = (Button)findViewById(R.id.update_button);
        buttonDelete = (Button)findViewById(R.id.delete_button);
        addData();
        viewAllMethod();
        updateData();
        deleteData();
    }

    public  void addData(){

        buttonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              boolean isInsertedData =  myDb.insertData(editname.getText().toString(), editsurname.getText().toString(), editmarks.getText().toString());


                if (isInsertedData == true)

                    Toast.makeText(MainActivity.this, "Data Inserted Correctly ", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data NOT Inserted Correctly ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewAllMethod(){

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAlldata();

                if (res.getCount()==0){
                    //show message
                    showMessage("Error", " Nothing found");

                    return;
                }
                StringBuffer buffer = new StringBuffer();

                while (res.moveToNext()){
                    buffer.append("ID: "+ res.getString(0) + "\n");
                    buffer.append("NAME: "+ res.getString(1) + "\n");
                    buffer.append("SURNAME: "+ res.getString(2) + "\n");
                    buffer.append("MARKS: "+ res.getString(3) + "\nn");
                }
                    // show all data
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
    public void updateData()
    {
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = myDb.udpateData(editId.getText().toString(), editname.getText().toString(), editsurname.getText().toString(), editmarks.getText().toString());
                if (isUpdate== true)
                    Toast.makeText(MainActivity.this, "Data Updateed Correctly ", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data couldn't Update ", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void deleteData(){

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer deleteRow = myDb.deleteData(editId.getText().toString());
                if (deleteRow > 0)
                    Toast.makeText(MainActivity.this, "Data Delete Correctly ", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Delete ", Toast.LENGTH_LONG).show();

            }
        });
    }
}
