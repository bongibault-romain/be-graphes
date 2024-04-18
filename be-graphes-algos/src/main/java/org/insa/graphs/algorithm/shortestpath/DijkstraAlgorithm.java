package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.PriorityQueue;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

import java.util.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;

        // Initialize the origin label
        Node origin = data.getOrigin();

        Map<Node, Label> labels = new HashMap<>();

        // Insert origin in labels;
        labels.put(origin, new Label(origin, false, 0, null));

        // Initialize the heap
        PriorityQueue<Label> heap = new BinaryHeap<>();

        // Insert the origin in the heap
        heap.insert(labels.get(origin));

        while ((!labels.containsKey(data.getDestination())
                || !labels.get(data.getDestination()).isMarked())
                && !heap.isEmpty()) {
            Label label = heap.deleteMin();
            Node node = label.getNode();
            label.setMarked(true);

            if (node == data.getOrigin()) {
                this.notifyOriginProcessed(node);
            }

            this.notifyNodeMarked(node);

            for (Arc successorArc : node.getSuccessors()) {
                if (!data.isAllowed(successorArc)) {
                    continue;
                }

                Label destinationLabel = labels.getOrDefault(successorArc.getDestination(), null);

                if (destinationLabel != null && destinationLabel.isMarked()) {
                    continue;
                }

                double oldDistance = Double.POSITIVE_INFINITY;

                if (destinationLabel != null) {
                    oldDistance = destinationLabel.getRealCost();
                }

                double cost = data.getCost(successorArc);
                double newDistance = labels.get(node).getRealCost() + cost;

                this.notifyNodeReached(successorArc.getDestination());

                if (successorArc.getDestination() == data.getDestination()) {
                    this.notifyDestinationReached(data.getDestination());
                }

                if (newDistance < oldDistance) {
                    Label newDestinationLabel = new Label(successorArc.getDestination(), false, newDistance, node);

                    labels.put(
                            successorArc.getDestination(),
                            newDestinationLabel
                    );

                    if (destinationLabel != null) {
                        heap.remove(destinationLabel);
                    }
                    heap.insert(newDestinationLabel);
                }
            }
        }

        Label destinationLabel = labels.get(data.getDestination());

        if (destinationLabel != null) {
            ArrayList<Arc> arcs = new ArrayList<>();

            Label currentLabel = destinationLabel;

            while (currentLabel.getNode() != data.getOrigin()) {
                Label label = currentLabel;

                Node father = currentLabel.getParent();
                Arc arc = father.getSuccessors().stream()
                        .filter(successor -> successor.getDestination() == label.getNode())
                        .min(Comparator.comparingDouble(data::getCost))
                        .orElse(null);

                if (arc == null) {
                    throw new RuntimeException("No arc found");
                }

                arcs.add(0, arc);
                currentLabel = labels.get(father);
            }

            solution = new ShortestPathSolution(
                    data,
                    AbstractSolution.Status.OPTIMAL,
                    new Path(data.getGraph(), arcs)
            );


        } else {
            solution = new ShortestPathSolution(data, AbstractSolution.Status.INFEASIBLE);
        }

        return solution;
    }

}
