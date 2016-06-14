package com.hyena.samples.latex.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.himamis.retex.editor.android.FormulaEditor;
import com.himamis.retex.renderer.android.FactoryProviderAndroid;
import com.himamis.retex.renderer.android.LaTeXView;
import com.himamis.retex.renderer.share.platform.FactoryProvider;
import com.hyena.fillin.LatexFillInView;

/**
 * Created by yangzc on 16/6/12.
 */
public class SampleLatexFragment1 extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FactoryProvider.INSTANCE = new FactoryProviderAndroid(getActivity().getAssets());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LatexFillInView view = new LatexFillInView(getActivity());
        view.setLatexText(ExampleFormula.mExample4);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
