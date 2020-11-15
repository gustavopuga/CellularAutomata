package br.edu.sp.mackenzie.ppgeec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;
import br.edu.sp.mackenzie.ppgeec.ca.state.PopulationState;

public class CellularAutomataTeste {

    private CellularAutomataState[][] currentGeneration;
    private List<CellularAutomataState[][]> generations;

    private int columns;
    private int rows;
    private int population;

    public CellularAutomataTeste(int columns, int rows) {

	this.generations = new ArrayList<>();
	this.currentGeneration = new PopulationState[columns][rows];
	this.columns = columns;
	this.rows = rows;
	this.population = columns * rows;
	init();
    }

    private void init() {

	Map<CellularAutomataState, Double> populationStateQuantity = new HashMap<>();
	for (PopulationState populationState : PopulationState.values()) {
	    Double populationPercentage = populationState.getPercentage();
	    Double maxPopulationState = null;
	    if (populationPercentage != null) {
		maxPopulationState = populationPercentage * population;
	    }
	    populationStateQuantity.put(populationState, maxPopulationState);
	}

	for (int i = 0; i < columns; i++) {
	    for (int j = 0; j < rows; j++) {

		int statesQuantity = PopulationState.values().length;
		CellularAutomataState populationState = PopulationState.get((int) Math.floor((Math.random() * statesQuantity)));
		Double quantity = populationStateQuantity.get(populationState);
		if (quantity != null) {
		    while (quantity <= 0) {
			populationState = PopulationState.get((int) Math.floor((Math.random() * statesQuantity)));
			quantity = populationStateQuantity.get(populationState);
		    }
		    populationStateQuantity.put(populationState, --quantity);
		}
		currentGeneration[i][j] = populationState;
	    }
	}
    }

    private void nextGeneration() {

	CellularAutomataState[][] nextGeneration = new PopulationState[columns][rows];
	this.generations.add(this.currentGeneration);

	for (int x = 0; x < columns; x++) {
	    for (int y = 0; y < rows; y++) {

		List<CellularAutomataState> neighborhood = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
		    for (int j = -1; j <= 1; j++) {
			int xNeighborhood = (x + i + this.columns) % this.columns;
			int yNeighborhood = (y + j + this.rows) % this.rows;
			if (x != xNeighborhood && yNeighborhood != y) {
			    neighborhood.add(this.currentGeneration[xNeighborhood][yNeighborhood]);
			}
		    }
		}

		CellularAutomataState cell = currentGeneration[x][y];
		nextGeneration[x][y] = cell.applyRule(neighborhood);

	    }
	}

	currentGeneration = nextGeneration;
    }

    public void nextGeneration(int time) {

	for (int i = 0; i < time; i++) {
	    nextGeneration();
	}
    }

    public Map<CellularAutomataState, List<Double>> generateGenerationsStateMap() {

	Map<CellularAutomataState, List<Double>> map = new HashMap<>();
	for (PopulationState populationState : PopulationState.values()) {
	    map.put(populationState, new ArrayList<>());
	}

	for (CellularAutomataState[][] populationStates : generations) {

	    Map<CellularAutomataState, Double> mapCount = new HashMap<>();
	    for (CellularAutomataState cellularAutomataState : PopulationState.values()) {
		mapCount.put(cellularAutomataState, 0d);
	    }

	    for (int x = 0; x < columns; x++) {
		for (int y = 0; y < rows; y++) {
		    CellularAutomataState state = populationStates[x][y];
		    Double count = mapCount.get(state);
		    mapCount.put(state, ++count);
		}
	    }

	    for (CellularAutomataState populationState : PopulationState.values()) {
		map.get(populationState).add(mapCount.get(populationState));
	    }

	}

	return map;
    }
}
