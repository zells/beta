package org.rtens.zells.beta.gui;

import org.rtens.zells.beta.Engine;
import org.rtens.zells.beta.Observer;
import org.rtens.zells.beta.Path;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class CellTreeNode implements TreeNode, Observer {

    private final Engine engine;
    private final Path path;
    private final CellTreeNode parent;
    private List<CellTreeNode> children;
    private CellTreeModel model;

    public CellTreeNode(Engine engine, Path path, CellTreeNode parent) {
        this.engine = engine;
        this.path = path;
        this.parent = parent;

        engine.observe(path, this);
    }

    @Override
    public String toString() {
        Path stem = engine.getStem(path);
        return path.last() + (stem != null ? " : " + stem : "");
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return getChildren().get(childIndex);
    }

    @Override
    public int getChildCount() {
        return getChildren().size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        if (!(node instanceof CellTreeNode)) {
            return -1;
        }
        return getChildren().indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return getChildCount() == 0;
    }

    @Override
    public Enumeration children() {
        throw new RuntimeException("Not implemented");
    }

    public List<CellTreeNode> getChildren() {
        if (children == null) {
            children = new ArrayList<CellTreeNode>();
            for (String child : listChildren()) {
                CellTreeNode node = new CellTreeNode(engine, path.with(child), this);
                node.setModel(model);
                children.add(node);
            }
        }
        return children;
    }

    private List<String> listChildren() {
        try {
            return engine.listChildren(path);
        } catch (Exception e) {
            return engine.listOwnChildren(path);
        }
    }

    public void setModel(CellTreeModel model) {
        this.model = model;
    }

    @Override
    public void cellChanged(Path cell) {
        children = null;
        model.nodeChanged(this);
    }

    public TreePath getTreePath() {
        return new TreePath(_getTreePath().toArray());
    }

    private List<CellTreeNode> _getTreePath() {
        List<CellTreeNode> path;
        if (parent != null) {
            path = parent._getTreePath();
        } else {
            path = new ArrayList<CellTreeNode>();
        }
        path.add(this);

        return path;
    }

    public Path getPath() {
        return path;
    }
}
