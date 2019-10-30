/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Methods;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Marcos Filho
 */
public class MyCollorCellRenderer extends DefaultTableCellRenderer {

    private int rowToColor = -1;

    public MyCollorCellRenderer() {
    }

    public void setRowToColor(int row) {
        rowToColor = row;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

         if (rowToColor!=-1 && row==rowToColor)
            setBackground(Color.RED);
         return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);      
    }
}
