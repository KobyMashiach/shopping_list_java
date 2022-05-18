package com.example.mylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class edit_product extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        Button epBtn = (Button) findViewById(R.id.editProduct_btn);
        epBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToMainActivity();
            }
        });
    }

    public void returnToMainActivity() {
        EditText epInputName = (EditText)findViewById(R.id.editProduct_et_ProductName);
        String nameInput = epInputName.getText().toString();
        EditText epInputPrice = (EditText)findViewById(R.id.editProduct_et_ProductPrice);
        String priceInput = epInputPrice.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("EXTRA_NAME",nameInput);
        intent.putExtra("EXTRA_PRICE",priceInput);
        setResult(RESULT_OK, intent);
        finish();

    }
}