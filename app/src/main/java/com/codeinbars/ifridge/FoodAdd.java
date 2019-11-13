package com.codeinbars.ifridge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class FoodAdd extends AppCompatActivity {

    Button add, dExpire, dBuy;
    EditText dateb, datee, foodn;
    static int dia, mes, anio;
    static Calendar c;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_add);

        add = findViewById(R.id.button);
        dExpire = findViewById(R.id.btnExpiryDate);
        dBuy = findViewById(R.id.btnBuyDate);
        dateb = findViewById(R.id.datebuyadd);
        datee = findViewById(R.id.dateexpiryadd);
        foodn = findViewById(R.id.foodnameadd);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food f = new Food(foodn.getText().toString(),dateb.getText().toString(), datee.getText().toString());
                MethodsFile.writeFile(f);
                Toast.makeText(getApplicationContext(),getString(R.string.foodaddlist),Toast.LENGTH_SHORT).show();
            }
        });

        dExpire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                anio = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(FoodAdd.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        datee.setText(day+"/"+(month+1)+"/"+year);
                    }
                },dia,mes,anio);
                datePickerDialog.show();
            }
        });

        dBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                anio = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(FoodAdd.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        dateb.setText(day+"/"+(month+1)+"/"+year);
                    }
                },dia,mes,anio);
                datePickerDialog.show();
            }
        });


    }
}
