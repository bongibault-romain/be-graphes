package org.insa.graphs.algorithm.confinedwalks;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Path;

public class ConfinedWalksSolution extends AbstractSolution {
    private final Path path;

    protected ConfinedWalksSolution(ConfinedWalksData data) {
        super(data);
        this.path = null;
    }

    protected ConfinedWalksSolution(ConfinedWalksData data, Status status) {
        super(data, status);
        this.path = null;
    }

    protected ConfinedWalksSolution(ConfinedWalksData data, Status status, Path path) {
        super(data, status);
        this.path = path;
    }

    @Override
    public ConfinedWalksData getInputData() {
        return (ConfinedWalksData) super.getInputData();
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        String info = null;
        if (!isFeasible()) {
            info = String.format("No path found from node #%d to node #%d",
                    getInputData().getCenter().getId(), getInputData().getCenter().getId());
        }
        else {
            double cost = 0;
            for (Arc arc: getPath().getArcs()) {
                cost += getInputData().getCost(arc);
            }
            info = String.format("Found a path from node #%d to node #%d",
                    getInputData().getCenter().getId(), getInputData().getCenter().getId());
            if (getInputData().getMode() == AbstractInputData.Mode.LENGTH) {
                info = String.format("%s, %.4f kilometers", info, cost / 1000.0);
            }
            else {
                info = String.format("%s, %.4f minutes", info, cost / 60.0);
            }
        }
        info += " in " + getSolvingTime().getSeconds() + " seconds.";
        return info;
    }
}
