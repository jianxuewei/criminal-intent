package com.xxk.criminalintent;


import android.support.v4.app.Fragment;



public class Activity_CrimeList extends Activity_SingleFragment {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }


}
