package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.Node;

public class LabelStar extends Label {

    private final ShortestPathData data;

    public LabelStar(Node current, boolean marked, double realCost, Node father, ShortestPathData data) {
        super(current, marked, realCost, father);
        this.data = data;
    }

    @Override
    public double getTotalCost() {
        if (this.data.getMode() == AbstractInputData.Mode.LENGTH) {
            return this.getOriginCost() + this.getNode().getPoint().distanceTo(this.data.getDestination().getPoint());
        }

        double speed = this.data.getGraph().getGraphInformation().getMaximumSpeed() / 3.6;

        return this.getOriginCost() + this.getNode().getPoint().distanceTo(this.data.getDestination().getPoint()) / speed;
    }
}
