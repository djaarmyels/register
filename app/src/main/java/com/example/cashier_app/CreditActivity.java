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

public class CreditActivity extends AppCompatActivity {

    List<Double> al;
    List<String> al2;
    List<String> al3;
    List<Long> al4;
    List<Long> al5;
    List<String> al6;
    String credit;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        recyclerView = findViewById(R.id.recyclerView);
        credit="credit";

        al = new ArrayList<>();
        al2 = new ArrayList<>();
        al3 = new ArrayList<>();
        al4 = new ArrayList<>();
        al5 = new ArrayList<>();
        al6 = new ArrayList<>();


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("credits").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int k = 0;

                for (DataSnapshot entrySnapshot : snapshot.getChildren()) {

                    for (DataSnapshot propertySnapshot : entrySnapshot.getChildren()) {
                        //System.out.println(propertySnapshot.getKey()+": "+propertySnapshot.getValue(String.class));

                        al.add(k, Double.parseDouble(Objects.requireNonNull(propertySnapshot.child("total").getValue()).toString()));
                        al2.add(k, Objects.requireNonNull(propertySnapshot.child("personname").getValue()).toString());
                        al3.add(k, Objects.requireNonNull(propertySnapshot.child("date").getValue()).toString());
                        al4.add(k, Long.parseLong(Objects.requireNonNull(propertySnapshot.child("timeseconds").getValue()).toString()));
                        al5.add(k, Long.valueOf(Objects.requireNonNull(entrySnapshot.getKey())));
                        al6.add(k, Objects.requireNonNull(propertySnapshot.child("id").getValue()).toString());
                        k++;
                    }
                }

                Double tempVar;
                String tempVar2;
                String tempVar3;
                String tempVar6;
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

                            tempVar6 = al6.get(j + 1);
                            al6.set(j + 1, al6.get(j));
                            al6.set(j, tempVar6);
                        }
                    }
                }




                MyAdapterCredit myAdapter = new MyAdapterCredit(CreditActivity.this, al, al2, al3,al5,al6);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(CreditActivity.this));
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