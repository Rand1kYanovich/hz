package com.example.inass;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends SmartFragmentStatePagerAdapter {
    private final List<Fragment> fragments = new ArrayList<>();

    public FragmentAdapter (FragmentManager fragmentManager) {super(fragmentManager);}

    public void addFragments(Fragment fragment){ fragments.add(fragment); }

    @Override
    public Fragment getItem(int posiition) {return fragments.get(posiition);}

    @Override
    public int getCount() { return fragments.size(); }



}
