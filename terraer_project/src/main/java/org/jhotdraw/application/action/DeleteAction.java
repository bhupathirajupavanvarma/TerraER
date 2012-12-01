/*
 * @(#)DeleteAction.java  1.0  October 9, 2005
 *
 * Copyright (c) 1996-2006 by the original authors of JHotDraw
 * and all its contributors ("JHotDraw.org")
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * JHotDraw.org ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * JHotDraw.org.
 */

package org.jhotdraw.application.action;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.JTextComponent;

import org.jhotdraw.application.EditableComponent;
/**
 * Deletes the region at (or after) the caret position.
 * Acts on the EditableComponent or JTextComponent which had the focus when
 * the ActionEvent was generated.
 *
 * @author Werner Randelshofer
 * @version 1.0 October 9, 2005 Created.
 */
public class DeleteAction extends AbstractApplicationAction {
    public final static String ID = "Edit.delete";

    /** Creates a new instance. */
    public DeleteAction() {
        initActionProperties(ID);
    }
    
    public void actionPerformed(ActionEvent evt) {
        Component focusOwner = KeyboardFocusManager.
                getCurrentKeyboardFocusManager().
                getPermanentFocusOwner();
        if (focusOwner != null && focusOwner instanceof EditableComponent) {
            ((EditableComponent) focusOwner).delete();
        } else if (focusOwner instanceof JTextComponent) {
            deleteNextChar(evt, (JTextComponent) focusOwner);
        }
    }
    /** This method was copied from
     * DefaultEditorKit.DeleteNextCharAction.actionPerformed(ActionEvent).
     */
    public void deleteNextChar(ActionEvent e, JTextComponent target) {
        boolean beep = true;
        if ((target != null) && (target.isEditable())) {
            try {
                javax.swing.text.Document doc = target.getDocument();
                Caret caret = target.getCaret();
                int dot = caret.getDot();
                int mark = caret.getMark();
                if (dot != mark) {
                    doc.remove(Math.min(dot, mark), Math.abs(dot - mark));
                    beep = false;
                } else if (dot < doc.getLength()) {
                    doc.remove(dot, 1);
                    beep = false;
                }
            } catch (BadLocationException bl) {}
        }
        if (beep) {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
