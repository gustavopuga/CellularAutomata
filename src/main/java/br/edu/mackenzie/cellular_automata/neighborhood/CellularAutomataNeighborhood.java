package br.edu.mackenzie.cellular_automata.neighborhood;

import br.edu.mackenzie.cellular_automata.state.CellularAutomataState;

public interface CellularAutomataNeighborhood {

    public abstract CellularAutomataState[][] goToNextGeneration(CellularAutomataState[][] currentGeneration);

}
