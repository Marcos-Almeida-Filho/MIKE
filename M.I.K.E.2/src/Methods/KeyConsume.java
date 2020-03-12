/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Methods;

import java.awt.event.KeyEvent;

/**
 *
 * @author Marcos Filho
 */
public class KeyConsume {
    
    public void altConsume(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ALT) {
            e.consume();
        }
    }
}
