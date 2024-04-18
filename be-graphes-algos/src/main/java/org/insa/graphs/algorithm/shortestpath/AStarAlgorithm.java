package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    public Label createLabel(Node node, double cost, Node parent) {
        return new LabelStar(node, false, cost, parent, this.getInputData().getDestination(), this.data.getMode());
    }
}
