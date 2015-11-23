package com.xxk.criminalintent;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;

import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.xxk.criminalintent.models.Crime;
import com.xxk.criminalintent.models.CrimeLab;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class CrimeListFragment extends Fragment {

    public static final int REQUEST_CRIME=1;
    private static String TAG="CrimeListFragment";

    private ListView mListView;
    private Button mCreateCrimeButton;
    private View mEmptyView;
    private CrimeAdapter mAdapter;
    private ArrayList<Crime> mCrimes;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.d(TAG, "onCreate");

        getActivity().setTitle(R.string.crimes_title);

//        mCrimes= CrimeLab.get(getActivity()).getCrimes();
////        ArrayAdapter<Crime> adapter=new ArrayAdapter<Crime>(getActivity(),android.R.layout.simple_list_item_1,mCrimes);
//        CrimeAdapter adapter=new CrimeAdapter(mCrimes);
//        setListAdapter(adapter);
    }


    private class CrimeAdapter extends ArrayAdapter<Crime>{

        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=getActivity().getLayoutInflater().inflate(R.layout.list_item_crime,null);
            }
            Crime c=getItem(position);
            TextView titleTextview= (TextView) convertView.findViewById(R.id.crime_list_item_title_textview);
            TextView dateTextview= (TextView) convertView.findViewById(R.id.crime_list_item_date_textview);
            CheckBox solvedCheckbox= (CheckBox) convertView.findViewById(R.id.crime_list_item_solved_checkbox);

            titleTextview.setText(c.getTitle());
            dateTextview.setText(c.getDate().toString());
            solvedCheckbox.setChecked(c.isSolved());

            return convertView;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_crime_list,container,false);
        mListView= (ListView) v.findViewById(R.id.crime_list_listview);
        mEmptyView=v.findViewById(R.id.emptyView);
        mCreateCrimeButton= (Button) v.findViewById(R.id.button_create_crime);
        try {
            mCrimes= CrimeLab.get(getActivity()).getCrimes();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter=new CrimeAdapter(mCrimes);

        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mEmptyView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Crime c = mCrimes.get(position);
                startCrime(c);
            }
        });
        mCreateCrimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewCrime();
            }


        });
        return v;
    }

    private void createNewCrime() {
        Crime c=new Crime();
        mCrimes.add(c);
        startCrime(c);

    }

    private void startCrime(Crime c) {
        Intent i = new Intent(getActivity(), Activity_CrimePage.class);
        i.putExtra(Fragment_Crime.EXTRA_CRIME_ID, c.getId());
        startActivityForResult(i, REQUEST_CRIME);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_crime_list, menu);

    }

    @Override
    public void onResume() {
        super.onResume();
        //mAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_add_crime:
                createNewCrime();
                return true;
            default:
                return true;
        }

    }
}
