package com.example.inass;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class FragmentSearch extends Fragment {

    private EditText searchEditText;
    private Button button;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private String user_id;
    private SharedPreferences sPref;

    public FragmentSearch(){
        //Конструктор
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        //Назначим хмл этому фрагменту
        View rootView = inflater.inflate(R.layout.fragment_search,container,false);
        initFirebase();




        searchEditText =(EditText) rootView.findViewById(R.id.searchIdEditText);
        button = (Button)rootView.findViewById(R.id.buttonSearch) ;
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                //Поиск по базе данных
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Получаем все комнаты и проверяем,существует ли введенная
                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            if(data.child(searchEditText.getText().toString()).exists()){
                                sPref = getActivity().getPreferences(MODE_PRIVATE);
                                user_id = sPref.getString("Id","Что то пошло не так ");
                                mDatabaseReference.child("user").child(user_id).child("NumberRoom").setValue(searchEditText.getText().toString());
                                mDatabaseReference.child("rooms").child(searchEditText.getText().toString()).child(user_id).child("NumberRoom").setValue(searchEditText.getText().toString());
                                mDatabaseReference.child("rooms").child(searchEditText.getText().toString()).child("All").setValue("Yes");



                                //Создаем второго юзера
                                //Переходим в комнату
                                ((MainActivity)getActivity()).setFragmentWaitPartner();
                            }else{
                                Toast.makeText(getContext(), "Entered room not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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


