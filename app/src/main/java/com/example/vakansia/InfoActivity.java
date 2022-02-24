package com.example.vakansia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class InfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView textView_job_name, textView_salary, textView_location, textView_schedule,
            textView_creation_date, textView_employment, textView_email, textView_phone;

    DatabaseReference ref;
    private String VACANCY_KEY = "vacancy";
    private String company_KEY = "company";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        init();
        String key = getIntent().getStringExtra("key");
        ref.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String job_name = snapshot.child("job_name").getValue().toString();
                String salary = snapshot.child("salary").getValue().toString();
                String location = snapshot.child("location").getValue().toString();
                String phone = snapshot.child(company_KEY).child("phone").getValue().toString();
                String employment = snapshot.child("employment").getValue().toString();
                String schedule = snapshot.child("schedule").getValue().toString();
                String email = snapshot.child(company_KEY).child("email").getValue().toString();


                textView_job_name.setText(job_name);
                textView_salary.setText(salary);
                textView_location.setText(location);
                textView_phone.setText(phone);
                textView_employment.setText(employment);
                textView_schedule.setText(schedule);
                textView_email.setText(email);

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       // textView_job_name.setText(job_name);
    }

    private void init(){
        textView_job_name = findViewById(R.id.tv_info_job_name);
        textView_salary = findViewById(R.id.tv_info_salary);
        textView_location = findViewById(R.id.tv_info_location);
        textView_phone = findViewById(R.id.tv_info_phone);
        textView_employment = findViewById(R.id.tv_info_employment);
        textView_schedule = findViewById(R.id.tv_info_schedule);
        textView_email = findViewById(R.id.tv_info_email);

        ref = FirebaseDatabase.getInstance().getReference().child(VACANCY_KEY);


        //Fragment fragment = new MapFragment();
       /* Bundle bundle = new Bundle();
        bundle.putString(Location_map, "Location_map");
        fragment.setArguments(bundle);
*/
        //getSupportFragmentManager().beginTransaction().replace(R.id.mapFragmetn, fragment).commit();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
            String address = getIntent().getStringExtra("location");
            List<Address> addressList = null;
            LatLng latLng = null;

                Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocationName(address, 1);
                    while (addressList.size()==0) {
                        addressList = geocoder.getFromLocationName(address, 1);}
                    if (addressList.size()>0) {
                        Address addr = addressList.get(0);
                        latLng = new LatLng(addr.getLatitude(), addr.getLongitude());
                    }

                    googleMap.addMarker(new MarkerOptions().position(latLng).title(address));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }

