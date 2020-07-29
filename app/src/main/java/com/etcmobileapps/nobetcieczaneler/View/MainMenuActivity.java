package com.etcmobileapps.nobetcieczaneler.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.etcmobileapps.nobetcieczaneler.Fragments.FragmentCountry;
import com.etcmobileapps.nobetcieczaneler.Fragments.FragmentLocal;
import com.etcmobileapps.nobetcieczaneler.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class MainMenuActivity extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener {
    FragmentCountry fragmentCountry;
    FragmentLocal fragmentLocal;
    Fragment fragment;
    FrameLayout frameLayout2;
    private BottomNavigationView mBtmView;
    private int mMenuId;
    public void changeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout3,fragment);
        fragmentTransaction.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mBtmView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBtmView.setOnNavigationItemSelectedListener(this);
        mBtmView.getMenu().findItem(R.id.country).setChecked(true);
        frameLayout2 = findViewById(R.id.framelayout3);
       // mBtmView.setSelectedItemId(R.id.local);
        changeFragment(new FragmentCountry());





    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // uncheck the other items.
        mMenuId = item.getItemId();
        for (int i = 0; i < mBtmView.getMenu().size(); i++) {
            MenuItem menuItem = mBtmView.getMenu().getItem(i);
            boolean isChecked = menuItem.getItemId() == item.getItemId();
            menuItem.setChecked(isChecked);
        }

        switch (item.getItemId()) {
            case R.id.country:
                changeFragment(new FragmentCountry());
            break;
            case R.id.local:
                changeFragment(new FragmentLocal());
                break;


        }
        return true;
    }






}
