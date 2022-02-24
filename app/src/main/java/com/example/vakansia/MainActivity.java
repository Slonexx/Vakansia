package com.example.vakansia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

   RecyclerView recyclerView;

    private String oblast;
    private String PlusOblast = "Казахстан, Казахстан, ";
    private String Filter = "job_name";
    private MenuItem menuItem;
    SearchView searchView;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    private String VACANCY_KEY = "vacancy";
    private String USER_KEY = "User";
   // private int SIZE_LIST = 50;

    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new recfragment()).commit();
        init();
    }

    private void init(){
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if (user != null) {
            myRef = FirebaseDatabase.getInstance().getReference(USER_KEY);
            myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String value = snapshot.child("Oblast").getValue(String.class);
                    oblast = PlusOblast + value;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });

        }

        recyclerView = findViewById(R.id.RecyclerView_list);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<VacancyClass> options =
                new FirebaseRecyclerOptions.Builder<VacancyClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(VACANCY_KEY).orderByChild("location").startAt(oblast+"~"), VacancyClass.class)
                        .build();
        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);

        MenuItem item = menu.findItem(R.id.search_job_name);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.search_job_name){
            Filter = "job_name";
        } else if (id == R.id.search_local){
            Filter = "location";
        } else if (id == R.id.search_data){
            Filter = "creation_date";
        } else if (id == R.id.search_work){
            Filter = "schedule";
        } else if (id == R.id.search_zp){
            Filter = "salary";
        }



        return super.onOptionsItemSelected(item);
    }


    private void txtSearch(String str){
        FirebaseRecyclerOptions<VacancyClass> options =
                new FirebaseRecyclerOptions.Builder<VacancyClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(VACANCY_KEY).orderByChild(Filter).startAt(str+"~"), VacancyClass.class)
                        .build();
        mainAdapter = new MainAdapter(options);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }
}
