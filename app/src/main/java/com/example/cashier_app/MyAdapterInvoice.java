package com.example.cashier_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterInvoice extends RecyclerView.Adapter<MyAdapterInvoice.MyViewHolder> {

    List<Double> data1;
    List<Long> data5;
    List<String> data3, data2;
  //  int images[];
    Context context;
    public MyAdapterInvoice(Context ct, List<Double> s1, List<String> s2, List<String> s3,List<Long> s5){
        context=ct;
        data1=s1;
        data2=s2;
        data3=s3;
        data5=s5;
       // images=img;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            holder.myText1.setText(String.valueOf(data1.get(position)));
            holder.myText2.setText(String.valueOf(data2.get(position)));
        holder.myText3.setText(String.valueOf(data3.get(position)));

          //  holder.myImage.setImageResource(images[position]);

            holder.mainLayout.setOnClickListener(v -> {
                Intent intent=new Intent(context, SecondActivity.class);
                intent.putExtra("data1",String.valueOf(data1.get(position)));
                intent.putExtra("data2",String.valueOf(data2.get(position)));
                intent.putExtra("data5",String.valueOf(data5.get(position)));
                intent.putExtra("data6","invoice");
                //intent.putExtra("myImage",images[position]);
                context.startActivity(intent);
            });
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView myText1,myText2,myText3;
      //  ImageView myImage;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            myText1= itemView.findViewById(R.id.myText1);

            myText2= itemView.findViewById(R.id.myText2);
            myText3= itemView.findViewById(R.id.myText3);
           // myImage= itemView.findViewById(R.id.myImageView);
            mainLayout=itemView.findViewById(R.id.mainLayout);
        }
    }
}
