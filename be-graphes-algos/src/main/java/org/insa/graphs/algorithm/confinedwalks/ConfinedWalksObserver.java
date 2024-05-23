package org.insa.graphs.algorithm.confinedwalks;

import org.insa.graphs.model.Node;

public interface ConfinedWalksObserver {

    /**
     * Notify the observer that the origin has been processed.
     *
     * @param node Origin.
     */
    public void notifyOriginProcessed(Node node);

    /**
     * Notify the observer that a node has been marked, i.e. its final
     * value has been set.
     *
     * @param node Node that has been marked.
     */
    public void notifyNodeMarked(Node node);
}
