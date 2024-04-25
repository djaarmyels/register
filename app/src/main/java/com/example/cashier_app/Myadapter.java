package com.example.cashier_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder>{


    Context context;

    static List<Product> list;



    public Myadapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;

       
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,viewGroup,false);

        return  new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Product product=list.get(i);

        myViewHolder.name.setText(product.getName());

        myViewHolder.price.setText(String.valueOf(product.getPrice()));
        myViewHolder.amount.setText(String.valueOf(product.getAmount()));


        myViewHolder.mainLayout.setOnClickListener(v -> {

        });


    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView mainLayout;
        LinearLayout uhh;
        TextView name, price,amount;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.product_name_list);

                    price =itemView.findViewById(R.id.price_list);
            amount =itemView.findViewById(R.id.amount_list);

            mainLayout=itemView.findViewById(R.id.mainLayout);
            uhh=itemView.findViewById(R.id.h);

       uhh.setOnLongClickListener(view -> {
           list.remove(getAdapterPosition());
           notifyItemRemoved(getAdapterPosition());

           return true;
       });

        }
    }


}
