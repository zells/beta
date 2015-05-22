package org.rtens.zells.beta.gui;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

public class CellTreeModel implements TreeModel {

    private CellTreeNode root;
    private List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();

    public CellTreeModel(CellTreeNode root) {
        this.root = root;
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        return ((CellTreeNode) parent).getChildAt(index);
    }

    @Override
    public int getChildCount(Object parent) {
        return ((CellTreeNode) parent).getChildCount();
    }

    @Override
    public boolean isLeaf(Object node) {
        return ((CellTreeNode) node).isLeaf();
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        ((CellTreeNode) path.getLastPathComponent()).changeValue((String) newValue);
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return ((CellTreeNode) parent).getIndex((CellTreeNode) child);
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }

    public void nodeChanged(CellTreeNode node) {
        TreeModelEvent e = new TreeModelEvent(this, node.getTreePath(), null, null);

        for (TreeModelListener l : listeners) {
            l.treeStructureChanged(e);
        }
    }
}
