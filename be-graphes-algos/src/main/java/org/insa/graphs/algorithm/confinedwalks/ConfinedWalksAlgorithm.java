package org.insa.graphs.algorithm.confinedwalks;

import org.insa.graphs.algorithm.AbstractAlgorithm;
import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.PriorityQueue;
import org.insa.graphs.algorithm.utils.Vector2D;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Point;

import javax.crypto.Cipher;
import java.util.*;

public class ConfinedWalksAlgorithm extends AbstractAlgorithm<ConfinedWalksObserver> {
    protected ConfinedWalksAlgorithm(ConfinedWalksData data) {
        super(data);
    }

    @Override
    public ConfinedWalksSolution run() {
        return (ConfinedWalksSolution) super.run();
    }

    @Override
    protected ConfinedWalksSolution doRun() {
        ConfinedWalksData data = this.getInputData();
        Node origin = data.getCenter();
        double radius = data.getInnerRadius();

        Map<Node, ConfinedLabel> labels = new HashMap<>();
        PriorityQueue<ConfinedLabel> heap = new BinaryHeap<>();

        // Find the nearest node to the origin that is outside the radius
        Node startNode = this.getInputData().getStartNode();

        if (startNode == null) {
            return new ConfinedWalksSolution(data, AbstractSolution.Status.INFEASIBLE, null);
        }

        ConfinedLabel startLabel = this.createLabel(startNode, 0, null);

        labels.put(startNode, startLabel);
        heap.insert(startLabel);

        while (this.shouldContinue(heap, labels)) {
            ConfinedLabel label = heap.deleteMin();
            Node node = label.getCurrent();
            label.setMarked(true);

            this.notifyNodeMarked(node);

            for (Arc arc : node.getSuccessors()) {
                if (!data.isAllowed(arc)) {
                    continue;
                }

                Node successor = arc.getDestination();
                ConfinedLabel successorLabel = labels.getOrDefault(successor, null);

                if (successorLabel == null) {
                    successorLabel = this.createLabel(successor, Double.POSITIVE_INFINITY, null);
                    labels.put(successor, successorLabel);
                    heap.insert(successorLabel);
                }

                System.out.println("Successor: " + successor.getId() + " Angle: " + label.getAngle() + " Distance: " + label.getDistance() + " Cost: " + data.getCost(arc));

                if (successor == startNode && label.getAngle() >= Math.PI) {
                    ConfinedLabel newSuccessorLabel = this.createLabel(successor, label.getDistance() + data.getCost(arc), node);
                    labels.put(successor, newSuccessorLabel);
                }

                if (successorLabel.isMarked()) {
                    continue;
                }

                ConfinedLabel newSuccessorLabel = this.createLabel(successor, label.getDistance() + data.getCost(arc), node);

                if (successorLabel.compareTo(newSuccessorLabel) > 0) {
                    heap.remove(successorLabel);
                    labels.put(successor, newSuccessorLabel);
                    heap.insert(newSuccessorLabel);
                }
            }


        }


        if (labels.get(startNode).getParent() != null) {
            ArrayList<Arc> arcs = new ArrayList<>();
            Node currentNode = startNode;

            while (labels.get(currentNode).getParent() != null) {
                Node parent = labels.get(currentNode).getParent();

                for (Arc arc : parent.getSuccessors()) {
                    if (arc.getDestination() == currentNode) {
                        arcs.add(arc);
                        break;
                    }
                }

                currentNode = parent;
            }

            Path path = new Path(data.getGraph(), arcs);

            return new ConfinedWalksSolution(data, AbstractSolution.Status.OPTIMAL, path);
        }

        return new ConfinedWalksSolution(data, AbstractSolution.Status.INFEASIBLE, null);
    }

    protected boolean shouldContinue(PriorityQueue<ConfinedLabel> heap, Map<Node, ConfinedLabel> labels) {
        Node startNode = this.getInputData().getStartNode();

        return (!heap.isEmpty()
                && labels.get(startNode).getParent() != null)
                || (!heap.isEmpty() && heap.findMin().getAngle() < Math.PI);
    }

    protected ConfinedLabel createLabel(Node node, double distance, Node parent) {
        double angle = this.calculateAngle(node);

        return new ConfinedLabel(node, distance, angle, parent);
    }

    /**
     * On a sphere, calculate the angle between the (OS) and (ON) vectors, where O is the center of the sphere, S is the starting node and N is the node.
     * cos c = cos a cos b + sin a sin b cos C
     * => C = acos(cos c - cos a cos b / sin a sin b)
     * @param node Node to calculate the angle for.
     * @return Angle between the (OS) and (ON) vectors.
     */
    private double calculateAngle(Node node) {
        Point C = this.getInputData().getCenter().getPoint();
        Point S = this.getInputData().getStartNode().getPoint();
        Point N = node.getPoint();

        Vector2D OS = new Vector2D(
                S.getLongitude() - C.getLongitude(),
                S.getLatitude() - C.getLatitude()
        );

        Vector2D ON = new Vector2D(
                N.getLongitude() - C.getLongitude(),
                N.getLatitude() - C.getLatitude()
        );

        return OS.angle(ON);
    }

    @Override
    public ConfinedWalksData getInputData() {
        return (ConfinedWalksData) super.getInputData();
    }

    /**
     * Notify all observers that the origin has been processed.
     *
     * @param node Origin.
     */
    public void notifyOriginProcessed(Node node) {
        for (ConfinedWalksObserver obs: getObservers()) {
            obs.notifyOriginProcessed(node);
        }
    }

    /**
     * Notify all observers that a node has been marked, i.e. its final value has
     * been set.
     *
     * @param node Node that has been marked.
     */
    public void notifyNodeMarked(Node node) {
        for (ConfinedWalksObserver obs: getObservers()) {
            obs.notifyNodeMarked(node);
        }
    }
}
