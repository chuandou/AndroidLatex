package com.hyena.fillin.utils;

import android.content.Context;

import com.himamis.retex.renderer.share.MacroInfo;
import com.hyena.fillin.core.PluginMacroInfo;

/**
 * Created by yangzc on 16/6/13.
 */
public class PluginInstaller {

    public static void install(Context context) {
        AjLatexMath.init(context);
        MacroInfo.Commands.put("fillIn", new PluginMacroInfo(1, 2));
    }
}
