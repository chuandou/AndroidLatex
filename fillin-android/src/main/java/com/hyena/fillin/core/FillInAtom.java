package com.hyena.fillin.core;

import com.himamis.retex.renderer.android.font.FontA;
import com.himamis.retex.renderer.share.Atom;
import com.himamis.retex.renderer.share.Box;
import com.himamis.retex.renderer.share.Char;
import com.himamis.retex.renderer.share.ScaleBox;
import com.himamis.retex.renderer.share.TeXEnvironment;
import com.himamis.retex.renderer.share.TeXFont;

/**
 * Created by yangzc on 16/6/13.
 */
public class FillInAtom extends Atom {

    private String mIndex;
    private String mText;
    private String textStyle;

    public FillInAtom(String index, String text) {
        this.mIndex = index;
        this.mText = text;
    }

    @Override
    public Box createBox(TeXEnvironment env) {
        if (textStyle == null) {
            String ts = env.getTextStyle();
            if (ts != null) {
                textStyle = ts;
            }
        }
        boolean smallCap = env.getSmallCap();
        CString string = getString(env.getTeXFont(), env.getStyle(), smallCap);
        int index = 0;
        try {
            index = Integer.valueOf(mIndex);
        } catch (Exception e) {}
        Box box = new FillInBox(index, string);
        if (smallCap && Character.isLowerCase('0')) {
            // We have a small capital
            box = new ScaleBox(box, 0.8f, 0.8f);
        }
        return box;
    }

    private CString getString(TeXFont tf, int style, boolean smallCap) {
        Char ch;
        if (textStyle == null) {
            ch = tf.getDefaultChar('0', style);
        } else {
            ch = tf.getChar('0', textStyle, style);
        }
        FontA font = (FontA) ch.getFont();
        CString string = new CString(mText, font.getTypeface(),
                ch.getFontCode(), ch.getMetrics());
        return string;
    }
}
