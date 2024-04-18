package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {

    private final Node current;

    private boolean marked;

    private final double originCost;

    private final Node father;

    public Label(Node current, boolean marked, double originCost, Node father) {
        this.current = current;
        this.marked = marked;
        this.originCost = originCost;
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

    public double getOriginCost() {
        return originCost;
    }

    public double getTotalCost() {
        return originCost;
    }

    public Node getParent() {
        return father;
    }

    @Override
    public int compareTo(Label o) {
        return Double.compare(this.getTotalCost(), o.getTotalCost());
    }
}
