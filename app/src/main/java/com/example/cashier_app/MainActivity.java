package com.example.cashier_app;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cashier_app.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button addinventorybuttonmain, checkoutbutton, clear, miscellaneous;
    Button bread_2_1, bread_2_2;
    Button bread_4_1, bread_4_2;
    Button bun, bun_1, bun_2, bun_3;
    Button meatball, patty, sausageroll, bananabread;
    Myadapter myadapter;
    ArrayList<Product> list;
    private DrawerLayout mDrawerLayout;
    Button scan, addtocart, bread_2, bread_4, bread_6;
    TextView name, change, paid;
    TextView price;
    TextView totalview;
    String nam, pri;
    Spinner spinneramount;
    private static final String[] paths = {"1", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    List<Product> item;
    String amount;
    RecyclerView recyclerView;
    double total;
    ClipboardManager clipboard;
    ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView mDrawer = findViewById(R.id.main_drawer);
        mDrawer.setNavigationItemSelectedListener(this);

        //mDrawerLayout.openDrawer(GravityCompat.START);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.men);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(View->{
           // mDrawerLayout.openDrawer(GravityCompat.START);
            //  mDrawerLayout.closeDrawer(GravityCompat.START);
        });


         ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

//            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

         drawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar, R.string.default_web_client_id, R.string.default_web_client_id) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //Todo
                //you don't have to write here anything to enable icon
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //Todo
                //you dont have to write here anything to enable icon
            }

        };
        //    mDrawerLayout.setDrawerListener(drawerToggle);

