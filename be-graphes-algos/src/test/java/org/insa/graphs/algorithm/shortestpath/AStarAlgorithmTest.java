package org.insa.graphs.algorithm.shortestpath;


import org.insa.graphs.model.Path;
import org.junit.Assume;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class AStarAlgorithmTest extends ShortestPathAlgorithmTest {
    @Override
    public ShortestPathAlgorithm createShortestPathAlgorithm(ShortestPathData data) {
        return new AStarAlgorithm(data);
    }

    @Test
    public void solutionEqualsDijkstra(){
        DijkstraAlgorithm dijkstraAlgo = new DijkstraAlgorithm(this.inputData);
        ShortestPathSolution dijkstraSolution = dijkstraAlgo.run();

        assertSame(dijkstraSolution.getStatus(), this.solution.getStatus());

        if (!this.solution.isFeasible()) return;

        assertEquals(dijkstraSolution.getPath().getLength(), this.solution.getPath().getLength(), 0.01);
        assertSame(dijkstraSolution.isFeasible(), this.solution.isFeasible());

        if (!this.solution.isFeasible()) {
            return;
        }

        Path solutionPath = this.solution.getPath();
        Path bFSolutionPath = dijkstraSolution.getPath();

        assertEquals(solutionPath.getArcs().size(), bFSolutionPath.getArcs().size());

        for (int i = 0; i < solutionPath.getArcs().size(); i++) {
            assertSame(solutionPath.getArcs().get(i).getDestination(), bFSolutionPath.getArcs().get(i).getDestination());
        }
    }
}
