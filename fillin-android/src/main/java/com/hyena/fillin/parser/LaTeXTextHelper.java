package com.hyena.fillin.parser;

import android.text.TextUtils;
import android.util.SparseArray;

/**
 * Created by yangzc on 16/6/14.
 */
public class LaTeXTextHelper {

    private SparseArray<String> mTextSparse = new SparseArray<String>();

    private int mSelectedIndex = 0;
    private String mLatexText;

    private LaTexTextChangeListener mTextChangeListener = null;
    private FocusChangeListener mFocusChangeListener = null;

    public LaTeXTextHelper(FocusChangeListener focusChangeListener,
                           LaTexTextChangeListener texTextChangeListener) {
        this.mFocusChangeListener = focusChangeListener;
        this.mTextChangeListener = texTextChangeListener;
    }

    public void setLatexText(String latexText) {
        this.mLatexText = latexText;
        notifyTextChange();
    }

    public void setTextAtIndex(int index, String text) {
        if (TextUtils.isEmpty(mLatexText))
            return;

        if (TextUtils.isEmpty(text))
            text = "";

        setSelectIndex(index);
        mTextSparse.put(index, text);
        mLatexText = mLatexText.replaceFirst("fillIn\\{" + index + "\\}\\{.*?\\}", "fillIn{" + index + "}{" + text + "}");
        notifyTextChange();
    }

    public void setSelectIndex(int index) {
        this.mSelectedIndex = index;
        notifyIndexChange();
    }

    public int getSelectIndex() {
        return mSelectedIndex;
    }

    public String getTextAtIndex(int index) {
        return mTextSparse.get(index);
    }

    public void appendText(int index, String text) {
        String oldText = getTextAtIndex(index);
        if (TextUtils.isEmpty(oldText)) {
            oldText = "";
        }
        setTextAtIndex(index, oldText + text);
    }

    public void appendText(String text) {
        appendText(mSelectedIndex, text);
    }

    public String getLatexText() {
        return mLatexText;
    }

    private void notifyTextChange() {
        if (mTextChangeListener != null) {
            mTextChangeListener.onLaTexTextChange(mLatexText);
        }
    }

    private void notifyIndexChange() {
        if (mFocusChangeListener != null) {
            mFocusChangeListener.onFocusChange(mSelectedIndex);
        }
    }
}
