/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.SwingMain;

import Email.MessageController;
import Email.Summary;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author chanman
 */
class FolderTableModel extends AbstractTableModel {

    MessageController controller;
    String[] messages;

    public FolderTableModel(MessageController controller, String folderId) {
        this.controller = controller;
        messages = controller.getEmailList(folderId);
    }

    @Override
    public int getRowCount() {
        return messages.length;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Summary summary = controller.getEmailSummary(messages[row]);

        switch (col) {
            case 0:
                return summary.getDate();
            case 1:
                return summary.getFrom();
            case 2:
                return summary.getSubject();
            default:
                return new String();
        }

    }

    public String getMessageId(int selected) {
        try {
            return messages[selected];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}
