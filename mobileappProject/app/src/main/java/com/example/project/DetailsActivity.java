package com.example.project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    TextView Dphonename, Dprice, Ddescription;
    Button Deditbutton, Ddeletebutton;


    String id, name, price, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Dphonename = findViewById(R.id.Dphonename);
        Dprice = findViewById(R.id.Dprice);
        Ddescription = findViewById(R.id.Ddescription);
        Deditbutton = findViewById(R.id.Deditebutton);
        Ddeletebutton = findViewById(R.id.Ddeletebutton);

        getAndSetIntentData();

        //Confirm button event
        Deditbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(DetailsActivity.this);
                name = Dphonename.getText().toString().trim();
                price = Dprice.getText().toString().trim();
                description = Ddescription.getText().toString().trim();

                Intent EditIntent = new Intent(DetailsActivity.this, EditActivity.class);
                EditIntent.putExtra("id", String.valueOf(id));
                EditIntent.putExtra("name", String.valueOf(name));
                EditIntent.putExtra("price", String.valueOf(price));
                EditIntent.putExtra("description", String.valueOf(description));
                //context.startActivity(intent);
                view.getContext().startActivity(EditIntent);
            }
        });

        Ddeletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });


    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("price") &&
                getIntent().hasExtra("description")) {

            //get data from intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            price = getIntent().getStringExtra("price");
            description = getIntent().getStringExtra("description");

            //set data
            Dphonename.setText(name);
            Dprice.setText(price);
            Ddescription.setText(description);

        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    //Alert used to confirm delete call
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper databaseHelper = new DatabaseHelper(DetailsActivity.this);
                databaseHelper.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}

