package org.insa.graphs.algorithm.confinedwalks;

import org.insa.graphs.model.Node;

import java.io.PrintStream;

public class ConfinedWalksTextObserver implements ConfinedWalksObserver {

    private final PrintStream stream;

    public ConfinedWalksTextObserver(PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void notifyOriginProcessed(Node node) {
        stream.println("Origin processed: " + node.getId());
    }

    @Override
    public void notifyNodeMarked(Node node) {
        stream.println("Node marked: " + node.getId());
    }
}
