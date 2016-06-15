package com.hyena.samples.latex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.hyena.samples.latex.fragment.SampleLatexFragment1;

/**
 * Created by yangzc on 16/6/12.
 */
public class LatexActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        showFragment(Fragment.instantiate(this, SampleLatexFragment1.class.getName()));
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.commitAllowingStateLoss();
    }
}
