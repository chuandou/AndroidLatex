package com.hyena.fillin;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.himamis.retex.renderer.android.LaTeXView;
import com.himamis.retex.renderer.share.Box;
import com.hyena.fillin.core.FillInBox;
import com.hyena.fillin.utils.PluginInstaller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzc on 16/6/13.
 */
public class LatexFillInView extends LaTeXView {

    private List<FillInBox> mFillInBox = new ArrayList<FillInBox>();

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
    }

    @Override
    public void setLatexText(String latexText) {
        super.setLatexText(latexText);
        refreshFillInBox();
    }

    private FillInBox mFocusFillIn = null;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mTexIcon == null && mTexIcon.getSize() != 0)
            return false;

        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            {
                Log.v("yangzc", "width:" + mTexIcon.getTrueIconWidth() + ", height:" + mTexIcon.getTrueIconHeight());
                Log.v("yangzc", "x: " + x + ", y: " + y);
                FillInBox fillInBox = getFillInBox(x, y);
                if (fillInBox != null) {
                    if (mFocusFillIn != null) {
                        mFocusFillIn.setFocus(false);
                    }
                    fillInBox.setFocus(true);
                    mFocusFillIn = fillInBox;
                }
                postInvalidate();
                break;
            }
        }
        return true;
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

    public FillInBox getFillInBox(float x, float y) {
        if (mTexIcon == null || mTexIcon.getSize() == 0)
            return null;

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
}
