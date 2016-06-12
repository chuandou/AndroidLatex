package com.himamis.retex.editor.android.event;

import android.os.Build;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;

import com.himamis.retex.editor.share.event.KeyListener;

public class KeyListenerAdapter implements View.OnKeyListener {

    private KeyListener mKeyListener;

    public KeyListenerAdapter(KeyListener keyListener) {
        mKeyListener = keyListener;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (event.getAction()) {
            case KeyEvent.ACTION_DOWN:
                return mKeyListener.onKeyPressed(wrapEvent(event));
            case KeyEvent.ACTION_UP:
            case KeyEvent.ACTION_MULTIPLE:
                com.himamis.retex.editor.share.event.KeyEvent wrappedEvent = wrapEvent(event);
                boolean ret = mKeyListener.onKeyReleased(wrappedEvent);
                if (wrappedEvent.getUnicodeKeyChar() != '\0') {
                    ret |= mKeyListener.onKeyTyped(wrappedEvent);
                }
                return ret;
            default:
                return false;
        }
    }

    private static com.himamis.retex.editor.share.event.KeyEvent wrapEvent(KeyEvent keyEvent) {
        int keyCode = getKeyCode(keyEvent.getKeyCode());
        char charCode = getCharCode(keyEvent);
        int modifiers = getModifiers(keyEvent);
        return new com.himamis.retex.editor.share.event.KeyEvent(keyCode, modifiers, charCode);
    }

    private static int getKeyCode(int nativeKeyCode) {
        switch (nativeKeyCode) {
            case KeyEvent.KEYCODE_DEL:
                return com.himamis.retex.editor.share.event.KeyEvent.VK_BACK_SPACE;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                return com.himamis.retex.editor.share.event.KeyEvent.VK_LEFT;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return com.himamis.retex.editor.share.event.KeyEvent.VK_RIGHT;
            case KeyEvent.KEYCODE_DPAD_UP:
                return com.himamis.retex.editor.share.event.KeyEvent.VK_UP;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                return com.himamis.retex.editor.share.event.KeyEvent.VK_DOWN;
        }
        return 0;
    }

    private static int getModifiers(KeyEvent keyEvent) {
        if (Build.VERSION.SDK_INT >= 13) {
            // TODO use the same modifiers as in natur/cuni... KeyEvent
            return keyEvent.getModifiers();
        }
        // TODO ?
        return 0;
    }

    private static char getCharCode(KeyEvent keyEvent) {
        int unicodeChar = keyEvent.getUnicodeChar();
        if (keyEvent.getAction() == KeyEvent.ACTION_MULTIPLE && keyEvent.getKeyCode() == KeyEvent.KEYCODE_UNKNOWN) {
            // return the first character
            return keyEvent.getCharacters().charAt(0);
        } else if ((unicodeChar & KeyCharacterMap.COMBINING_ACCENT) != 0 || (unicodeChar == 0)) {
            return '\0';
        } else {
            return (char) unicodeChar;
        }
    }
}
