package com.example.inass;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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


     @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.rrfragment_final,container,false);
        initFirebase();

        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        sPrefId = sPref.getString("Id","Fuck");

        //mDatabaseReference.child("rooms").child(nameRoom).child("Ready").setValue("5");
        if(((MainActivity)getActivity()).getViewpager() == 4 || ((MainActivity)getActivity()).getViewpager() == 5) {
            Toast.makeText(getContext(),((MainActivity)getActivity()).getViewpager()+"",Toast.LENGTH_SHORT).show();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        mDatabaseReference.child("user").child(sPrefId).child("NumberRoom").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameRoom = dataSnapshot.getValue().toString();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootView;


    }



     public void initFirebase(){

         FirebaseApp.initializeApp(getContext());

         mFirebaseDatabase = FirebaseDatabase.getInstance();

         mDatabaseReference = mFirebaseDatabase.getReference();

     }


    }
