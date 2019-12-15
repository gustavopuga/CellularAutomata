package br.edu.mackenzie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CellularAutomata {

	private PopulationState[][] currentGeneration;
	private List<PopulationState[][]> generations;

	private int columns;
	private int rows;
	private int population;

	public CellularAutomata(int columns, int rows) {

		this.generations = new ArrayList<>();
		this.currentGeneration = new PopulationState[columns][rows];
		this.columns = columns;
		this.rows = rows;
		this.population = columns * rows;
		init();
	}

	private void init() {

		Map<PopulationState, Double> populationStateQuantity = new HashMap<>();
		for (PopulationState populationState : PopulationState.values()) {
			Double populationPercentage = populationState.getPopulationPercentage();
			Double maxPopulationState = null;
			if (populationPercentage != null) {
				maxPopulationState = populationPercentage * population;
			}
			populationStateQuantity.put(populationState, maxPopulationState);
		}

		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {

				PopulationState populationState = PopulationState.get((int) Math.floor((Math.random() * 3)));
				Double quantity = populationStateQuantity.get(populationState);
				if (quantity != null) {
					while (quantity <= 0) {
						populationState = PopulationState.get((int) Math.floor((Math.random() * 3)));
						quantity = populationStateQuantity.get(populationState);
					}
					populationStateQuantity.put(populationState, --quantity);
				}
				currentGeneration[i][j] = populationState;
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
						neighborhood += this.currentGeneration[(x + i + this.columns)
								% this.columns][(y + j + this.rows) % this.rows].getValue();
					}
				}

				// Subtract the current cell's state since
				PopulationState cell = currentGeneration[x][y];
				neighborhood -= cell.getValue();
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

	public Map<PopulationState, List<Double>> generateGenerationsStateMap() {

		Map<PopulationState, List<Double>> map = new HashMap<>();
		map.put(PopulationState.SUSCEPTIBLE, new ArrayList<>());
		map.put(PopulationState.INFECTIOUS, new ArrayList<>());
		map.put(PopulationState.RECOVERY, new ArrayList<>());

		for (PopulationState[][] populationStates : generations) {

			double susceptible = 0;
			double infectious = 0;
			double recovery = 0;

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
