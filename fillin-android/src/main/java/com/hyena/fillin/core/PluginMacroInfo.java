package com.hyena.fillin.core;

import com.himamis.retex.renderer.share.MacroInfo;
import com.himamis.retex.renderer.share.TeXParser;
import com.himamis.retex.renderer.share.exception.ParseException;

/**
 * Created by yangzc on 16/6/13.
 */
public class PluginMacroInfo extends MacroInfo {

    private int id;

    public PluginMacroInfo(int id, int nbArgs, int posOpts) {
        super(nbArgs, posOpts);
        this.id = id;
    }

    public PluginMacroInfo(int id, int nbArgs) {
        super(nbArgs);
        this.id = id;
    }

    @Override
    public Object invoke(TeXParser tp, String[] args) throws ParseException {
        return invokeID(id, tp, args);
    }

    private static final Object invokeID(final int id, final TeXParser tp, final String[] args)
            throws ParseException {
        try {
            switch (id) {
                case 1: {//FillIn atom
                    return new FillInAtom(args[1]);
                }
                default:
                    return null;
            }
        } catch (Exception e) {
            throw new ParseException("Problem with command " + args[0] + " at position " + tp.getLine() + ":"
                    + tp.getCol() + "\n" + e.getMessage());
        }
    }

}
