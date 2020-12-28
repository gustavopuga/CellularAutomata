package br.edu.sp.mackenzie.ppgeec.edo.calculator;

import java.util.ArrayList;
import java.util.List;

import br.edu.sp.mackenzie.ppgeec.Constants;
import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;
import br.edu.sp.mackenzie.ppgeec.ca.state.TwoDiasesState;

public class VariablesCalculator {

	public static double average(List<CellularAutomataState[][]> generations, int lastGenerations, TwoDiasesState state) {
		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
	
			long c = 0;
			double n = 0;
			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					c++;
					if (state.equals(statesTime1[j][k])) {
						n++;
					}
				}
			}

			values.add(n/Constants.NORMALIZE);
		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble();
	}

}
