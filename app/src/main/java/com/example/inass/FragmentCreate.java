package com.example.inass;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;


public class FragmentCreate extends Fragment {
    private Button buttonCreate;
    private Button buttonSearch;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private SharedPreferences sPref;
    private String user_id;

    private String numberRoom;
    private int numberRoomInt;




    public FragmentCreate(){
        //Constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create,container,false);






        buttonCreate = (Button) rootView.findViewById(R.id.buttonCreate);
        buttonSearch = (Button) rootView.findViewById(R.id.buttonSearch);



        sPref = getActivity().getPreferences(MODE_PRIVATE);
        Toast.makeText(getContext(),sPref.getString("Id","FUCK"),Toast.LENGTH_SHORT).show();


        initFirebase();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setFragmentSearch();
            }
        });


        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Функция для получения последней созданной комнаты
                Query lastQuery = mDatabaseReference.child("rooms").limitToLast(1);
                lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Search last room and create room +1;

                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            numberRoom = data.child("NumberRoom").getValue().toString();
                        }
                        numberRoomInt = Integer.parseInt(numberRoom)+1;

                        user_id = sPref.getString("Id","fusk");

                        mDatabaseReference.child("rooms").child(numberRoomInt+"").child(user_id).child("NumberRoom").setValue(numberRoomInt+"");
                        mDatabaseReference.child("rooms").child(numberRoomInt+"").child("NumberRoom").setValue(numberRoomInt+"");
                        mDatabaseReference.child("user").child(user_id).child("NumberRoom").setValue(numberRoomInt+"");
                        mDatabaseReference.child("rooms").child(numberRoomInt+"").child("Ready").setValue("1");
                        //mDatabaseReference.child("rooms").child(numberRoomInt+"").child("All").setValue("1");


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });




                ((MainActivity)getActivity()).setFragmentWaitPartner();

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
