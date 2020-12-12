package br.edu.sp.mackenzie.ppgeec.edo.calculator;

import java.util.ArrayList;
import java.util.List;

import br.edu.sp.mackenzie.ppgeec.Constants;
import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;
import br.edu.sp.mackenzie.ppgeec.ca.state.TwoDiasesState;

public class XCalculator {

	public static double getX(List<CellularAutomataState[][]> generations, int lastGenerations) {
		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);

			double i1 = 0;
			double i12 = 0;
			double i2 = 0;
			double i21 = 0;
			double i = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {

					if (TwoDiasesState.I1.equals(statesTime1[j][k])) {
						i1++;
					}

					if (TwoDiasesState.I12.equals(statesTime1[j][k])) {
						i12++;
					}
					if (TwoDiasesState.I2.equals(statesTime1[j][k])) {
						i2++;
					}
					
					if (TwoDiasesState.I21.equals(statesTime1[j][k])) {
						i21++;
					}
					if (TwoDiasesState.I.equals(statesTime1[j][k])) {
						i++;
					}
					
				}
			}

			double delta = i1 + i12;
			double d = i1 + i12 + i2 + i21 + i;
			double x = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(x);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble();
	}

	public static double get1MinusX(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);

			double i1 = 0;
			double i12 = 0;
			double i2 = 0;
			double i21 = 0;
			double i = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {

					if (TwoDiasesState.I1.equals(statesTime1[j][k])) {
						i1++;
					}

					if (TwoDiasesState.I12.equals(statesTime1[j][k])) {
						i12++;
					}
					if (TwoDiasesState.I2.equals(statesTime1[j][k])) {
						i2++;
					}
					
					if (TwoDiasesState.I21.equals(statesTime1[j][k])) {
						i21++;
					}
					if (TwoDiasesState.I.equals(statesTime1[j][k])) {
						i++;
					}
					
				}
			}

			double delta = i2 + i21 + i;
			double d = i1 + i12 + i2 + i21 + i;
			double oneMinusX = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(oneMinusX);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble();

	}

}
