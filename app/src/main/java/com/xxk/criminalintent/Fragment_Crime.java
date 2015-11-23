package com.xxk.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.xxk.criminalintent.models.Crime;
import com.xxk.criminalintent.models.CrimeLab;

import org.json.JSONException;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;


public  class Fragment_Crime extends Fragment {

    public static final String EXTRA_CRIME_ID="com.xxk.criminalintent.crime_id";
    private static final String DATEPICKEFRAGMENT_TAG = "DATE";
    public static final int DATE_REQUEST_CODE = 0;


    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    private OnFragmentInteractionListener mListener;


    public static Fragment_Crime newInstance(UUID id) {
        Fragment_Crime fragment = new Fragment_Crime();
        Bundle args=new Bundle();
        args.putSerializable(EXTRA_CRIME_ID,id);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_Crime() {

    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID id= (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
        getActivity().setTitle(R.string.crime_fragment_title);
        Log.d("CriminalIntent", "fragment_crime onCreate");
        try {
            mCrime= CrimeLab.get(getActivity()).getCrime(id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField= (EditText) v.findViewById(R.id.crime_title);
        mDateButton= (Button) v.findViewById(R.id.button_crime_date);
        mSolvedCheckBox= (CheckBox) v.findViewById(R.id.checkbox_crime_solved);

        mTitleField.setText(mCrime.getTitle());
        mDateButton.setText(mCrime.getDate().toString());
//        mDateButton.setEnabled(true);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
//                DatePickerFragment dialog=new DatePickerFragment();
                DatePickerFragment dialog=DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(Fragment_Crime.this,DATE_REQUEST_CODE);
                dialog.show(fm,DATEPICKEFRAGMENT_TAG);
            }
        });
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= Activity.RESULT_OK) return;
        if(requestCode==DATE_REQUEST_CODE){
            Date date= (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            mDateButton.setText(mCrime.getDate().toString());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            CrimeLab.get(getActivity()).checkCrimes();
            CrimeLab.get(getActivity()).saveCrimes();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("CriminalIntent", "fragment_crime onPause");
        //CrimeLab.get(getActivity()).saveCrimes();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("CriminalIntent", "fragment_crime onDetach");
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("CriminalIntent", "fragment_crime onStart");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d("CriminalIntent", "fragment_crime onStop");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
