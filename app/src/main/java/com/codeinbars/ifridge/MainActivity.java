package com.codeinbars.ifridge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //All declarations
    private TextView titleApp;
    private Typeface Sketch3D;
    private ListView foodList;
    private String fontSkecth = "fonts/Sketch3D.otf"; //This String containt the directory to the special font
    private ArrayList<Food> foodListMain; //Save the food in arraylist
    private static ArrayList<String> values = new ArrayList<>(); //use to pass all food in the adapter
    private FloatingActionButton floatButton; //Im never use that but np
    private ArrayAdapter<String>adapter;
    private Button btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatButton = findViewById(R.id.floatingActionButton);
        titleApp = findViewById(R.id.titleApp2);
        foodList = findViewById(R.id.foodList);
        btn = findViewById(R.id.button3);

        this.Sketch3D = Typeface.createFromAsset(getAssets(),fontSkecth);
        titleApp.setTypeface(Sketch3D); //Change the typeface


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(MainActivity.this, btn);
                popupMenu.getMenuInflater().inflate(R.menu.menu_info, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){

                            case R.id.about:
                                Toast.makeText(getApplicationContext(), "CODEINBARS", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        MethodsFile.statusFile();
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {

        }
        int permission2Check = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission2Check != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
        } else {

        }





    }

    @Override
    protected void onResume() {
        super.onResume();
        //If fileClear method return false, save all food in the foodListMain and call converList method
        if(!MethodsFile.fileClear()){


            foodListMain = MethodsFile.readFile();
            convertList(foodListMain, values);
            adapter = new ArrayAdapter<String>(this,R.layout.listviewstyle, R.id.bText, values);

            foodList.setAdapter(adapter);

            foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    /**
                     * @param item save the position of list
                     * @param dateF save the date when the food expire
                     * @param dateB save the date when the food buy
                     */
                    int item = i;
                    String dateF = (String) foodListMain.get(i).getDateExpire();
                    String dateB = (String) foodListMain.get(i).getDateBuy();

                    Toast.makeText(getApplicationContext(), getString(R.string.datebuy)+": " + dateB+"\n"+getString(R.string.dateexpiry) + ": " + dateF, Toast.LENGTH_SHORT).show();

                }
            });

            foodList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    int item = i;
                    MethodsFile.deletedFood(foodListMain.get(item).getName());
                    values.remove(item);
                    foodListMain.remove(item);
                    adapter.notifyDataSetChanged();
                    return false;
                }
            });


        }else{

            String[] values2 = {getString(R.string.nocingHere)};
            adapter = new ArrayAdapter<String>(this,R.layout.listviewstyle, R.id.bText, values2);
            foodList.setAdapter(adapter);
        }






        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FoodAdd.class);
                startActivity(intent);
            }
        });
        //MethodsFile.sendNotification(foodListMain,MainActivity.this);
    }

    //This method pass the names of food to a Array String
    public static void convertList(ArrayList<Food> list, ArrayList<String> val){

        val.clear();
        for(int i = 0; i<list.size(); i++){

            Log.d("convertList", "Size: " + list.size());
            val.add(list.get(i).getName());
            Log.d("convertList","Food: " + val.get(i));

        }
    }
}
