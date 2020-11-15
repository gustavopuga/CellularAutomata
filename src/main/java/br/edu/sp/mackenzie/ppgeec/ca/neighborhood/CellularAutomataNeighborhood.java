package br.edu.sp.mackenzie.ppgeec.ca.neighborhood;

import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;

public interface CellularAutomataNeighborhood {

    public abstract CellularAutomataState[][] goToNextGeneration(CellularAutomataState[][] currentGeneration);

}
