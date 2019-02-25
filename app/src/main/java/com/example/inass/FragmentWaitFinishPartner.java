package com.example.inass;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentWaitFinishPartner extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private String nameRoom;
    private SharedPreferences sPref;
    private String sPrefId;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wait_finish_partner,container,false);

        initFirebase();

        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        sPrefId = sPref.getString("Id","Fuck");

        //Get number Room

        mDatabaseReference.child("user").child(sPrefId).child("NumberRoom").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameRoom = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        });

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("rooms").child(nameRoom).child("Ready").exists()) {
                    if (dataSnapshot.child("rooms").child(nameRoom).child("Ready").getValue().toString() == "2") {
                        // ((MainActivity)getActivity()).setFragmentFinal();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








        return rootView;
    }







    //Функция инициализации базы данных
    public void initFirebase(){

        //инициализируем наше приложение для Firebase согласно параметрам в google-services.json
        // (google-services.json - файл, с настройками для firebase, кот. мы получили во время регистрации)
        FirebaseApp.initializeApp(getContext());

        //получаем точку входа для базы данных
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        //получаем ссылку для работы с базой данных
        mDatabaseReference = mFirebaseDatabase.getReference();

    }
}