mDrawerLayout.addDrawerListener(drawerToggle);






        recyclerView = findViewById(R.id.userList2);
        item = new ArrayList<>();
        list = new ArrayList<>();

        myadapter = new Myadapter(this, list);
        //item.add(new Product("name.toString()","price.toString())"));
        miscellaneous = findViewById(R.id.button13);
        clear = findViewById(R.id.clear_main);
        bread_2 = findViewById(R.id.bread_2);
        bread_2_1 = findViewById(R.id.button);
        bread_2_2 = findViewById(R.id.button2);
        bread_4 = findViewById(R.id.bread_3);
        bread_4_1 = findViewById(R.id.button4);
        bread_4_2 = findViewById(R.id.button5);
        bun = findViewById(R.id.button8);
        bun_1 = findViewById(R.id.button9);
        bun_2 = findViewById(R.id.button10);
        bun_3 = findViewById(R.id.button11);
        bread_6 = findViewById(R.id.bread_5);
        meatball = findViewById(R.id.button3);
        patty = findViewById(R.id.button6);
        sausageroll = findViewById(R.id.button7);
        bananabread = findViewById(R.id.button12);
        name = findViewById(R.id.name_scan_main);
        change = findViewById(R.id.textViewchange);
        paid = findViewById(R.id.textViewpaid);
        price = findViewById(R.id.price_scan_main);
        totalview = findViewById(R.id.textViewtotal);

        addinventorybuttonmain = findViewById(R.id.add_inventory_main);
        checkoutbutton = findViewById(R.id.check_out);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        spinneramount = findViewById(R.id.amount_spinner_id);

        addtocart = findViewById(R.id.add_to_cart);

        addtocart.setOnClickListener(view -> {

            if (nam != null && pri != null && amount != null) {
                item.add(new Product(nam, pri, amount));
                recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));

                double total = 0;
                for (int i = 0; i < item.size(); i++) {
                    total = Double.parseDouble(item.get(i).getPrice()) * Double.parseDouble(item.get(i).getAmount()) + total;
                }
                ttl();
                // Myadapter.notifyDataSetChanged();

            }
        });
        checkoutbutton.setOnClickListener(view -> {
            if (item.size() > 0) {
                recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
                ttl();
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("paid");
                alert.setMessage("Amount paid");
                // Set an EditText view to get user input
                final EditText input = new EditText(MainActivity.this);
                // input.setInputType(8192);
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                alert.setView(input);
                alert.setNeutralButton("credit", (dialogInterface, i) -> alartmethod3());
                alert.setPositiveButton("Ok", (dialog, whichButton) -> {
                    if (!input.getText().toString().equals("") && Double.parseDouble(input.getText().toString()) >= total) {

                        DecimalFormat df = new DecimalFormat("####0.00");
                        //System.out.println("Value: " + df.format(value));
                        String totals = "$" + df.format(total);

                        paid.setText(String.valueOf(df.format(Double.parseDouble(input.getText().toString()))));
                        change.setText(String.valueOf(df.format(Double.parseDouble(input.getText().toString()) - total)));
                        // Do something with value!
                        alartmethod();
                    } else {
                        Toast.makeText(MainActivity.this, "enter correct input amount", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this, input.getText().toString(), Toast.LENGTH_SHORT).show();

                    }
                });
                alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
                    // Canceled.
                });

                alert.show();
            }
        });
        miscellaneous.setOnClickListener(view -> {
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle(" miscellaneous");
            alert.setMessage("price");
            // Set an EditText view to get user input
            EditText inputs = new EditText(MainActivity.this);
            // input.setInputType(8192);

            inputs.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);


            alert.setView(inputs);
            alert.setPositiveButton("Ok", (dialog, whichButton) -> {
                if (!inputs.getText().toString().equals("")) {
                    item.add(new Product("miscellaneous", inputs.getText().toString(), "1"));
                    recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
                    ttl();
                } else {
                    Toast.makeText(MainActivity.this, "input price", Toast.LENGTH_SHORT).show();
                }
            });
            alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
                // Canceled.
            });

            alert.show();


        });
        clear.setOnClickListener(view -> {
            paid.setText("");
            change.setText("");
            item.clear();
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });
        bread_2.setOnClickListener(view -> {

            item.add(new Product("bread", "2", "1"));
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });
        bread_2_1.setOnClickListener(view -> {

            item.add(new Product("bread..C", "5", "1"));
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });
        bread_2_2.setOnClickListener(view -> {

            item.add(new Product("bread.B.C", "5", "1"));
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });
        bread_4_1.setOnClickListener(view -> {

            item.add(new Product("bread.C", "10", "1"));
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });
        bread_4_2.setOnClickListener(view -> {

            item.add(new Product("bread.C", "12", "1"));
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });
        bread_4.setOnClickListener(view -> {

            item.add(new Product("bread", "4", "1"));
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });
        bread_6.setOnClickListener(view -> {

            item.add(new Product("bread", "6", "1"));
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });
        bun.setOnClickListener(view -> {

            item.add(new Product("bun", "3", "1"));
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });
        bun_1.setOnClickListener(view -> {

            item.add(new Product("bun.C", "6", "1"));
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });
        bun_2.setOnClickListener(view -> {

            item.add(new Product("bun.B.C", "7", "1"));
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });
        bun_3.setOnClickListener(view -> {

            item.add(new Product("bun.B.C.C", "9", "1"));
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });
        meatball.setOnClickListener(view -> {

            item.add(new Product("meatball", "2.50", "1"));
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });
        patty.setOnClickListener(view -> {

            item.add(new Product("patty", "5", "1"));
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });
        sausageroll.setOnClickListener(view -> {

            item.add(new Product("sausageroll", "5", "1"));
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });
        bananabread.setOnClickListener(view -> {

            item.add(new Product("banana bread", "6", "1"));
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            ttl();
        });


        amount = "1";
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneramount.setAdapter(adapter);
        spinneramount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                    case 1:
                        amount = "1";
                        break;
                    case 2:
                        amount = "2";
                        break;
                    case 3:
                        amount = "3";
                        break;
                    case 4:
                        amount = "4";
                        break;
                    case 5:
                        amount = "5";
                        break;
                    case 6:
                        amount = "6";
                        break;
                    case 7:
                        amount = "7";
                        break;
                    case 8:
                        amount = "8";
                        break;
                    case 9:
                        amount = "9";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        scan = findViewById(R.id.scan_sell);
        scan.setOnClickListener(v ->
                scanCode());

        addinventorybuttonmain.setOnClickListener(view -> startActivity(new Intent(this, AddInventory.class)));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.addinvent) {
            startActivity(new Intent(this, AddInventory.class));
        } else if (item.getItemId() == R.id.unhideProjects) {
            alartmethod2();
        }

        return true;
    }

    public void ttl() {
        total = 0;
        for (int i = 0; i < item.size(); i++) {
            total = Double.parseDouble(item.get(i).getPrice()) + total;
        }
        DecimalFormat df = new DecimalFormat("####0.00");
        //System.out.println("Value: " + df.format(value));
        String totals = "$" + df.format(total);
        totalview.setText(totals);

        // Toast.makeText(MainActivity.this, String.valueOf(total), Toast.LENGTH_SHORT).show();

    }


    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        options.setTorchEnabled(true);
        barLaucher.launch(options);


    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result ->
    {
        if (result.getContents() != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child("inventory").child(result.getContents());

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        if (!Objects.requireNonNull(snapshot.child("name").getValue()).toString().equals("")) {
                            // Toast.makeText(MainActivity.this, Objects.requireNonNull(snapshot.child("name").getValue()).toString(), Toast.LENGTH_SHORT).show();
                            name.setText(Objects.requireNonNull(snapshot.child("name").getValue()).toString());
                            price.setText(Objects.requireNonNull(snapshot.child("price").getValue()).toString());

                            nam = (Objects.requireNonNull(snapshot.child("name").getValue()).toString());
                            pri = (Objects.requireNonNull(snapshot.child("price").getValue()).toString());
                            item.add(new Product(nam, pri, "1"));
                            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
                            ttl();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Item cannot be found", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "please add item to database", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    });

    public void alartmethod3() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Add to credit list");
        alert.setMessage("enter name of person");
// Set an EditText view to get user input
        final EditText input = new EditText(MainActivity.this);
        // input.setInputType(8192);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        alert.setView(input);

        alert.setPositiveButton("Ok", (dialog, whichButton) -> {

            String id = getAlphaNumericString();

            Date date2 = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter2 = new SimpleDateFormat("y");
            // SimpleDateFormat formatter3 = new SimpleDateFormat("MM");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter4 = new SimpleDateFormat("D");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter5 = new SimpleDateFormat("k");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter6 = new SimpleDateFormat("m");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter7 = new SimpleDateFormat("s");

            String str6 = formatter7.format(date2);
            if (str6.length() == 1) {
                str6 = "0" + str6;
            }

            String str5 = formatter6.format(date2);
            if (str5.length() == 1) {
                str5 = "0" + str5;
            }

            String str4 = formatter5.format(date2);
            if (str4.length() == 1) {
                str4 = "0" + str4;
            }

            String str3 = formatter4.format(date2);
            if (str3.length() == 1) {
                str3 = "00" + str3;
            } else if (str3.length() == 2) {
                str3 = "0" + str3;
            }

            String str1 = formatter2.format(date2);
            String day = str1 + str3;
            String seconds = str4 + str5 + str6;


            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'AT' HH:mm:ss");
            String str = formatter.format(date);


            // Do something with value!
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child("credits").child(day).child(id);
            for (int j = 0; j < item.size(); j++) {
                myRef.child("products").child(String.valueOf(j)).setValue(item.get(j).getName());
            }
            myRef.child("total").setValue(total);
            myRef.child("date").setValue(str);
            myRef.child("id").setValue(id);
            myRef.child("timeseconds").setValue(seconds);
            myRef.child("personname").setValue(input.getText().toString());


            Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
            item.clear();
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            // myRef.child("time").setValue(str);


        });
        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            // Canceled.
        });
        alert.show();
    }


    public void alartmethod() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Add to invoice");
        alert.setMessage("");
