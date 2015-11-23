package com.xxk.criminalintent.models;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by xxk on 15/11/16.
 */
public class CrimeLab {
    private static final String FILE_NAME="crimes.json";
    private static CrimeLab sCrimeLab;
    private Context mContext;
    private ArrayList<Crime> mCrimes;
    private CriminalIntentSerializer mSerializer;

    private CrimeLab(Context appContext)  {
        mContext=appContext;
        mCrimes=new ArrayList<>();
        mSerializer = new CriminalIntentSerializer(mContext, FILE_NAME);
        Log.d("CriminalIntent","CrimeLab:load crimes from file:"+mCrimes.size());

        try {
            mCrimes = mSerializer.loadCrimes();
            Log.d("CriminalIntent","CrimeLab:load crimes from file:"+mCrimes.size());
        } catch (Exception e) {
            mCrimes = new ArrayList<Crime>();

        }

//            for (int i = 0; i < 3; i++) {
//                Crime c=new Crime();
//                c.setTitle("Crime #"+i);
//                c.setSolved(i%2==0);
//                mCrimes.add(c);
//            }


    }

    public static CrimeLab get(Context context) throws IOException, JSONException {
        if(sCrimeLab==null){
            sCrimeLab=new CrimeLab(context.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
//        try {
//            mCrimes = mSerializer.loadCrimes();
//            Log.d("CriminalIntent","CrimeLab:load crimes from file:"+mCrimes.size());
//        } catch (Exception e) {
//            mCrimes = new ArrayList<Crime>();
//
//        }

        return mCrimes;
    }
    public Crime getCrime(UUID id){
        for (Crime c :mCrimes) {
            if(c.getId().equals(id))
                return c;
        }
        return null;
    }
    public boolean saveCrimes(){
        try {
            mSerializer.saveCrimes(mCrimes);

            return true;
        } catch (Exception e) {

            return false;
        }
    }
    public  void checkCrimes(){


        for (int i = mCrimes.size()-1; i >=0 ; i--) {
            Crime c=mCrimes.get(i);
            if(c!=null){
//                Log.i("CriminalIntent",c.getId().toString());
                if(c.getTitle()==null){
                   mCrimes.remove(i);
                }
            }
        }
        Log.i("CriminalIntent",mCrimes.size()+"");

    }
}
