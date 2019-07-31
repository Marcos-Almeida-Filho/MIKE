/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Marcos Filho
 */
public class JTextFieldLimit extends PlainDocument {

    private final int LIMIT;

    JTextFieldLimit(int limit) {
        super();
        this.LIMIT = limit;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return;
        }

        if ((getLength() + str.length()) <= LIMIT) {
            super.insertString(offset, str, attr);
        }
    }
}
