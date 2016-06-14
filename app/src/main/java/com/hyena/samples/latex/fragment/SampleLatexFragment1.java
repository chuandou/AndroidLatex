package com.hyena.samples.latex.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.himamis.retex.editor.android.FormulaEditor;
import com.himamis.retex.renderer.android.FactoryProviderAndroid;
import com.himamis.retex.renderer.android.LaTeXView;
import com.himamis.retex.renderer.share.MacroInfo;
import com.himamis.retex.renderer.share.platform.FactoryProvider;
import com.hyena.fillin.LatexFillInView;
import com.hyena.samples.latex.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangzc on 16/6/12.
 */
public class SampleLatexFragment1 extends Fragment {

    private LatexFillInView mLatexView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FactoryProvider.INSTANCE = new FactoryProviderAndroid(getActivity().getAssets());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_latex, null);
        this.mLatexView = (LatexFillInView) view.findViewById(R.id.latexview);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.latex_keyboard_1).setOnClickListener(mClickListener);
        view.findViewById(R.id.latex_keyboard_2).setOnClickListener(mClickListener);
        view.findViewById(R.id.latex_keyboard_3).setOnClickListener(mClickListener);
        view.findViewById(R.id.latex_keyboard_4).setOnClickListener(mClickListener);
        view.findViewById(R.id.latex_keyboard_5).setOnClickListener(mClickListener);
        view.findViewById(R.id.latex_keyboard_6).setOnClickListener(mClickListener);
        view.findViewById(R.id.latex_keyboard_7).setOnClickListener(mClickListener);
        view.findViewById(R.id.latex_keyboard_8).setOnClickListener(mClickListener);
        view.findViewById(R.id.latex_keyboard_9).setOnClickListener(mClickListener);
        view.findViewById(R.id.latex_keyboard_star).setOnClickListener(mClickListener);
        view.findViewById(R.id.latex_keyboard_del).setOnClickListener(mClickListener);
        view.findViewById(R.id.latex_keyboard_w).setOnClickListener(mClickListener);

        String question = "<p><span class=\"mathquill-embedded-latex\" style=\"width: " +
                "26px; height: 42px;\">\\frac{7}{5}</span><span style=\" white-space: normal;" +
                "\">×25=(<img src=\"/images/edu_fillin.png\" class=\"img_fillin\"/>)</span></p>";
        String result = trimString(replaceFillIn(question));
        Log.v("yangzc", result);

        List<Integer[]> pointArray = parseFormula(result);
        for (int i = 0; i < pointArray.size(); i++) {
            System.out.println("formula:" + result.substring(pointArray.get(i)[0],
                    pointArray.get(i)[1]));
        }
        mLatexView.setLatexText("\\fillIn{0}{123}");
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v != null && v instanceof TextView) {
                TextView textView = (TextView) v;
                String text = textView.getText().toString();
                mLatexView.onKeyClick("删除".equals(text), text);
            }
        }
    };

    private String replaceFillIn(String html) {
        Pattern pattern = Pattern.compile("<img.*?edu_fillin.*?>|<div[^>]*?edu_fillin.*?>(</div>)");
        Matcher matcher = pattern.matcher(html);
        String result = html;
        while (matcher.find()) {
            String group = matcher.group();
            result = result.replace(group, "\\fillIn{}{}");
        }

        return result;
    }

    private String trimString(String str) {
        Pattern pattern = Pattern.compile("<[\\s\\S]*?>");
        Matcher matcher = pattern.matcher(str);
        String result = str;
        while (matcher.find()) {
            String group = matcher.group();
            if (!"</p>".equals(group)) {
                result = result.replace(group, "");
            }
        }
        return result;
    }

    private List<Integer[]> parseFormula(String data){
        int startIndex = -1;
        List<Integer[]> result = new ArrayList<Integer[]>();
        for (int i = 0; i < data.length(); i++) {
            char ch = data.charAt(i);
            if (ch == '\\') {
                startIndex = i;
            }
            if (ch == '{' && startIndex >= 0) {
                String command = data.substring(startIndex + 1, i);
                MacroInfo macroInfo = MacroInfo.Commands.get(command);
                if (macroInfo != null && macroInfo.nbArgs > 0) {
                    //公式
                    for (int j = 0; j < macroInfo.nbArgs; j++) {
                        int endIndex = getEndIndex(data, i);
                        if (endIndex > 0) {
                            i = endIndex;
                        }
                        i ++;
                    }
                    i--;
                    result.add(new Integer[]{startIndex, i + 1});
                }
            }
        }
        return result;
    }

    private int getEndIndex(String data, int startIndex){
        int endIndex = startIndex;
        char first = data.charAt(startIndex);
        if (first != '{') {
            return -1;
        }

        Stack<Character> stack = new Stack<Character>();
        while (endIndex < data.length()) {
            char c = data.charAt(endIndex);
            if (c == '}') {
                while (!stack.isEmpty()) {
                    c = stack.pop();
                    if (c == '{') {
                        break;
                    }
                }
                if (stack.isEmpty()) {
                    return endIndex;
                }
            } else {
                stack.push(c);
            }
            endIndex ++;
        }
        return endIndex;
    }
}
