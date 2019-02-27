package com.example.inass;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;


public class FragmentRoom extends Fragment {


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private SharedPreferences sPref;
    private String sPrefId;

    private String numberRoom;

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    private String dataReady;
    private int dataReadyQuality;

    private int qualityCards;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){




        View rootView = inflater.inflate(R.layout.fragment_room,container,false);
        initFirebase();

        qualityCards = Utils.loadProfiles(this.getContext()).size();

        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        sPrefId = sPref.getString("Id","Fuck");

        mSwipeView = (SwipePlaceHolderView)rootView.findViewById(R.id.swipeView);
        mContext = getContext();


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("rooms").child(numberRoom+"").child("Ready").exists() && dataSnapshot.child("rooms").child(numberRoom+"").child("Ready").getValue()!=null) {
                    dataReady = dataSnapshot.child("rooms").child(numberRoom).child("Ready").getValue().toString();
                    mDatabaseReference.child("rooms").child(numberRoom).child("All").removeValue();
                }
                if(dataReady == null) dataReadyQuality = 0;
                else
                dataReadyQuality = Integer.parseInt(dataReady);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.card_swipe_accept)
                        .setSwipeOutMsgLayoutId(R.layout.card_swipe_reject));


        for(final Item item : Utils.loadProfiles(this.getContext())){
            mSwipeView.addView(new PoseCard(mContext, item, mSwipeView, new OnSwipeListener() {
                @Override
                public void onSwipeListener(String itemName,int quality) {
                    mDatabaseReference.child("rooms").child(numberRoom).child(sPrefId).child(itemName).setValue(itemName);
                    qualityCards = qualityCards-1;
                    if(qualityCards == 0){



                        mDatabaseReference.child("rooms").child(numberRoom).child("All").removeValue();
                        mDatabaseReference.child("rooms").child(numberRoom).child("Ready").setValue(dataReadyQuality+1);
                        qualityCards = 1;
                        ((MainActivity)getActivity()).setFragmentWaitPartnerFinish();

                    }



                }

            }, new OnSwipeListenerOut() {


                @Override
                public void onSwipeListenerOut(int qualityCard) {
                    qualityCards = qualityCards-1;
                    if(qualityCards == 0){
                        mDatabaseReference.child("rooms").child(numberRoom).child("Ready").setValue(dataReadyQuality+1);
                        //mDatabaseReference.child("rooms").child(numberRoom).child("All").removeValue();
                        qualityCards = 1;
                        ((MainActivity)getActivity()).setFragmentWaitPartnerFinish();

                    }
                }
            }));
        }





        mDatabaseReference.child("user").child(sPrefId).child("NumberRoom").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numberRoom = dataSnapshot.getValue().toString();



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
