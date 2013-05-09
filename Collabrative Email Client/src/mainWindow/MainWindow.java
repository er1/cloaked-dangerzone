package mainWindow;

import javax.swing.*;

import java.awt.Container;
import java.awt.event.*;
import net.miginfocom.swing.MigLayout;

public class MainWindow extends JFrame implements ActionListener {

    ToolRibbon toolRibbon = new ToolRibbon();
    MenuMain menuBar = new MenuMain();

    public MainWindow() {
        JFrame window = new JFrame("Email Client");
        Container contentPane = window.getContentPane();
        contentPane.setLayout(new MigLayout("fill"));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JToolBar tool_bar = toolRibbon.toolbar();
        JMenuBar mainmenu = menuBar.menumail();
        TreeExplorer inboxTree = new TreeExplorer();
        EmailListDisplayPane emailListDisplayPane = new EmailListDisplayPane();
        EmailContentPane emailContentPane = new EmailContentPane();

        window.setJMenuBar(mainmenu);

        contentPane.add(tool_bar, "north");
        contentPane.add(inboxTree, "west");
        //TODO Figure out why this isn't filling the full screen width
        contentPane.add(emailListDisplayPane, "grow,wrap");
        contentPane.add(emailContentPane, "grow");

        window.setSize(1000, 600);
        window.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
    }
}
