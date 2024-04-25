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

public class graph extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {
    TextView ggg;

    Double[] total;
    List<Double> al;
    List<Double> al2;
    List<Double> al3;
    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);


        GraphView graphView = findViewById(R.id.graph);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(-2.0);
        graphView.getViewport().setMaxY(1000);
        //graphView.getViewport().setScalableY(true);
        graphView.getViewport().setMinX(-1.5);
        graphView.getViewport().setMaxX(35);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScalable(true);
        graphView.setTitle("Sales in the last 30 days");
        ggg = findViewById(R.id.textView6);
        series = new LineGraphSeries<>();

        graphView.addSeries(series);


        Date date2 = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter2 = new SimpleDateFormat("y");
        // SimpleDateFormat formatter3 = new SimpleDateFormat("MM");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter4 = new SimpleDateFormat("D");


        String str3 = formatter4.format(date2);
        String str33 = formatter4.format(date2);
        if (str3.length() == 1) {
            str3 = "00" + str3;
        } else if (str3.length() == 2) {
            str3 = "0" + str3;
        }


        String str1 = formatter2.format(date2);
        String days = str1 + str3;


        al = new ArrayList<>();
        al2 = new ArrayList<>();
        al3 = new ArrayList<>();
        for (int yo = 0; yo < 30; yo++) {
            al2.add(yo, 0.0);
        }

        for (int yo = 0; yo < 31; yo++) {
            al.add(yo, 0.0);
        }
        for (int yo = 0; yo < 31; yo++) {
            al3.add(yo, (double) yo);
        }


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("invoices").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double max = 1.0;


                for (DataSnapshot entrySnapshot : snapshot.getChildren()) {

                    int currentday = 0;
                    for (DataSnapshot propertySnapshot : entrySnapshot.getChildren()) {
                        //System.out.println(propertySnapshot.getKey()+": "+propertySnapshot.getValue(String.class));
                        currentday = Integer.parseInt(days);
                        if (Integer.parseInt(str33) <= 30 && Integer.parseInt(str1) - Integer.parseInt(Objects.requireNonNull(entrySnapshot.getKey()).substring(0, 4)) == 1) {
                            currentday = Integer.parseInt(days) - 1000 + Integer.parseInt(str33) + 365;

                        }
                        if (currentday - Integer.parseInt(Objects.requireNonNull(entrySnapshot.getKey())) <= 30) {
                            int look = currentday - Integer.parseInt(Objects.requireNonNull(entrySnapshot.getKey()));
                            al.set(look, al.get(look) + Double.parseDouble(Objects.requireNonNull(propertySnapshot.child("total").getValue()).toString()));
                            if (al.get(look) > max) {
                                max = al.get(look);
                            }
                        }


                    }


                }
                Collections.reverse(al);
                lagrange( al, 10.0);
                Toast.makeText(graph.this, String.valueOf( lagrange( al, 15.0)), Toast.LENGTH_SHORT).show();
                graphView.getViewport().setMaxY(max + 10.0);
                series.setAnimated(true);
                series.resetData(generateData());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private DataPoint[] generateData() {
        //int count = al2.size();

        double x = 0;
        /**   List<Double> better;
        better = new ArrayList<>();
        for(int i = 0; i <= 30; i++){
            better.add(i,lagrange( al,  ((double)i*0.5))   );
        }
*/

         List<Double> tail = al;
      //  List<Double> tail = better;
        DataPoint[] values = new DataPoint[tail.size()];


        for (int i = 0; i <= tail.size() - 1; i++) {


            double y = tail.get(i);
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
            x = x + 1;
        }

        //t=t+0.1;
        return values;

    }


    public double lagrange(List<Double> al, Double x) {
        double yp = 0.0;
        int n = 30;
        for (int i = 1; i <= n; i++) {
            double p = 1.0;

            for (int j = 1; j <= n; j++) {

                if (i != j) {
                    p = p * (x - al3.get(j)) / (al3.get(i) - al3.get(j));
                }


            }
            yp = yp + p * al.get(i);


        }
        return yp;
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