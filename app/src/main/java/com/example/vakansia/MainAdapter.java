package com.example.vakansia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends FirebaseRecyclerAdapter<VacancyClass, MainAdapter.myViewHolder> {

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView job_name, location, creation_date, salary, schedule;
        View view;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
                job_name = itemView.findViewById(R.id.tv_job_name);
                location = itemView.findViewById(R.id.tv_location);
                creation_date = itemView.findViewById(R.id.tv_creation_date);
                salary = itemView.findViewById(R.id.tv_salary);
                schedule = itemView.findViewById(R.id.tv_schedule);

                view=itemView;
        }
    }


    public MainAdapter(@NonNull FirebaseRecyclerOptions<VacancyClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull VacancyClass model) {
        if (holder != null) {
            holder.job_name.setText(model.getJob_name());
            holder.location.setText(model.getLocation());
            holder.creation_date.setText(model.getCreation_date());
            holder.salary.setText(model.getSalary());
            holder.schedule.setText(model.getSchedule());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
                final String location = model.location;

                final String key = getRef(position).getKey();

            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent intent = new Intent(activity, InfoActivity.class);
                intent.putExtra("key",key);
                intent.putExtra("location",location);
                //next
                activity.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_adapter_vacancy,parent,false);
        return new myViewHolder(view);
    }




}
