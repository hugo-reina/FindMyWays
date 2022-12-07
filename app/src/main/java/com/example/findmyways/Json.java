package com.example.findmyways;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

    public class Json {

        private String fileName = "data.json";

        public Json() {
        }




        public String getData(Context context) {
            try {
                File f = new File(context.getFilesDir().getPath() + "/" + fileName);
                FileInputStream is = new FileInputStream(f);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                return new String(buffer);
            } catch (IOException e) {
                Log.e("TAG", "Error in Reading: " + e.getLocalizedMessage());
                return null;
            }
        }
    }
