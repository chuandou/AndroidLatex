package com.hyena.fillin.core;

/**
 * Created by yangzc on 16/6/13.
 */
public class CStringFont {

    public String c;
    public int fontId;
    public int boldFontId;

    public CStringFont(String ch, int f) {
        this(ch, f, f);
    }

    public CStringFont(String ch, int f, int bf) {
        c = ch;
        fontId = f;
        boldFontId = bf;
    }

}
