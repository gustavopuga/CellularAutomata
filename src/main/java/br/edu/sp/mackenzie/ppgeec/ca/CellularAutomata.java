package br.edu.sp.mackenzie.ppgeec.ca;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.sp.mackenzie.ppgeec.ca.neighborhood.CellularAutomataNeighborhood;
import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;

public class CellularAutomata {

	private CellularAutomataState[][] currentGeneration;
	private List<CellularAutomataState[][]> generations;

	private int columns;
	private int rows;
	private int population;
	private EnumSet<? extends CellularAutomataState> values;
	private CellularAutomataNeighborhood neighborhood;

	public <S extends Enum<S> & CellularAutomataState> CellularAutomata(int columns, int rows,
			CellularAutomataNeighborhood neighborhood, Class<S> clazz) {

		this.neighborhood = neighborhood;
		this.generations = new ArrayList<>();
		this.values = EnumSet.allOf(clazz);
		this.currentGeneration = new CellularAutomataState[columns][rows];
		this.columns = columns;
		this.rows = rows;
		this.population = columns * rows;
		init();
	}

	private void init() {

		Map<CellularAutomataState, Double> stateQuantity = new HashMap<>();
		Map<Integer, CellularAutomataState> valueMap = new HashMap<>();

		for (CellularAutomataState state : this.values) {
			Double statePercentage = state.getPercentage();
			Double maxPopulationState = null;
			if (statePercentage != null) {
				maxPopulationState = statePercentage * population;
			}
			stateQuantity.put(state, maxPopulationState);
			valueMap.put(state.getValue(), state);
		}

		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {

				int statesQuantity = this.values.size();
				int randomValue = (int) Math.floor((Math.random() * statesQuantity));

				CellularAutomataState state = valueMap.get(randomValue);
				Double quantity = stateQuantity.get(state);

				if (quantity != null) {
					while (quantity <= 0) {
						randomValue = (int) Math.floor((Math.random() * statesQuantity));
						state = valueMap.get(randomValue);
						quantity = stateQuantity.get(state);
					}
					stateQuantity.put(state, --quantity);
				}

				currentGeneration[i][j] = state;
			}
		}
	}

	private void nextGeneration() {

		this.getGenerations().add(this.currentGeneration);
		currentGeneration = neighborhood.goToNextGeneration(currentGeneration);
	}

	public void nextGeneration(int time) {

		for (int i = 0; i < time; i++) {
			nextGeneration();
		}
	}

	public Map<CellularAutomataState, List<Double>> generateGenerationsStateMap() {

		Map<CellularAutomataState, List<Double>> map = new HashMap<>();
		for (CellularAutomataState cellularAutomataState : this.values) {
			map.put(cellularAutomataState, new ArrayList<>());
		}

		for (CellularAutomataState[][] cellularAutomataStates : getGenerations()) {

			Map<CellularAutomataState, Double> mapCount = new HashMap<>();
			for (CellularAutomataState cellularAutomataState : this.values) {
				mapCount.put(cellularAutomataState, 0d);
			}

			for (int x = 0; x < columns; x++) {
				for (int y = 0; y < rows; y++) {
					CellularAutomataState state = cellularAutomataStates[x][y];
					Double count = mapCount.getOrDefault(state, 0d);
					mapCount.put(state, ++count);
				}
			}

			for (CellularAutomataState cellularAutomataState : this.values) {
				map.get(cellularAutomataState).add(mapCount.get(cellularAutomataState));
			}

		}

		return map;
	}

	public List<CellularAutomataState[][]> getGenerations() {
		return generations;
	}
	
}
