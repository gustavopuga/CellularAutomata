package br.edu.sp.mackenzie.ppgeec.edo.calculator;

import java.util.ArrayList;
import java.util.List;

import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;
import br.edu.sp.mackenzie.ppgeec.ca.state.TwoDiasesState;

public class GammaCalculator {

	public static double getC1(List<CellularAutomataState[][]> generations, int lastGenerations) {
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
					if (TwoDiasesState.I1.equals(statesTime1[j][k]) && TwoDiasesState.S.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.I1.equals(statesTime2[j][k])) {
						i1++;
					}

				}
			}

			double d = i1;
			double c1 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(c1);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;
	}

	public static double getC2(List<CellularAutomataState[][]> generations, int lastGenerations) {

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
					if (TwoDiasesState.I2.equals(statesTime1[j][k]) && TwoDiasesState.S.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.I2.equals(statesTime2[j][k])) {
						i2++;
					}
				}
			}

			double d = i2;
			double c2 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(c2);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;

	}

	public static double getGamma(List<CellularAutomataState[][]> generations, int lastGenerations) {

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
					if (TwoDiasesState.I.equals(statesTime1[j][k]) && TwoDiasesState.S.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.I.equals(statesTime2[j][k])) {
						i++;
					}

				}
			}

			double d = i;
			double gamma = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(gamma);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;

	}

	public static double getGamma1(List<CellularAutomataState[][]> generations, int lastGenerations) {

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
					if (TwoDiasesState.I12.equals(statesTime1[j][k]) && TwoDiasesState.S.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.I12.equals(statesTime2[j][k])) {
						i12++;
					}

				}
			}

			double d = i12;
			double gamma1 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(gamma1);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;

	}

	public static double getGamma2(List<CellularAutomataState[][]> generations, int lastGenerations) {

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
					if (TwoDiasesState.I21.equals(statesTime1[j][k]) && TwoDiasesState.S.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.I21.equals(statesTime2[j][k])) {
						i21++;
					}
				}
			}

			double d = i21;
			double gamma2 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(gamma2);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;
	}

}
