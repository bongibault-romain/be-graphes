package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Path;
import org.junit.Assume;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;


public class DijkstraAlgorithmTest extends ShortestPathAlgorithmTest {
    @Override
    public ShortestPathAlgorithm createShortestPathAlgorithm(ShortestPathData data) {
        return new DijkstraAlgorithm(data);
    }
}
