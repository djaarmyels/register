package com.example.cashier_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class SecondActivity extends AppCompatActivity {

 //   ImageView mainImageView;
    TextView title, description;

    String data1,data2,data5,data6,name;
    Button delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        title=findViewById(R.id.title);
        description= findViewById(R.id.descrition);
        getData();
        setData();
        delete= findViewById(R.id.button14);
        if(Objects.equals(data6, "credit")){
            delete.setVisibility(View.VISIBLE);
        }else if(Objects.equals(data6, "invoice")){
            delete.setVisibility(View.GONE);
        }

        delete.setOnClickListener(view -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child("credits").child(data5).child(data2);
            myRef.removeValue();



            String id = getAlphaNumericString();

            Date date2 = new Date();
            @SuppressLint("SimpleDateFormat")SimpleDateFormat formatter2 = new SimpleDateFormat("y");
            // SimpleDateFormat formatter3 = new SimpleDateFormat("MM");
            @SuppressLint("SimpleDateFormat")SimpleDateFormat formatter4 = new SimpleDateFormat("D");
            @SuppressLint("SimpleDateFormat")SimpleDateFormat formatter5 = new SimpleDateFormat("k");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter6 = new SimpleDateFormat("m");
            @SuppressLint("SimpleDateFormat")SimpleDateFormat formatter7 = new SimpleDateFormat("s");

            String str6 = formatter7.format(date2);
            if(str6.length()==1){
                str6="0"+str6;
            }

            String str5 = formatter6.format(date2);
            if(str5.length()==1){
                str5="0"+str5;
            }

            String str4 = formatter5.format(date2);
            if(str4.length()==1){
                str4="0"+str4;
            }

            String str3 = formatter4.format(date2);
            if(str3.length()==1){
                str3="00"+str3;
            }else if(str3.length()==2){
                str3="0"+str3;
            }


            String str1 = formatter2.format(date2);
            String day=str1+str3;
            String seconds=str4+str5+str6;



            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'AT' HH:mm:ss");
            String str = formatter.format(date);
            FirebaseDatabase database1 = FirebaseDatabase.getInstance();
            DatabaseReference myRef3 = database1.getReference().child("invoices").child(day).child(id);

                myRef3.child("products").setValue("credit: "+name);

            myRef3.child("total").setValue(data1);
            myRef3.child("date").setValue(str);
            myRef3.child("id").setValue(id);
            myRef3.child("timeseconds").setValue(seconds);





            Intent intent=new Intent(SecondActivity.this, CreditActivity.class);
            startActivity(intent);
            finish();
        });

   //     mainImageView = findViewById(R.id.mainimageView);



    }
    private void getData(){
       // if(getIntent().hasExtra("myImage")&&getIntent().hasExtra("data1")&&getIntent().hasExtra("data2")){
        if(getIntent().hasExtra("data1")&&getIntent().hasExtra("data2")&&getIntent().hasExtra("data5")){
            data1=getIntent().getStringExtra("data1");
            data2=getIntent().getStringExtra("data2");
            name=getIntent().getStringExtra("name");

            data5=getIntent().getStringExtra("data5");
            data6=getIntent().getStringExtra("data6");
        //    myImage=getIntent().getIntExtra("myImage",1);
            Toast.makeText(this,"data",Toast.LENGTH_LONG).show();
        }else{

            Toast.makeText(this,"no data",Toast.LENGTH_LONG).show();
        }
    }
    private void setData(){
        title.setText(data1);
        String h=data2+"_"+data5;
        description.setText(h);
     //   mainImageView.setImageResource(myImage);
    }



    static String getAlphaNumericString() {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(20);

        for (int i = 0; i < 20; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

}