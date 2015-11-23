package com.xxk.criminalintent;


import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.xxk.criminalintent.models.Crime;
import com.xxk.criminalintent.models.CrimeLab;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class Activity_CrimePage extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager=new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);
        FragmentManager fm=getSupportFragmentManager();

        try {
            mCrimes=CrimeLab.get(Activity_CrimePage.this).getCrimes();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime c=mCrimes.get(position);
                return Fragment_Crime.newInstance(c.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
        UUID crimeId= (UUID) getIntent().getSerializableExtra(Fragment_Crime.EXTRA_CRIME_ID);
        for (int i = 0; i < mCrimes.size(); i++) {
            if(mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
