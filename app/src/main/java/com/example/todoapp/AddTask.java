package com.example.todoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddTask extends RecyclerView.Adapter<AddTask.ViewHolder> {

    private List<MainData> dataList;
    private Activity context;
    private  RoomDB database;


    AlertDialog.Builder builder;

    public AddTask(List<MainData> dataList, Activity context){
        this.context=context;
        this.dataList=dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_tasks,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddTask.ViewHolder holder, int position) {
        //int main data
        MainData data=dataList.get(position);
        //init db
        database=RoomDB.getInstance(context);
        //set text in textview

        holder.taskName.setText(data.getText());
        holder.taskTime.setText(data.getHours() + " : " + data.getMinutes());


        holder.btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainData d=dataList.get(holder.getAdapterPosition());
                //delete text from database

                database.mainDao().delete(d);
                //notify when data is eleted
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });

        holder.btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer tot_time= data.getHours()*3600+data.getMinutes()*60;

                new CountDownTimer(tot_time*1000, 1000) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onTick(long millisUntilFinished) {
                        millisUntilFinished=(int) millisUntilFinished;
                        Integer hours_till_end= Math.toIntExact(millisUntilFinished / (1000 * 3600));
                        Integer minutes_till_end=Math.toIntExact(millisUntilFinished/ (1000*60) - hours_till_end * 60 );
                        Integer seconds_till_end=Math.toIntExact(millisUntilFinished/1000 - hours_till_end*3600 - minutes_till_end * 60);
                        holder.taskTime.setText( hours_till_end + " : " + minutes_till_end + " : " + seconds_till_end);
                    }

                    public void onFinish() {
                        holder.taskTime.setText("time over");
                    }
                }.start();

            }
        });

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainData d=dataList.get(holder.getAdapterPosition());
                //delete text from database

                database.mainDao().delete(d);
                //notify when data is eleted
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskName,taskTime;

        ImageView btEdit,btDelete,btPlay,btDone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName =itemView.findViewById(R.id.text_view);
            taskTime=itemView.findViewById(R.id.time_view);
            btEdit=itemView.findViewById(R.id.bt_edit);
            btDelete=itemView.findViewById(R.id.bt_delete);
            btPlay=itemView.findViewById(R.id.play_button);
            btDone=itemView.findViewById(R.id.done_button);
        }
    }


}
