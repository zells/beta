package org.rtens.zells.beta.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inspector extends JPanel implements ActionListener {

    private int newNodeSuffix = 1;

    private static String ADD_COMMAND = "add";
    private static String REMOVE_COMMAND = "remove";

    private CellTree tree;

    public Inspector() {
        super(new BorderLayout());

        tree = new CellTree();

        JButton addButton = new JButton("Add");
        addButton.setActionCommand(ADD_COMMAND);
        addButton.addActionListener(this);

        JButton removeButton = new JButton("Remove");
        removeButton.setActionCommand(REMOVE_COMMAND);
        removeButton.addActionListener(this);

        tree.setPreferredSize(new Dimension(300, 150));
        add(tree, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(addButton);
        panel.add(removeButton);
        add(panel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (ADD_COMMAND.equals(command)) {

            String name = JOptionPane.showInputDialog(this, "Name:");
            if (name != null) {
                String stem = JOptionPane.showInputDialog(this, "Stem:", "°.cell");
                if (stem != null) {
                    tree.addCell(name, stem);
                } else {
                    tree.addCell(name);
                }
            }
        } else if (REMOVE_COMMAND.equals(command)) {
            tree.removeCell();
        }
    }

    private static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Couldn't use system look and feel.");
        }

        JFrame frame = new JFrame("Inspector");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setContentPane(new Inspector());

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
