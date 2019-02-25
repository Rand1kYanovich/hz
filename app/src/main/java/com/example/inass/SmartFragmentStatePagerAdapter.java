package com.example.inass;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

public abstract class SmartFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public SmartFragmentStatePagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    // Регистрация фрагмента при создании экземпляра
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        Fragment fragment = (Fragment)super.instantiateItem(container,position);
        registeredFragments.put(position,fragment);
        return fragment;
    }

    // Отмена регистрации, когда элемент неактивен
    @Override
    public void destroyItem (ViewGroup container,int position, Object object){
        registeredFragments.remove(position);
        super.destroyItem(container,position,object);
    }
    // Возвращает позицию фрагмента (если создан)
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
