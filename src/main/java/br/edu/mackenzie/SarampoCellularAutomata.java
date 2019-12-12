package br.edu.mackenzie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class SarampoCellularAutomata {

    private PopulationState[][] currentGeneration;
    private List<PopulationState[][]> generations;
    private int columns, rows;

    public SarampoCellularAutomata(int columns, int rows) {

	this.generations = new ArrayList<>();
	this.currentGeneration = new PopulationState[columns][rows];
	this.columns = columns;
	this.rows = rows;
	init();
    }

    private void init() {
	for (int i = 0; i < columns; i++) {
	    for (int j = 0; j < rows; j++) {
		currentGeneration[i][j] = PopulationState.get((int) Math.floor((Math.random() * 3)));
	    }
	}
    }

    private void nextGeneration() {

	PopulationState[][] nextGeneration = new PopulationState[columns][rows];
	this.generations.add(this.currentGeneration);

	// Loop through every spot in our 2D array and check spots neighbors
	for (int x = 0; x < columns; x++) {
	    for (int y = 0; y < rows; y++) {

		// Add up all the states in a 3x3 surrounding grid
		int neighborhood = 0;
		for (int i = -1; i <= 1; i++) {
		    for (int j = -1; j <= 1; j++) {
			neighborhood += this.currentGeneration[(x + i + this.columns) % this.columns][(y + j + this.rows) % this.rows]
				.getValue();
		    }
		}

		// A little trick to subtract the current cell's state since
		// we added it in the above loop
		neighborhood -= currentGeneration[x][y].getValue();
		// Rules
		nextGeneration[x][y] = currentGeneration[x][y].applyRule(neighborhood);

	    }
	}

	currentGeneration = nextGeneration;
    }

    public void nextGeneration(int time) {

	for (int i = 0; i < time; i++) {
	    nextGeneration();
	}
    }

    public Map<PopulationState, List<Integer>> generateMap() {

	Map<PopulationState, List<Integer>> map = new HashMap<>();
	map.put(PopulationState.SUSCEPTIBLE, new ArrayList<>());
	map.put(PopulationState.INFECTIOUS, new ArrayList<>());
	map.put(PopulationState.RECOVERY, new ArrayList<>());

	for (PopulationState[][] populationStates : generations) {
	    
	    int susceptible = 0;
	    int infectious = 0;
	    int recovery = 0;
	    
	    for (int x = 0; x < columns; x++) {
		for (int y = 0; y < rows; y++) {
		    PopulationState state = populationStates[x][y];
		    
		    switch (state) {
		    
		    case SUSCEPTIBLE:
			susceptible++;
			break;
		    case INFECTIOUS:
			infectious++;
			break;
		    case RECOVERY:
			recovery++;
			break;

		    }
		}
	    }

	    map.get(PopulationState.SUSCEPTIBLE).add(susceptible);
	    map.get(PopulationState.INFECTIOUS).add(infectious);
	    map.get(PopulationState.RECOVERY).add(recovery);
	    
	}

	return map;
    }
}
