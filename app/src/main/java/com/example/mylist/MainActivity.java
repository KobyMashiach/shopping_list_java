package com.example.mylist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
public class MainActivity extends AppCompatActivity {
    ListView listViewData;
    ArrayAdapter<String> adapter;
    Product allProducts = new Product();

    static String databaseURL = "https://mylist-9460b-default-rtdb.firebaseio.com/";
    FirebaseDatabase database = FirebaseDatabase.getInstance(databaseURL);
    DatabaseReference myRef = database.getReference("products");
    HashMap update = new HashMap();


    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    static String returnStringName, returnStringPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewData = findViewById(R.id.listViewData);
        adapter = new CustomAdapter(this,allProducts.getAllProductsString());
        listViewData.setAdapter(adapter);

        updateData();
        readData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item_done){
            btn_item_done();
        }
        if(item.getItemId() == R.id.item_add){
            btn_item_add();
        }
        if(item.getItemId() == R.id.item_remove){
            btn_item_remove();
        }
        if(item.getItemId() == R.id.item_edit){
            btn_item_edit();
        }
        if(item.getItemId() == R.id.logout){
            btn_logout();
        }

            return super.onOptionsItemSelected(item);
    }

    private void btn_item_done(){
        String itemSelected = "Selected: ";
        boolean first = true;
        for (int i=0; i<listViewData.getCount(); i++){
            if(((CheckBox)listViewData.getChildAt(i).findViewById(R.id.cbItem)).isChecked()){
                if(first){
                    first = false;
                    itemSelected += " " + allProducts.getNameById(i);
                }else{
                    itemSelected += ", " + allProducts.getNameById(i);
                }
            }
        }
        Toast.makeText(MainActivity.this, itemSelected, Toast.LENGTH_SHORT).show();
    }

    private void btn_item_add() {
        mainAddProduct();
    }

    private void btn_item_remove() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want remove that products?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mainRemoveProduct();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void btn_item_edit() {
        int count = 0;
        int index = 0;
        for (int i=0; i<listViewData.getCount(); i++){
            if(((CheckBox)listViewData.getChildAt(i).findViewById(R.id.cbItem)).isChecked()){
                count++;
                index = i;
            }
        }
        if(count == 1){
            int finalIndex = index;
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want edit that products?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mainRemoveProduct(finalIndex);
                            mainAddProduct();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }else {
            Toast.makeText(this, "To edit please check just 1 product", Toast.LENGTH_SHORT).show();
        }
    }

    private void btn_logout() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void mainAddProduct(){
        Intent intent = new Intent(this,edit_product.class);
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
    }

    private void addProduct(String name,String price) {
        allProducts.addProduct(name,price);
        adapter.notifyDataSetChanged();
        adapter.clear();
        adapter.addAll(allProducts.getAllProductsString());
        Toast.makeText(MainActivity.this, name + " add Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // Get String data from Intent
                returnStringName = data.getStringExtra("EXTRA_NAME");
                returnStringPrice = data.getStringExtra("EXTRA_PRICE");

                addProduct(returnStringName,returnStringPrice);
                update.put("name", returnStringName);
                update.put("price", returnStringPrice);
                myRef.child(returnStringName + ": " + returnStringPrice).setValue(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }
        }
    }

    private void mainRemoveProduct(){
        for (int i=listViewData.getCount()-1; i>=0; i--){
            if(((CheckBox)listViewData.getChildAt(i).findViewById(R.id.cbItem)).isChecked()){
                myRef.child(allProducts.getNameById(i) + ": " + allProducts.getPriceById(i)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
                removeProduct(allProducts.getNameById(i),allProducts.getPriceById(i));
            }
        }
    }

    private void mainRemoveProduct(int index){
                myRef.child(allProducts.getNameById(index) + ": " + allProducts.getPriceById(index)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
                removeProduct(allProducts.getNameById(index),allProducts.getPriceById(index));
    }

    private void removeProduct(String name,String price) {
        allProducts.removeProduct(name,price);
        adapter.notifyDataSetChanged();
        adapter.clear();
        adapter.addAll(allProducts.getAllProductsString());
        Toast.makeText(MainActivity.this, name + " removed Successfully", Toast.LENGTH_SHORT).show();
    }

    private void readData(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clear();
                allProducts.clear();
                for (DataSnapshot item: snapshot.getChildren()){
                    adapter.add(item.getValue(Product.class).getName()+ ": " +
                            item.getValue(Product.class).getPrice());
                    allProducts.addProduct(item.getValue(Product.class).getName(), item.getValue(Product.class).getPrice());
                }
                listViewData.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateData(){
        myRef.updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}