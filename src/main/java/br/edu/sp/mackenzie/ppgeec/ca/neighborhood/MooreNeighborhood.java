package br.edu.sp.mackenzie.ppgeec.ca.neighborhood;

import java.util.ArrayList;
import java.util.List;

import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;

public class MooreNeighborhood implements CellularAutomataNeighborhood {

	private int radius;

	public MooreNeighborhood(int radius) {
		this.radius = radius;
	}

	public CellularAutomataState[][] goToNextGeneration(CellularAutomataState[][] currentGeneration) {

		int columns = currentGeneration.length;
		int rows = currentGeneration[0].length;

		CellularAutomataState[][] nextGeneration = new CellularAutomataState[columns][rows];

		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {

				List<CellularAutomataState> neighborhood = new ArrayList<>();
				for (int i = -radius; i <= radius; i++) {
					for (int j = -radius; j <= radius; j++) {
						int xNeighborhood = (x + i + columns) % columns;
						int yNeighborhood = (y + j + rows) % rows;
						if (x != xNeighborhood && yNeighborhood != y) {
							neighborhood.add(currentGeneration[xNeighborhood][yNeighborhood]);
						}
					}
				}

				CellularAutomataState cell = currentGeneration[x][y];
				nextGeneration[x][y] = cell.applyRule(neighborhood);

			}
		}

		return nextGeneration;
	}

}
