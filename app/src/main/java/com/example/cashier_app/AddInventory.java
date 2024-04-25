package com.example.cashier_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class AddInventory extends AppCompatActivity {
    Button addinventorybutton;
    Button btn_scan;
    EditText editText_inventoryname, editText_inventorydollar, editText_inventorycents;
    EditText textView_newcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);

        editText_inventoryname = findViewById(R.id.new_inventory_name);
        editText_inventorydollar = findViewById(R.id.new_inventory_price_dollar);
        editText_inventorycents = findViewById(R.id.new_inventory_price_cents);
        textView_newcode = findViewById(R.id.new_inventory_code);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("inventory");

        addinventorybutton = findViewById(R.id.add_inventory);

        addinventorybutton.setOnClickListener(view -> {


            if (!editText_inventorydollar.getText().toString().equals("") && !editText_inventoryname.getText().toString().equals("")) {
                try {
                    String productid = textView_newcode.getText().toString();
                    String name = editText_inventoryname.getText().toString();
                    String cents = (editText_inventorycents.getText().toString());
                    Double price = Double.parseDouble(editText_inventorydollar.getText() + "." + cents);
                    String centsb = (editText_inventorycents.getText().toString());
                    Double priceb = Double.parseDouble(editText_inventorydollar.getText() + "." + centsb);
                    HelperClassAddInventory helperClass = new HelperClassAddInventory(name, price,priceb);

                    myRef.child(productid).setValue(helperClass);
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(this, "added", Toast.LENGTH_SHORT).show();
                finish();

            }else {
                Toast.makeText(this, "enter details", Toast.LENGTH_SHORT).show();
            }


        });

        btn_scan = findViewById(R.id.add_new_inventory_button);
        btn_scan.setOnClickListener(v ->
                scanCode());


    }


    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setTorchEnabled(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result ->
    {
        if (result.getContents() != null) {
            textView_newcode.setText(result.getContents());


        }
    });


}