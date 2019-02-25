package com.example.inass;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

@Layout(R.layout.room_card_view)

public class PoseCard {




    @View(R.id.name_pose)
    private TextView nameAgeTxt;




    private Item mItem;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;
    private OnSwipeListener mOnSwipeListener;
    private OnSwipeListenerOut mOnSwipeListenerOut;

    public PoseCard(Context context, Item item, SwipePlaceHolderView swipeView,OnSwipeListener onSwipeListener,OnSwipeListenerOut onSwipeListenerOut) {
        mContext = context;
        mItem = item;
        mSwipeView = swipeView;
        mOnSwipeListener = onSwipeListener;
        mOnSwipeListenerOut = onSwipeListenerOut;

    }

    @Resolve
    private void onResolved(){
        nameAgeTxt.setText(mItem.getName());

    }

    @SwipeOut
    private void onSwipedOut(){
      mOnSwipeListenerOut.onSwipeListenerOut(mItem.getSize());
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        mOnSwipeListener.onSwipeListener(mItem.getName(),mItem.getSize());


    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }





}
