package com.example.cashier_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class graph2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {
    TextView ggg;


    List<Double> al;
    List<Double> al2;
    LineGraphSeries<DataPoint> series;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph2);

        //editText.findViewById(R.id.function_id);

        GraphView graphView = findViewById(R.id.graph);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(-2.0);
        graphView.getViewport().setMaxY(1000);

        graphView.getViewport().setMinX(-1.5);
        graphView.getViewport().setMaxX(25);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScalable(true);
        graphView.setTitle("Sales today");
        ggg = findViewById(R.id.textView6);
        series = new LineGraphSeries<>();

        graphView.addSeries(series);


        Date date2 = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter2 = new SimpleDateFormat("y");

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter4 = new SimpleDateFormat("D");




        String str3 = formatter4.format(date2);
        if (str3.length() == 1) {
            str3 = "00" + str3;
        } else if (str3.length() == 2) {
            str3 = "0" + str3;
        }


        String str1 = formatter2.format(date2);
        String days = str1 + str3;
        pass = "no pass";

        al = new ArrayList<>();
        al2 = new ArrayList<>();
        //al.add(0.0);
        for (int c = 0; c <= 26; c++) {
            al2.add(c, 0.0);
        }
        for (int c = 0; c <= 25; c++) {
            al.add(c, 0.0);
        }

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("invoices").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot entrySnapshot : snapshot.getChildren()) {
                    int hoursold;
                    for (DataSnapshot propertySnapshot : entrySnapshot.getChildren()) {
                        //System.out.println(propertySnapshot.getKey()+": "+propertySnapshot.getValue(String.class));
                        if (Integer.parseInt(days) - Integer.parseInt(Objects.requireNonNull(entrySnapshot.getKey())) == 0) {

                            hoursold = Integer.parseInt(Objects.requireNonNull(propertySnapshot.child("timeseconds").getValue()).toString().substring(0, 2));
                           if(hoursold==24){
                               hoursold=0;
                           }
                            al.set(hoursold, Double.parseDouble(Objects.requireNonNull(propertySnapshot.child("total").getValue()).toString()) + al.get(hoursold));


                        }
                    }
                }


                if (al2.size() <= 1) {
                    Toast.makeText(graph2.this, "not enough data", Toast.LENGTH_LONG).show();
                }




                series.setAnimated(true);
                 graphView.getViewport().setMaxY((int)(Collections.max(al)+10));
                series.resetData(generateData());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private DataPoint[] generateData() {


        double x = 0;

        List<Double> tail = al;

        DataPoint[] values = new DataPoint[tail.size()];

        for (int i = 0; i <= tail.size() - 1; i++) {


            double y = tail.get(i);
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
            x = x + 1;
        }

        return values;

    }



    @Override
    public void onClick(View v) {

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        // navigate(mSelectedId);
        return true;
    }
}