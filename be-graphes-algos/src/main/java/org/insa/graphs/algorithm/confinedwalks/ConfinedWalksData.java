package org.insa.graphs.algorithm.confinedwalks;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

public class ConfinedWalksData extends AbstractInputData {
    private final Node center;

    private final double innerRadius;

    private final double outerRadius;

    private final Node startNode;

    /**
     * Create a new AbstractInputData instance for the given graph, mode and filter.
     *
     * @param graph        Graph for this input data.
     * @param arcInspector Arc inspector for this input data.
     */
    public ConfinedWalksData(Graph graph, ArcInspector arcInspector, Node center, double innerRadius, double outerRadius) {
        super(graph, arcInspector);
        this.center = center;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.startNode = this.calculateStartNode();
    }

    private Node calculateStartNode() {
        Node nearestNode = null;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (Node node : this.getGraph().getNodes()) {
            double distance = node.getPoint().distanceTo(this.getCenter().getPoint());

            if (distance > this.getInnerRadius() && distance < nearestDistance) {
                nearestNode = node;
                nearestDistance = distance;
            }
        }

        return nearestNode;
    }

    @Override
    public boolean isAllowed(Arc arc) {
        double distanceTo = arc.getDestination().getPoint().distanceTo(this.getCenter().getPoint());
        double distanceFrom = arc.getOrigin().getPoint().distanceTo(this.getCenter().getPoint());

        return super.isAllowed(arc)
                && distanceTo >= this.getInnerRadius()
                && distanceTo <= this.getOuterRadius()
                && distanceFrom >= this.getInnerRadius()
                && distanceFrom <= this.getOuterRadius();
    }

    public Node getCenter() {
        return center;
    }

    public double getInnerRadius() {
        return innerRadius;
    }

    public double getOuterRadius() {
        return outerRadius;
    }

    public Node getStartNode() {
        return startNode;
    }
}
