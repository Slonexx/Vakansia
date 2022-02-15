package com.example.vakansia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class recfragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recview;
    MainAdapter myAdapter;

    private static String VACANCY_KEY = "vacancy";

    public recfragment() {
     }

     public static recfragment newInstance(String param1, String param2) {
        recfragment fragment = new recfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

          View view = inflater.inflate(R.layout.fragment_recfragment, container, false);

          recview=(RecyclerView) view.findViewById(R.id.recview);
          recview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<VacancyClass> options =
                new FirebaseRecyclerOptions.Builder<VacancyClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(VACANCY_KEY), VacancyClass.class)
                        .build();

        myAdapter = new MainAdapter(options);
        recview.setAdapter(myAdapter);

          return  view;
    }


    @Override
    public void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }

    public void txtSearch(String str){
        FirebaseRecyclerOptions<VacancyClass> options =
                new FirebaseRecyclerOptions.Builder<VacancyClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(VACANCY_KEY).orderByChild("location").startAt(str+"~"), VacancyClass.class)
                        .build();
        myAdapter = new MainAdapter(options);
        myAdapter.startListening();
        recview.setAdapter(myAdapter);
    }

}