package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Activity context;
    EditText task_title;
    EditText task_time;
    Button add_task_btn;
    FloatingActionButton new_task_btn;
    RecyclerView recyclerView;
    Button button;

    List<MainData> dataList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;

    AddTask addTask;

    NumberPicker hours_picker,minutes_picker;


    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycler_view);
        button=findViewById(R.id.bt_update);
        database=RoomDB.getInstance(this);
        //store db value in datalist

        dataList=database.mainDao().getAll();


        linearLayoutManager =new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        //init adapter

        addTask=new AddTask(dataList,MainActivity.this);
        //set adapter

        recyclerView.setAdapter(addTask);


        new_task_btn=findViewById(R.id.new_task);
        new_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog=new Dialog(view.getContext());

                dialog.setContentView(R.layout.add_task_dialog);

                hours_picker = dialog.findViewById(R.id.hours_picker);
                hours_picker.setMaxValue(60);

                minutes_picker=dialog.findViewById(R.id.minutes_picker);
                minutes_picker.setMaxValue(60);

                dialog.findViewById(R.id.bt_update).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainData data=new MainData();
                        EditText tmp=(EditText)dialog.findViewById(R.id.task_title);
                        String str=tmp.getText().toString();
                        data.setText(str);

                        Integer hours=hours_picker.getValue();
                        data.setHours(hours);

                        Integer minutes=minutes_picker.getValue();
                        data.setMinutes(minutes);


                        //insert text in database
                        database.mainDao().insert(data);

                        dataList.clear();
                        Toast.makeText(MainActivity.this,"Successfully added!",Toast.LENGTH_LONG).show();

                        dataList.addAll(database.mainDao().getAll());
                        addTask.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });
    }

}