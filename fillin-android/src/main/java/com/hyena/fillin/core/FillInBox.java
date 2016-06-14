package com.hyena.fillin.core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.himamis.retex.renderer.android.graphics.Graphics2DA;
import com.himamis.retex.renderer.android.graphics.ScaleStack;
import com.himamis.retex.renderer.share.Box;
import com.himamis.retex.renderer.share.FontInfo;
import com.himamis.retex.renderer.share.platform.font.Font;
import com.himamis.retex.renderer.share.platform.graphics.Graphics2DInterface;
import com.hyena.fillin.utils.AjLatexMath;

/**
 * Created by yangzc on 16/6/13.
 */
public class FillInBox extends Box {

    private final CStringFont cf;
    private final float size;
    private CString cString;
    private RectF mRect = new RectF();
    private RectF mBorderF = new RectF();

    public FillInBox(CString string) {
        super();
        this.cString = string;
        cf = string.getStringFont();
        size = string.getMetrics().getSize();
        width = string.getWidth();
        height = string.getHeight();
        depth = string.getDepth();
    }

    @Override
    public void draw(Graphics2DInterface g2, float x, float y) {
        if (g2 instanceof Graphics2DA) {
            Graphics2DA graphics2DA = (Graphics2DA) g2;

            drawDebug(g2, x, y);
            g2.saveTransformation();
            g2.translate(x, y);
            Font font = FontInfo.getFont(cf.fontId);
            if (size != 1) {
                g2.scale(size, size);
            }
            if (g2.getFont() != font) {
                g2.setFont(font);
            }

            Canvas canvas = graphics2DA.getCanvas();
            Paint paint = AjLatexMath.getPaint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(0);
            ScaleStack scaleStack = graphics2DA.getScaleStack();
            mRect.set(0, -height - 0.1f, width, 0.1f);

            mBorderF.set(x, y - height - 0.1f, x + width, y + 0.1f);
            scaleStack.scaleRectF(mBorderF);

            if (focus) {
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(graphics2DA.getScaleStack().scaleRectF(mRect), paint);
            }

            char chs[] = cString.getString().toCharArray();
            g2.drawChars(chs, 0, chs.length, 0, 0);
            g2.restoreTransformation();
        }
    }

    @Override
    public int getLastFontId() {
        return cf.fontId;
    }

    public RectF getVisibleRect() {
        Log.v("yangzc", mBorderF.toString());
        return mBorderF;
    }

    private boolean focus = false;

    public void setFocus(boolean focus) {
        this.focus = focus;
    }
}
