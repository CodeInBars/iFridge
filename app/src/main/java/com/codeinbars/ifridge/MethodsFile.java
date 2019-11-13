package com.codeinbars.ifridge;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.os.Environment;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MethodsFile {

    static PendingIntent pendingIntent;
    static String Channel_ID = "Notification";
    static int notification_id = 0;
    static String directory = Environment.getExternalStorageDirectory() + "/ifridgeFood.txt";
    static String directoryTMP = Environment.getExternalStorageDirectory() + "/ifridgeFood.tmp";
    //File when all food data is saved
    static File fich = new File(directory);

    //This method check if file fich exist or not
    //If not exists it create
    public static void statusFile(){


        if(!fich.exists()){
            try {
                fich.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void sendNotification(ArrayList<Food> food, Context context){

        SimpleDateFormat p = new SimpleDateFormat("dd/MM/yyyy");
        Date dateToday = new Date();
        ArrayList<Date> dates = new ArrayList<>();
        for(int i = 0; i<food.size(); i++){

            try{
                dates.add(new SimpleDateFormat("dd/MM/yyyy").parse(food.get(i).getDateExpire()));
            }catch (ParseException e){

            }

        }
        for(int x = 0; x<dates.size(); x++){

            if((dates.get(x).compareTo(dateToday))==0){
                createNotification(food.get(x).getName(),context);
            }
        }

    }

    public static void createNotification(String foodexpire, Context context){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Channel_ID);
        builder.setSmallIcon(R.drawable.ic_local_pizza_black_24dp);
        builder.setContentTitle("iFridge");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.BLUE, 1000, 1000);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentText("Tu " + foodexpire + " Esta apunto de caducar");

        NotificationManagerCompat  notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notification_id, builder.build());
    }

    /**
     *
     * @param foodname i pass the name of the food to be erased
     *
     */
    public static void deletedFood(String foodname){

            BufferedReader br;
            BufferedWriter bw;

            try{
                br = new BufferedReader(new FileReader(directory));
                bw = new BufferedWriter(new FileWriter(directoryTMP, false));

                String linea, linea2 = "";

                while((linea=br.readLine())!=null){

                    String[] campos = linea.split(";");
                    if(!foodname.equalsIgnoreCase(campos[0])){
                        linea2 += campos[0] + ";";
                        linea2 += campos[1] + ";";
                        linea2 += campos[2] + "\n";

                        bw.write(linea2);
                    }

                }

                bw.close();
                br.close();

            }catch(IOException e){

            }

            finally {
                if(new File(directory).delete()){
                    new File(directoryTMP).renameTo(fich);
                }
            }

    }


    /**
     * This method read the file fich and return a ArrayList whit the food
     * @return listFood save all food
     */
    public static ArrayList<Food> readFile(){

        Food f;
        ArrayList<Food> listFood = new ArrayList<>();
        BufferedReader br;

        try {

            br = new BufferedReader(new FileReader(directory));
            String linea;

            while((linea=br.readLine())!=null){
                f = new Food();
                String[] campos = linea.split(";");
                f.setName(campos[0]);
                f.setDateBuy(campos[1]);
                f.setDateExpire(campos[2]);

                listFood.add(f);
            }


            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){

        }

        return listFood;

    }

    //This method write Food in the file fich
    public static String writeFile(Food f){

        String linea = "";
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(directory, true));
            linea += f.getName()+";";
            linea += f.getDateBuy()+";";
            linea += f.getDateExpire()+"\n";

            br.write(linea);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return linea;
    }

    //If the File fich is clear it return a true boolean else it return false
    public static Boolean fileClear(){

        Boolean flag = true;
        try {

            BufferedReader br = new BufferedReader(new FileReader(directory));
            if(br.readLine()==null){
                flag = true;

            }else{
                flag = false;

            }

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e){

        }
        return flag;

    }



}
