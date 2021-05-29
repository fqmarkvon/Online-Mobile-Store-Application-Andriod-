package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    //initialize
    RecyclerView recyclerView;
    Button mainAdd;
    DatabaseHelper databaseHelper;
    ArrayList<String> phone_id, phone_name,phone_price, phone_description;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle readdInstanceState) {
        super.onCreate(readdInstanceState);
        setContentView(R.layout.activity_main);


        //Initialize variable
        recyclerView = findViewById(R.id.recyclerView);
        mainAdd = findViewById(R.id.add_main);

        databaseHelper = new DatabaseHelper(MainActivity.this);
        phone_id = new ArrayList<>();
        phone_name = new ArrayList<>();
        phone_price = new ArrayList<>();
        phone_description = new ArrayList<>();

        //Method used to store the data in arrays
        storeDataInArrays();

        //Custom Adapter to use with the database and recycler view
        customAdapter = new CustomAdapter(MainActivity.this,phone_id,phone_name,phone_price,phone_description);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        //Add function
        mainAdd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                // add jump to  edit
                Intent AddIntent = new Intent(MainActivity.this, AddActivity.class);
                view.getContext().startActivity(AddIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);

                return false;
            }
        });
        return true;
    }

    //read data
    void storeDataInArrays(){
        Cursor cursor = databaseHelper.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this,"No data.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                phone_id.add(cursor.getString(0));
                phone_name.add(cursor.getString(1));
                phone_price.add(cursor.getString(2));
                phone_description.add(cursor.getString(3));
            }
        }
    }
    // Used to search query
    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Cursor c = databaseHelper.getWordMatches(query, null);
            //process Cursor and display results
        }
    }
}
