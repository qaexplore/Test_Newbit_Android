package com.spark.otcbitrade.base;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/9.
 */

public abstract class BaseTransFragmentActivity extends BaseActivity {
    protected List<BaseTransFragment> fragments = new ArrayList<>();
    protected BaseFragment currentFragment;

    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) initFragments();
        super.onCreate(savedInstanceState);
    }

    protected abstract void recoverFragment();

    protected abstract void initFragments();

    public abstract int getContainerId();

    public void addFragment(BaseTransFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(getContainerId(), fragment, fragment.getmTag()).commit();
    }

    public void removeFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        fragments.remove(fragment);
    }

    protected void showFragment(BaseTransFragment fragment) {
        if (currentFragment == fragment) return;
        currentFragment = fragment;
        hideFragments();
        if (!fragment.isAdded()) addFragment(fragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.show(fragment).commit();
    }

    protected void hideFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            /*if (!fragments.get(i).isHidden() && currentFragment != fragments.get(i)) {//未隐藏并且不是currentFragment
            }*/
            transaction.hide(fragments.get(i));
        }
        transaction.commit();
    }

    protected void hideFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
    }
}
