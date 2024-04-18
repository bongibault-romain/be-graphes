package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {

    private final Node current;

    private boolean marked;

    private final double realCost;

    private final Node father;

    public Label(Node current, boolean marked, double realCost, Node father) {
        this.current = current;
        this.marked = marked;
        this.realCost = realCost;
        this.father = father;
    }

    public Node getNode() {
        return this.current;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public double getRealCost() {
        return realCost;
    }

    public Node getParent() {
        return father;
    }

    @Override
    public int compareTo(Label o) {
        return Double.compare(this.realCost, o.getRealCost());
    }
}
