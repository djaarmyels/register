package com.example.cashier_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InvoiceActivity extends AppCompatActivity {

    List<Double> al;
    List<String> al2;
    List<String> al3;
    List<Long> al4;
    List<Long> al5;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        recyclerView = findViewById(R.id.recyclerView);







        al = new ArrayList<>();
        al2 = new ArrayList<>();
        al3 = new ArrayList<>();
        al4 = new ArrayList<>();
        al5 = new ArrayList<>();
        //al.add(0.0);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("invoices").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int k = 0;

                for (DataSnapshot entrySnapshot : snapshot.getChildren()) {

                    for (DataSnapshot propertySnapshot : entrySnapshot.getChildren()) {
                        //System.out.println(propertySnapshot.getKey()+": "+propertySnapshot.getValue(String.class));

                        al.add(k, Double.parseDouble(Objects.requireNonNull(propertySnapshot.child("total").getValue()).toString()));
                        al2.add(k, Objects.requireNonNull(propertySnapshot.child("id").getValue()).toString());
                        al3.add(k, Objects.requireNonNull(propertySnapshot.child("date").getValue()).toString());
                        al4.add(k, Long.parseLong(Objects.requireNonNull(propertySnapshot.child("timeseconds").getValue()).toString()));
                        al5.add(k, Long.valueOf(Objects.requireNonNull(entrySnapshot.getKey())));
                        k++;
                    }

                }

                Double tempVar;
                String tempVar2;
                String tempVar3;
               long tempVar5;
                long tempVar4;

                for (int y = 0; y < al4.size()-1; y++)
                {
                    for(int j = 0; j < al4.size()-y-1; j++)
                    {
                        if(al4.get(j) < al4.get(j + 1))
                        {
                            tempVar4 = al4.get(j + 1);
                            al4.set(j + 1, al4.get(j));
                            al4.set(j, tempVar4);
                            tempVar = al.get(j + 1);
                            al.set(j + 1, al.get(j));
                            al.set(j, tempVar);

                            tempVar2 = al2.get(j + 1);
                            al2.set(j + 1, al2.get(j));
                            al2.set(j, tempVar2);

                            tempVar3 = al3.get(j + 1);
                            al3.set(j + 1, al3.get(j));
                            al3.set(j, tempVar3);

                            tempVar5 = al5.get(j + 1);
                            al5.set(j + 1, al5.get(j));
                            al5.set(j, tempVar5);
                        }
                    }
                }


                for (int y = 0; y < al5.size()-1; y++)
                {
                    for(int j = 0; j < al5.size()-y-1; j++)
                    {
                        if(al5.get(j) < al5.get(j + 1))
                        {
                            tempVar4 = al4.get(j + 1);
                            al4.set(j + 1, al4.get(j));
                            al4.set(j, tempVar4);
                             tempVar = al.get(j + 1);
                            al.set(j + 1, al.get(j));
                            al.set(j, tempVar);

                            tempVar2 = al2.get(j + 1);
                            al2.set(j + 1, al2.get(j));
                            al2.set(j, tempVar2);

                            tempVar3 = al3.get(j + 1);
                            al3.set(j + 1, al3.get(j));
                            al3.set(j, tempVar3);

                            tempVar5 = al5.get(j + 1);
                            al5.set(j + 1, al5.get(j));
                            al5.set(j, tempVar5);
                        }
                    }
                }




                MyAdapterInvoice myAdapter = new MyAdapterInvoice(InvoiceActivity.this, al, al2, al3,al5);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(InvoiceActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

}