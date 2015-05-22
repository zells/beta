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
    private boolean failed = false;
    private ErrorListener errorListener;

    public CellTreeNode(Engine engine, Path path, CellTreeNode parent, ErrorListener error) {
        this.engine = engine;
        this.path = path;
        this.parent = parent;
        this.errorListener = error;

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
                CellTreeNode node = new CellTreeNode(engine, path.with(child), this, errorListener);
                node.setModel(model);
                children.add(node);
            }
        }
        return children;
    }

    private List<String> listChildren() {
        return new Runner<List<String>>("reading children") {
            @Override
            public List<String> run() {
                return engine.listChildren(path);
            }

            @Override
            public List<String> failed() {
                return engine.listOwnChildren(path);
            }
        }.tryToRun();
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

    public boolean hasFailed() {
        return failed;
    }

    public boolean isOwnChild() {
        return parent == null || engine.listOwnChildren(parent.getPath()).contains(path.last());
    }

    public void changeValue(String newValue) {
        final String[] nameAndStem = newValue.split(":");
        final String name = nameAndStem[0].trim();
        final Path stem = Path.parse(nameAndStem[1].trim());

        if (!stem.equals(engine.getStem(path))) {
            new Runner("changing stem") {
                @Override
                public Object run() {
                    engine.changeStem(path, stem);
                    return null;
                }
            }.tryToRun();
        }

        if (!name.equals(path.last())) {
            new Runner("renaming") {
                @Override
                public Object run() {
                    engine.copy(path, path.parent(), name);
                    engine.delete(path);
                    return null;
                }
            }.tryToRun();
        }
    }

    private abstract class Runner<T> {
        private String action;

        public Runner(String action) {
            this.action = action;
        }

        public abstract T run();

        public T failed() {
            return null;
        }

        public T tryToRun() {
            try {
                failed = false;
                return run();
            } catch (Exception e) {
                failed = true;
                if (errorListener != null) {
                    errorListener.onError("Error while " + action + " of " + path + ": " + e.getMessage());
                }
                return failed();
            }
        }
    }

    public interface ErrorListener {
        void onError(String message);
    }
}
