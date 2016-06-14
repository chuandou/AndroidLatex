package com.hyena.fillin.core;

import android.graphics.Typeface;

import com.himamis.retex.renderer.share.Metrics;

/**
 * Created by yangzc on 16/6/13.
 */
public class CString {

    private String c;
    private final Typeface font;
    private final Metrics m;
    private final int fontCode;

    public CString(String c, Typeface f, int fc, Metrics m) {
        if (c == null) {
            c = "";
        }
        font = f;
        fontCode = fc;
        this.c = c;
        this.m = m;
    }

    public CStringFont getStringFont() {
        return new CStringFont(c, fontCode);
    }

    public String getString() {
        return c;
    }

    public Typeface getFont() {
        return font;
    }

    public int getFontCode() {
        return fontCode;
    }

    public float getWidth() {
        return m.getWidth() * c.toCharArray().length;
    }

    public float getItalic() {
        return m.getItalic();
    }

    public float getHeight() {
        return m.getHeight();
    }

    public float getDepth() {
        return m.getDepth();
    }

    public Metrics getMetrics() {
        return m;
    }

    public void setText(String text){
        this.c = text;
    }
}
