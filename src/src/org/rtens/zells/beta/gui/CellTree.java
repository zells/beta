package org.rtens.zells.beta.gui;

import org.rtens.zells.beta.Engine;
import org.rtens.zells.beta.Path;
import org.rtens.zells.beta.model.SerialEngine;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

public class CellTree extends JPanel {

    private final Engine engine;
    private final JTree tree;
    private final CellTreeNode root;

    public CellTree() {
        super(new GridLayout(1, 0));

        engine = new SerialEngine();

        engine.create(new Path(), "one");
        engine.create(new Path(), "two");
        engine.create(new Path(), "three");
        engine.changeStem(new Path("one"), new Path("°", "two"));

        root = new CellTreeNode(engine, new Path("°"), null);
        CellTreeModel model = new CellTreeModel(root);
        root.setModel(model);

        tree = new JTree(model);
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        tree.setCellRenderer(new CellTreeCellRenderer());

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }

    public void addCell(String name, String stem) {
        Path cell = addCell(name);
        engine.changeStem(cell, Path.parse(stem));
    }

    public Path addCell(String name) {
        CellTreeNode parent = getSelectedNode();
        if (parent == null) {
            parent = root;
        }

        engine.create(parent.getPath(), name);
        return parent.getPath().with(name);
    }

    public void removeCell() {
        CellTreeNode node = getSelectedNode();
        if (node != null) {
            engine.delete(node.getPath());
        }
    }

    private CellTreeNode getSelectedNode() {
        return (CellTreeNode) tree.getLastSelectedPathComponent();
    }

    private class CellTreeCellRenderer implements TreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            CellTreeNode node = (CellTreeNode) value;
            JLabel label = new JLabel(node.toString());
            if (node.hasFailed()) {
                label.setForeground(Color.red);
            } else if (!node.isOwnChild()) {
                label.setForeground(Color.gray);
            }
            return label;
        }
    }
}
