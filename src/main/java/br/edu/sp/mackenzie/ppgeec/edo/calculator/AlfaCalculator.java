package br.edu.sp.mackenzie.ppgeec.edo.calculator;

import java.util.ArrayList;
import java.util.List;

import br.edu.sp.mackenzie.ppgeec.Constants;
import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;
import br.edu.sp.mackenzie.ppgeec.ca.state.TwoDiasesState;

public class AlfaCalculator {

	public static double getA1(List<CellularAutomataState[][]> generations, int lastGenerations) {
		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double s = 0;
			double i1 = 0;
			double i12 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiasesState.S.equals(statesTime1[j][k]) && TwoDiasesState.I1.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.S.equals(statesTime2[j][k])) {
						s++;
					}

					if (TwoDiasesState.I1.equals(statesTime2[j][k])) {
						i1++;
					}

					if (TwoDiasesState.I12.equals(statesTime2[j][k])) {
						i12++;
					}
				}
			}

			
			double d = (i1 + i12) * s;
			double a1 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(a1);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() * Constants.ROWS * Constants.COLUMNS;
	}

	public static double getA2(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double s = 0;
			double i = 0;
			double i2 = 0;
			double i21 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiasesState.S.equals(statesTime1[j][k]) && TwoDiasesState.I2.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.S.equals(statesTime2[j][k])) {
						s++;
					}

					if (TwoDiasesState.I.equals(statesTime2[j][k])) {
						i++;
					}

					if (TwoDiasesState.I2.equals(statesTime2[j][k])) {
						i2++;
					}
					if (TwoDiasesState.I21.equals(statesTime2[j][k])) {
						i21++;
					}
				}
			}

			double d = (i + i21 + i2) * s;
			double a2 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(a2);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() * Constants.ROWS * Constants.COLUMNS;

	}

	public static double getAlfa(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double v = 0;
			double i = 0;
			double i2 = 0;
			double i21 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiasesState.V.equals(statesTime1[j][k]) && TwoDiasesState.I.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.V.equals(statesTime2[j][k])) {
						v++;
					}

					if (TwoDiasesState.I.equals(statesTime2[j][k])) {
						i++;
					}

					if (TwoDiasesState.I2.equals(statesTime2[j][k])) {
						i2++;
					}
					if (TwoDiasesState.I21.equals(statesTime2[j][k])) {
						i21++;
					}
				}
			}

			double d = (i + i21 + i2) * v;
			double alfa = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(alfa);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() * Constants.ROWS * Constants.COLUMNS;

	}

	public static double getAlfa1(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double r2 = 0;
			double i1 = 0;
			double i12 = 0;
			double i21 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiasesState.R2.equals(statesTime1[j][k]) && TwoDiasesState.I12.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.R2.equals(statesTime2[j][k])) {
						r2++;
					}

					if (TwoDiasesState.I1.equals(statesTime2[j][k])) {
						i1++;
					}

					if (TwoDiasesState.I12.equals(statesTime2[j][k])) {
						i12++;
					}

				}
			}

			double d = (i1 + i12) * r2;
			double alfa1 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(alfa1);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() * Constants.ROWS * Constants.COLUMNS;

	}

	public static double getAlfa2(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double r1 = 0;
			double i = 0;
			double i2 = 0;
			double i21 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiasesState.R1.equals(statesTime1[j][k]) && TwoDiasesState.I21.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.R1.equals(statesTime2[j][k])) {
						r1++;
					}

					if (TwoDiasesState.I.equals(statesTime2[j][k])) {
						i++;
					}

					if (TwoDiasesState.I2.equals(statesTime2[j][k])) {
						i2++;
					}
					if (TwoDiasesState.I21.equals(statesTime2[j][k])) {
						i21++;
					}
				}
			}

			double d = (i + i21 + i2) * r1;
			double alfa2 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(alfa2);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() * Constants.ROWS * Constants.COLUMNS;
	}

}
