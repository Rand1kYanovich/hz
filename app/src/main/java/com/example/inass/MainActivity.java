package com.example.inass;


import android.accounts.AccountManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import android.provider.Settings.Secure;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    private FragmentAdapter fragmentAdapter;


    private NoSwipePager viewpager;
    private SharedPreferences sPref;
    private UserNumber userNumber;
    private String user_id;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       setupViewPager();
       initFirebase();

        sPref = getPreferences(MODE_PRIVATE);
        if(sPref.getString("Id","Fuck").length()<30) {
            userNumber = new UserNumber();
            user_id = userNumber.getID();

            SharedPreferences.Editor ed = sPref.edit();
            ed.putString("Id", user_id);
            ed.commit();

            mDatabaseReference.child("user").child(user_id).child("id").setValue(user_id);
        }

       Toast.makeText(getApplicationContext(),user_id,Toast.LENGTH_SHORT).show();



       }














    private void setupViewPager(){
        viewpager = (NoSwipePager) findViewById(R.id.viewpager);
        viewpager.setPagingEnabled(false);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());


        fragmentAdapter.addFragments(createFragmentCreate());
        fragmentAdapter.addFragments(createFragmentSearch());
        fragmentAdapter.addFragments(createFragmentRoom());
        fragmentAdapter.addFragments(createFragmentWaitPartner());
        fragmentAdapter.addFragments(createFragmentFinal());
        fragmentAdapter.addFragments(createFragmentWaitFinishPartner());


        viewpager.setAdapter(fragmentAdapter);

    }

    @NonNull
    private FragmentSearch createFragmentSearch(){
        FragmentSearch fragmentSearch = new FragmentSearch();
        return fragmentSearch;
    }
    private FragmentCreate createFragmentCreate(){
        FragmentCreate fragmentCreate = new FragmentCreate();
        return fragmentCreate;
    }
    private FragmentRoom createFragmentRoom(){
        FragmentRoom fragmentRoom = new FragmentRoom();
        return fragmentRoom;
    }
    private FragmentWaitPartner createFragmentWaitPartner(){
        FragmentWaitPartner fragmentWaitPartner = new FragmentWaitPartner();
        return fragmentWaitPartner;
    }

    private FragmentWaitFinishPartner createFragmentWaitFinishPartner(){
        FragmentWaitFinishPartner fragmentWaitFinishPartner = new FragmentWaitFinishPartner();
        return fragmentWaitFinishPartner;
    }
    private FragmentFinal createFragmentFinal(){
        FragmentFinal fragmentFinal = new FragmentFinal();
        return fragmentFinal;
    }

    public void setFragmentFinal(){viewpager.setCurrentItem(4);}
    public void setFragmentWaitPartnerFinish(){viewpager.setCurrentItem(5);}
    public void setFragmentWaitPartner (){
        viewpager.setCurrentItem(3);
    }
    public void setFragmentRoom (){ viewpager.setCurrentItem(2); }
    public void setFragmentSearch(){viewpager.setCurrentItem(1);}





    public void initFirebase(){

        FirebaseApp.initializeApp(getApplicationContext());

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mDatabaseReference = mFirebaseDatabase.getReference();

    }











}
