package org.insa.graphs.algorithm.shortestpath;

import org.junit.Test;


public class DijkstraAlgorithmTest extends ShortestPathAlgorithmTest {
    @Override
    public ShortestPathAlgorithm createShortestPathAlgorithm(ShortestPathData data) {
        return new DijkstraAlgorithm(data);
    }
}
