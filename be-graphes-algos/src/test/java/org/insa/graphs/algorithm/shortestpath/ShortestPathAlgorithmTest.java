package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.utils.PriorityQueue;
import org.insa.graphs.algorithm.utils.PriorityQueueTest;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public abstract class ShortestPathAlgorithmTest {

    private static Graph read(String mapName) throws IOException {
        // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        return reader.read();
    }

    @Parameterized.Parameters
    public static Collection<Object> data() throws IOException {
        Collection<Object> data = new ArrayList<>();

        // TODO: change maps URL
        final Graph insa = read("D:/projects/be-graphes/maps/insa.mapgr");
        final Graph belgium = read("D:/projects/be-graphes/maps/belgium.mapgr");

        // Chemin normal à l'INSA (filters[0] = shortest all roads allowed)
        data.add(new ShortestPathData(
                insa,
                insa.get(479),
                insa.get(702),
                ArcInspectorFactory.getAllFilters().get(0)
        ));

        // Chemin normal à l'INSA (filters[0] = shortest all roads allowed)
        data.add(new ShortestPathData(
                insa,
                insa.get(479),
                insa.get(702),
                ArcInspectorFactory.getAllFilters().get(0)
        ));

        // Chemin normal à l'INSA (filters[0] = fastest all roads allowed)
        data.add(new ShortestPathData(
                insa,
                insa.get(479),
                insa.get(702),
                ArcInspectorFactory.getAllFilters().get(2)
        ));

        // Chemin normal à l'INSA (filters[0] = fastest all roads allowed)
        data.add(new ShortestPathData(
                insa,
                insa.get(479),
                insa.get(702),
                ArcInspectorFactory.getAllFilters().get(2)
        ));

        // Trajet de longueur nulle
        data.add(new ShortestPathData(
                insa,
                insa.get(479),
                insa.get(479),
                ArcInspectorFactory.getAllFilters().get(0)
        ));

        // Trajet infaisable
        data.add(new ShortestPathData(
                insa,
                insa.get(186),
                insa.get(864),
                ArcInspectorFactory.getAllFilters().get(0)
        ));

        // Chemin long en Belgique (filters[0] = shortest, all roads allowed)
        data.add(new ShortestPathData(
                belgium,
                belgium.get(60975),
                belgium.get(804592),
                ArcInspectorFactory.getAllFilters().get(0)
        ));

        // Chemin long en Belgique (filters[2] = fastest, all roads allowed)
        data.add(new ShortestPathData(
                belgium,
                belgium.get(60975),
                belgium.get(804592),
                ArcInspectorFactory.getAllFilters().get(2)
        ));

        return data;
    }

    public abstract ShortestPathAlgorithm createShortestPathAlgorithm(ShortestPathData data);

    @Before
    public void init() {
        this.algorithm = this.createShortestPathAlgorithm(this.inputData);
        this.solution = this.algorithm.run();
    }

    private ShortestPathSolution solution;

    public ShortestPathAlgorithm algorithm;

    @Parameterized.Parameter
    public ShortestPathData inputData;

    @Test
    public void originShouldBeOrigin() {
        Assume.assumeTrue(this.solution.isFeasible());
        // vérifier que l'origine est bien l'origine
        assertEquals(this.inputData.getOrigin(), this.solution.getInputData().getOrigin());
    }

    @Test
    public void destinationShouldBeDestination() {
        Assume.assumeTrue(this.solution.isFeasible());
        // vérifier que la destination est bien la destination
        assertEquals(this.inputData.getDestination(), this.solution.getInputData().getDestination());
    }

    @Test
    public void solutionEqualsBellmanFord(){
        Assume.assumeTrue(this.inputData.getGraph().getNodes().size() <= 5000);

        BellmanFordAlgorithm bFAlgo = new BellmanFordAlgorithm(this.inputData);
        ShortestPathSolution bFSolution = bFAlgo.run();

        assertSame(bFSolution.getStatus(), this.solution.getStatus());

        if (!this.solution.isFeasible()) return;

        assertEquals(bFSolution.getPath().getLength(), this.solution.getPath().getLength(), 0.01);
        assertSame(bFSolution.isFeasible(), this.solution.isFeasible());

        if (!this.solution.isFeasible()) {
            return;
        }

        Path solutionPath = this.solution.getPath();
        Path bFSolutionPath = bFSolution.getPath();

        assertSame(solutionPath.getArcs().size(), bFSolutionPath.getArcs().size());

        for (int i = 0; i < solutionPath.getArcs().size(); i++) {
            assertSame(solutionPath.getArcs().get(i).getDestination(), bFSolutionPath.getArcs().get(i).getDestination());
        }
    }

    @Test
    public void pathValidity() {
        Assume.assumeTrue(this.solution.isFeasible());

        assertTrue(this.solution.getPath().isValid());
    }

    //public void costEqualsPath () {
        //Assume.assumeTrue(this.solution.isFeasible());
        //AbstractInputData.Mode mode = this.inputData.getMode(); // TIME ou LENGTH
        //double Cost ;
    //}



}