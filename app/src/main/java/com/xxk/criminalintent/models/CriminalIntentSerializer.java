package com.xxk.criminalintent.models;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by xxk on 15/11/19.
 */
public class CriminalIntentSerializer {
    private Context mContext;
    private String mFileName;

    public CriminalIntentSerializer(Context context, String fileName) {
        mContext = context;
        mFileName = fileName;
    }
    public ArrayList<Crime> loadCrimes() throws JSONException, IOException {
        ArrayList<Crime> crimes=new ArrayList<>();

        BufferedReader reader=null;
        try {
            // open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFileName);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                // line breaks are omitted and irrelevant
                jsonString.append(line);
            }

            // parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            // build the array of crimes from JSONObjects
            for (int i = 0; i < array.length(); i++) {

                Crime c=new Crime(array.getJSONObject(i));

                crimes.add(new Crime(array.getJSONObject(i)));
            }


        } catch (FileNotFoundException e) {
            // we will ignore this one, since it happens when we start fresh
        } finally {
            if (reader != null)
                reader.close();
        }

        return crimes;
    }
    public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException {
        JSONArray array=new JSONArray();
        for (Crime c:crimes) {
            JSONObject o=c.toJson();
            array.put(o);
        }
        Writer writer=null;
        try {
            OutputStream out=mContext.openFileOutput(mFileName,Context.MODE_PRIVATE);
            writer=new OutputStreamWriter(out);
            writer.write(array.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(writer!=null){
                writer.close();
            }
        }
    }
}
