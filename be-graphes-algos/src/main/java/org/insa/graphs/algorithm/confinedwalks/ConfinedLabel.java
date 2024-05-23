package org.insa.graphs.algorithm.confinedwalks;

import org.insa.graphs.model.Node;

public class ConfinedLabel implements Comparable<ConfinedLabel> {

    private final Node current;

    private final double distance;

    private boolean marked;

    private final double angle;

    private final Node parent;

    public ConfinedLabel(Node current, double distance, double angle, Node parent) {
        this.current = current;
        this.marked = false;
        this.distance = distance;
        this.angle = angle;
        this.parent = parent;
    }

    public Node getCurrent() {
        return current;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public double getDistance() {
        return distance;
    }

    public double getAngle() {
        return angle;
    }

    public Node getParent() {
        return parent;
    }

    public double getCost() {
        return this.getAngle();
    }

    @Override
    public int compareTo(ConfinedLabel o) {
        return Double.compare(this.getCost(), o.getCost());
    }
}
