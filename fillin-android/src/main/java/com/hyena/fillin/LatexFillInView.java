package com.hyena.fillin;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.himamis.retex.renderer.android.LaTeXView;
import com.himamis.retex.renderer.share.Box;
import com.hyena.fillin.core.FillInBox;
import com.hyena.fillin.parser.FocusChangeListener;
import com.hyena.fillin.parser.LaTeXTextHelper;
import com.hyena.fillin.parser.LaTexTextChangeListener;
import com.hyena.fillin.utils.PluginInstaller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzc on 16/6/13.
 */
public class LatexFillInView extends LaTeXView {

    private List<FillInBox> mFillInBox = new ArrayList<FillInBox>();

    private LaTeXTextHelper mLatexTextHelper = null;

    public LatexFillInView(Context context) {
        super(context);
        init();
    }

    public LatexFillInView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LatexFillInView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        PluginInstaller.install(getContext());
        mLatexTextHelper = new LaTeXTextHelper(mFocusChangeListener, mTextChangeListener);
    }

    @Override
    public void setLatexText(String latexText) {
        if (mLatexTextHelper != null) {
            mLatexTextHelper.setLatexText(latexText);
        }
    }

    private void setLatexTextImpl(String latexText){
        super.setLatexText(latexText);
        refreshFillInBox();
        updateFocus();
    }

    public void onKeyClick(boolean isDel, String text) {
        if (mLatexTextHelper != null) {
            if (isDel) {
                int index = mLatexTextHelper.getSelectIndex();
                String data = mLatexTextHelper.getTextAtIndex(index);
                if (TextUtils.isEmpty(data))
                    return;

                mLatexTextHelper.setTextAtIndex(index, data.substring(0, data.length() - 1));
            } else {
                mLatexTextHelper.appendText(text);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mTexIcon == null && mTexIcon.getSize() != 0)
            return false;

        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                Log.v("yangzc", "width:" + mTexIcon.getTrueIconWidth() + ", height:" + mTexIcon.getTrueIconHeight());
                Log.v("yangzc", "x: " + x + ", y: " + y);
                FillInBox fillInBox = getFillInBox(x, y);
                if (fillInBox != null) {
                    mLatexTextHelper.setSelectIndex(fillInBox.getIndex());
                }
                postInvalidate();
                break;
            }
        }
        return true;
    }

    private void updateFocus() {
        int index = mLatexTextHelper.getSelectIndex();
        if (mFillInBox != null && !mFillInBox.isEmpty()) {
            for (int i = 0; i < mFillInBox.size(); i++) {
                FillInBox fillInBox = mFillInBox.get(i);
                if (fillInBox.getIndex() == index) {
                    fillInBox.setFocus(true);
                } else {
                    fillInBox.setFocus(false);
                }
            }
        }
    }

    private void refreshFillInBox() {
        mFillInBox.clear();
        if (mTexIcon == null && mTexIcon.getSize() == 0) {
            return;
        }

        addFillInBox(mTexIcon.getBox());
    }

    private void addFillInBox(Box box) {
        List<Box> children = box.getChildren();
        if (children != null && !children.isEmpty()) {
            for (int i = 0; i < children.size(); i++) {
                Box subBox = children.get(i);
                if (subBox instanceof FillInBox) {
                    mFillInBox.add((FillInBox) subBox);
                } else {
                    addFillInBox(subBox);
                }
            }
        }
    }

    public FillInBox getFillInBoxAtIndex(int index) {
        if (mFillInBox != null && !mFillInBox.isEmpty()) {
            for (int i = 0; i < mFillInBox.size(); i++) {
                FillInBox fillInBox = mFillInBox.get(i);
                if (fillInBox.getIndex() == index) {
                    return fillInBox;
                }
            }
        }
        return null;
    }

    public FillInBox getFillInBox(float x, float y) {
        if (mFillInBox != null && !mFillInBox.isEmpty()) {
            for (int i = 0; i < mFillInBox.size(); i++) {
                FillInBox fillInBox = mFillInBox.get(i);
                if (fillInBox.getVisibleRect().contains(x, y)) {
                    return fillInBox;
                }
            }
        }
        return null;
    }

    private FocusChangeListener mFocusChangeListener = new FocusChangeListener() {
        @Override
        public void onFocusChange(int index) {
            updateFocus();
        }
    };

    private LaTexTextChangeListener mTextChangeListener = new LaTexTextChangeListener() {
        @Override
        public void onLaTexTextChange(String latexText) {
            setLatexTextImpl(latexText);
            updateFocus();
        }
    };
}
