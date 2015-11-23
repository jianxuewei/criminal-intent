package com.xxk.criminalintent;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment {


    public static final String EXTRA_DATE = "com.xxk.datepickerfragment.crime_date";

    private Date mDate;

    public DatePickerFragment() {
        // Required empty public constructor
    }
    public static DatePickerFragment newInstance(Date date){
        DatePickerFragment datePickerFragment=new DatePickerFragment();
        Bundle args=new Bundle();
        args.putSerializable(EXTRA_DATE,date);
        datePickerFragment.setArguments(args);
        return datePickerFragment;
    }

    private void sendResult(int resultCode){
        if(getTargetFragment()==null) return;
        Intent data=new Intent();
        data.putExtra(EXTRA_DATE,mDate);
        getTargetFragment().onActivityResult(Fragment_Crime.DATE_REQUEST_CODE,resultCode,data);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v=getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
        mDate= (Date) getArguments().getSerializable(EXTRA_DATE);
        final Calendar calendar=Calendar.getInstance();
        calendar.setTime(mDate);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        DatePicker datePicker= (DatePicker) v.findViewById(R.id.crime_date_picker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                calendar.set(year,monthOfYear,dayOfMonth);
                mDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setView(v)

                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }
}