// Set an EditText view to get user input

        alert.setPositiveButton("Ok", (dialog, whichButton) -> {
            String id = getAlphaNumericString();

            Date date2 = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter2 = new SimpleDateFormat("y");
            // SimpleDateFormat formatter3 = new SimpleDateFormat("MM");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter4 = new SimpleDateFormat("D");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter5 = new SimpleDateFormat("k");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter6 = new SimpleDateFormat("m");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter7 = new SimpleDateFormat("s");

            String str6 = formatter7.format(date2);
            if (str6.length() == 1) {
                str6 = "0" + str6;
            }

            String str5 = formatter6.format(date2);
            if (str5.length() == 1) {
                str5 = "0" + str5;
            }

            String str4 = formatter5.format(date2);
            if (str4.length() == 1) {
                str4 = "0" + str4;
            }

            String str3 = formatter4.format(date2);
            if (str3.length() == 1) {
                str3 = "00" + str3;
            } else if (str3.length() == 2) {
                str3 = "0" + str3;
            }


            String str1 = formatter2.format(date2);
            String day = str1 + str3;
            String seconds = str4 + str5 + str6;


            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'AT' HH:mm:ss");
            String str = formatter.format(date);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child("invoices").child(day).child(id);
            for (int i = 0; i < item.size(); i++) {
                myRef.child("products").child(String.valueOf(i)).setValue(item.get(i).getName());
            }
            myRef.child("total").setValue(total);
            myRef.child("date").setValue(str);
            myRef.child("id").setValue(id);
            myRef.child("timeseconds").setValue(seconds);

            //   Toast.makeText(MainActivity.this, seconds, Toast.LENGTH_SHORT).show();

            Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
            item.clear();
            recyclerView.setAdapter(new Myadapter(getApplicationContext(), item));
            // myRef.child("time").setValue(str);


        });
        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            // Canceled.
        });
        alert.show();
    }


    public void alartmethod2() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Things to add");
        alert.setMessage("profit/loss, change price of buttons,... ");
// Set an EditText view to get user input
        alert.setPositiveButton("Ok", (dialog, whichButton) -> {

        });
        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            // Canceled.
        });
        alert.show();
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


    private void navigate(int mSelectedId) {

        if (mSelectedId == R.id.graph_menu) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Intent i = new Intent(this, graph.class);
            startActivity(i);
        }
        if (mSelectedId == R.id.graph_menu2) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Intent i = new Intent(this, graph2.class);
            // Toast.makeText(getApplicationContext(),"not ready",Toast.LENGTH_LONG).show();
            startActivity(i);
        }
        if (mSelectedId == R.id.invoice_menu) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Intent i = new Intent(this, InvoiceActivity.class);
            startActivity(i);
        }
        if (mSelectedId == R.id.credit_menu) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Intent i = new Intent(this, CreditActivity.class);
            startActivity(i);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        int mSelectedId = item.getItemId();
        navigate(mSelectedId);
        return true;
    }
}