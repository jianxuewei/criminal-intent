package com.xxk.criminalintent.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by xxk on 15/11/15.
 */
public class Crime {

    private static final String JSON_ID="id";
    private static final String JSON_TITLE="title";
    private static final String JSON_DATE="date";
    private static final String JSON_SOLVED="solved";

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime(){
        mId=UUID.randomUUID();
        mDate=new Date();
    }

//    public Crime(JSONObject jsonObject) throws JSONException {
//        mId=UUID.fromString(jsonObject.getString(JSON_ID));
//        if(jsonObject.has(JSON_TITLE)){
//            mTitle=jsonObject.getString(JSON_TITLE);
//        }
//        mSolved=jsonObject.getBoolean(JSON_SOLVED);
//        mDate=new Date(jsonObject.getLong(JSON_DATE));
//    }
    public Crime(JSONObject json) throws JSONException {

        mId = UUID.fromString(json.getString(JSON_ID));

        mTitle = json.getString(JSON_TITLE);

        mSolved = json.getBoolean(JSON_SOLVED);

        mDate = new Date(json.getLong(JSON_DATE));

    }


    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject object=new JSONObject();
        object.put(JSON_ID,mId.toString());
        object.put(JSON_DATE,mDate.getTime());
        object.put(JSON_SOLVED,mSolved);
        object.put(JSON_TITLE,mTitle);
        return object;
    }




    @Override
    public String toString() {
        return mTitle;
    }
}
