package org.rtens.zells.beta.gui;

import org.rtens.zells.beta.Engine;
import org.rtens.zells.beta.Path;
import org.rtens.zells.beta.model.SerialEngine;

import javax.swing.*;
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

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }

    public void addCell(String name) {
        CellTreeNode parent = getSelectedNode();
        if (parent == null) {
            parent = root;
        }

        engine.create(parent.getPath(), name);
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
}
