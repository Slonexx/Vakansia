package com.example.vakansia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class FilterActivity extends AppCompatActivity {

    private String[] oblast = {"","АКМОЛИНСКАЯ ОБЛАСТЬ", "АКТЮБИНСКАЯ ОБЛАСТЬ", "АЛМАТИНСКАЯ ОБЛАСТЬ",
            "АТЫРАУСКАЯ ОБЛАСТЬ",  "ВОСТОЧНО-КАЗАХСТАНСКАЯ ОБЛАСТЬ", "ЖАМБЫЛСКАЯ ОБЛАСТЬ", "ЗАПАДНО-КАЗАХСТАНСКАЯ ОБЛАСТЬ",
            "КАРАГАНДИНСКАЯ ОБЛАСТЬ", "КОСТАНАЙСКАЯ ОБЛАСТЬ", "КЫЗЫЛОРДИНСКАЯ ОБЛАСТЬ", "МАНГИСТАУСКАЯ ОБЛАСТЬ",
            "ПАВЛОДАРСКАЯ ОБЛАСТЬ","СЕВЕРО-КАЗАХСТАНСКАЯ ОБЛАСТЬ", "ТУРКЕСТАНСКАЯ ОБЛАСТЬ"};

    EditText tv_job_namen;
    EditText tv_date;

    private String obalstFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        init();
        }

        private void init(){
            tv_job_namen = (EditText) findViewById(R.id.filter_tv_job_namen);
            tv_date = (EditText) findViewById(R.id.filter_tv_date);

            ArrayAdapter<String> oblastAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, oblast);

            Spinner spOblast = (Spinner) findViewById(R.id.sp_oblast);
            spOblast.setAdapter(oblastAdapter);
            spOblast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    obalstFilter = adapterView.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        public void onClickFilter(View v){
        Intent intent = new Intent(FilterActivity.this, MainActivity.class);
            String Filter = null;
            String Filter_id = null;
            String job_name = tv_job_namen.getText().toString();
            String creation_date = tv_date.getText().toString();
            String location = "Казахстан, Казахстан, "+obalstFilter;
            if (!TextUtils.isEmpty(job_name)){
                Filter = job_name;
                Filter_id = "job_name";
            }
            if (!TextUtils.isEmpty(creation_date)){
                Filter = creation_date;
                Filter_id = "creation_date";
            }
            if (!TextUtils.isEmpty(location)){
                Filter = location;
                Filter_id = "location";
            }
            intent.putExtra("Filter_id", Filter_id);
            intent.putExtra("Filter",Filter);

            startActivity(intent);
        }

}

/*<item android:id="@+id/search_local"
        android:icon="@drawable/ic_search_icon"
        android:title="Поиск по облости"
        app:showAsAction="never"
        app:actionViewClass="android.widget.SearchView"/>
    <item android:id="@+id/search_data"
        android:icon="@drawable/ic_search_icon"
        android:title="Поиск по дате"
        app:showAsAction="never"
        app:actionViewClass="android.widget.SearchView"/>
    <item android:id="@+id/search_work"
        android:icon="@drawable/ic_search_icon"
        android:title="Поиск по графику работы"
        app:showAsAction="never"
        app:actionViewClass="android.widget.SearchView"/>

    <item android:id="@+id/search_zp"
        android:icon="@drawable/ic_search_icon"
        android:title="Поиск по заработной платы"
        app:showAsAction="never"
        app:actionViewClass="android.widget.SearchView"/>*/