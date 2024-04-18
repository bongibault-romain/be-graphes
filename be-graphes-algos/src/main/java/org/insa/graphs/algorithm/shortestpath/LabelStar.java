package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.Node;

public class LabelStar extends Label {

    private final Node destination;

    private final AbstractInputData.Mode mode;

    public LabelStar(Node current, boolean marked, double realCost, Node father, Node destination, AbstractInputData.Mode mode) {
        super(current, marked, realCost, father);
        this.destination = destination;
        this.mode = mode;
    }

    @Override
    public double getTotalCost() {
        if (this.mode == AbstractInputData.Mode.LENGTH) {
            return this.getOriginCost() + this.getNode().getPoint().distanceTo(this.destination.getPoint());
        }

        return this.getOriginCost();
    }
}
