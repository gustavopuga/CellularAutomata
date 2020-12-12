package br.edu.sp.mackenzie.ppgeec;

import java.util.ArrayList;
import java.util.List;

import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;
import br.edu.sp.mackenzie.ppgeec.ca.state.TwoDiasesState;

public class EpslonCalculator {

	public static double getE1(List<CellularAutomataState[][]> generations, int lastGenerations) {
		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double i1 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiasesState.R1.equals(statesTime1[j][k]) && TwoDiasesState.S.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.R1.equals(statesTime2[j][k])) {
						i1++;
					}

				}
			}

			double d = i1;
			double a1 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(a1);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;
	}

	public static double getE2(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double i2 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiasesState.R2.equals(statesTime1[j][k]) && TwoDiasesState.S.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.R2.equals(statesTime2[j][k])) {
						i2++;
					}
				}
			}

			double d = i2;
			double a2 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(a2);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;

	}

	public static double getE(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double i = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiasesState.V.equals(statesTime1[j][k]) && TwoDiasesState.S.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.V.equals(statesTime2[j][k])) {
						i++;
					}

				}
			}

			double d = i;
			double alfa = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(alfa);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;

	}

	public static double getEpslon(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double i12 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiasesState.R.equals(statesTime1[j][k]) && TwoDiasesState.S.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.R.equals(statesTime2[j][k])) {
						i12++;
					}

				}
			}

			double d = i12;
			double alfa1 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(alfa1);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;

	}

	public static double getUpslon(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double i21 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiasesState.S.equals(statesTime1[j][k]) && TwoDiasesState.V.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.S.equals(statesTime2[j][k])) {
						i21++;
					}
				}
			}

			double d = i21;
			double alfa2 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(alfa2);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;
	}

}