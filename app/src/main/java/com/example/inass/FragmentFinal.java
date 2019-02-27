package com.example.inass;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentFinal extends Fragment
 {

     private FirebaseDatabase mFirebaseDatabase;
     private DatabaseReference mDatabaseReference;

     private SharedPreferences sPref;
     private String sPrefId;

     private  String nameRoom;
     private int numberRoom;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.rrfragment_final,container,false);
        initFirebase();
        

        return rootView;


    }



     public void initFirebase(){

         FirebaseApp.initializeApp(getContext());

         mFirebaseDatabase = FirebaseDatabase.getInstance();

         mDatabaseReference = mFirebaseDatabase.getReference();

     }


    }
