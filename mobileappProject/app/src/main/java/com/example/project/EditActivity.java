package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    EditText Ephonename, Eprice, Edescription;
    Button Econfirmbutton;

    String id, name, price, description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Ephonename = findViewById(R.id.Ephonename);
        Eprice = findViewById(R.id.Eprice);
        Edescription = findViewById(R.id.Edescription);
        Econfirmbutton = findViewById(R.id.EComfirmbutton);

        getAndSetIntentData();

        //Confirm button event
        Econfirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(EditActivity.this);
                name =Ephonename.getText().toString().trim();
                price=Eprice.getText().toString().trim();
                description=Edescription.getText().toString().trim();

                databaseHelper.updateData(id,name,price,description);


                // jump back to  main
                Intent MainIntent = new Intent(EditActivity.this, MainActivity.class);
                view.getContext().startActivity(MainIntent);
            }
        });


    }
    void getAndSetIntentData(){
        if (getIntent().hasExtra("id")&&getIntent().hasExtra("name") && getIntent().hasExtra("price") &&
                getIntent().hasExtra("description")){

            //get data from intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            price = getIntent().getStringExtra("price");
            description = getIntent().getStringExtra("description");

            //set data
            Ephonename.setText(name);
            Eprice.setText(price);
            Edescription.setText(description);

        }else{
            Toast.makeText(this,"No data.", Toast.LENGTH_SHORT).show();
        }
    }
}


