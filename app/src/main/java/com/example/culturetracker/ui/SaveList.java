package com.example.culturetracker.ui;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveList
{
    public void saveBookList(Activity activity, String filename, JSONObject jsonObject) throws IOException {
       /* String jsonString = jsonObject.toString(); //Convert the JSONObject to string before saving it to file. Allow the app to retrieve it easily afterwards without changing the structure of the object.

        File file = new File(activity.getFilesDir(),filename);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(jsonString);
        bufferedWriter.close();*/
        System.out.println(jsonObject);
    }

    public JSONObject getJson(Activity activity, String objectName) throws IOException, JSONException {

        File file = new File(activity.getFilesDir(),objectName);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        // This responce will have Json Format String
        String responce = stringBuilder.toString();

        JSONObject jsonObject  = new JSONObject(responce); //Create a JSONObject with the content of the file before returning it
        //Java Object

        return jsonObject;
    }
}
