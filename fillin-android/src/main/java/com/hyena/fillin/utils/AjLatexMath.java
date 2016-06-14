package com.hyena.fillin.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by yangzc on 16/6/13.
 */
public class AjLatexMath {

    private static Context mContext;
    private static Paint st;

    public static void init(Context context) {
        mContext = context;
        st = new Paint();
        st.setStyle(Paint.Style.FILL_AND_STROKE);
        st.setColor(Color.BLACK);
        st.setStrokeWidth(1);
    }

    public static AssetManager getAssetManager() {
        AssetManager mng = mContext.getAssets();
        return mng;
    }

    public static Context getContext() {
        return mContext;
    }

    public static Paint getPaint() {
        return st;
    }

    public static float getLeading(float textSize){
        st.setTextSize(textSize);
        return st.getFontSpacing();
    }

}
