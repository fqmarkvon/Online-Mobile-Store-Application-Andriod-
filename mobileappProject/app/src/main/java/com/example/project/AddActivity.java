package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText Aphoneid, Aprice, Adescription;
    Button Aaddbutton;

    String id, name, price, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        Aphoneid = findViewById(R.id.Aphonename);
        Aprice = findViewById(R.id.Aprice);
        Adescription = findViewById(R.id.Adescription);
        Aaddbutton = findViewById(R.id.Aaddbutton);


        //Confirm button event
        Aaddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(AddActivity.this);
                databaseHelper.addPhone(Aphoneid.getText().toString().trim(),
                        Aprice.getText().toString().trim(),
                        Adescription.getText().toString().trim());


                // jump back to  main
                Intent MainIntent = new Intent(AddActivity.this, MainActivity.class);
                view.getContext().startActivity(MainIntent);
            }
        });
    }
}