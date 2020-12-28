package br.edu.sp.mackenzie.ppgeec.edo.calculator;

import java.util.ArrayList;
import java.util.List;

import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;
import br.edu.sp.mackenzie.ppgeec.ca.state.TwoDiasesState;

public class EpsilonCalculator {

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
			double r1 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiasesState.R1.equals(statesTime1[j][k]) && TwoDiasesState.S.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.R1.equals(statesTime2[j][k])) {
						r1++;
					}

				}
			}

			double d = r1;
			double e1 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(e1);

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
			double r2 = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiasesState.R2.equals(statesTime1[j][k]) && TwoDiasesState.S.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.R2.equals(statesTime2[j][k])) {
						r2++;
					}
				}
			}

			double d = r2;
			double e2 = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(e2);

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
			double v = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiasesState.V.equals(statesTime1[j][k]) && TwoDiasesState.S.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.V.equals(statesTime2[j][k])) {
						v++;
					}

				}
			}

			double d = v;
			double e = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(e);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;

	}

	public static double getEpsilon(List<CellularAutomataState[][]> generations, int lastGenerations) {

		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;
			double r = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					if (TwoDiasesState.R.equals(statesTime1[j][k]) && TwoDiasesState.S.equals(statesTime2[j][k])) {
						delta++;
					}

					if (TwoDiasesState.R.equals(statesTime2[j][k])) {
						r++;
					}

				}
			}

			double d = r;
			double epslon = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(epslon);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;

	}

	public static double getUpsilon(List<CellularAutomataState[][]> generations, int lastGenerations) {

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

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					CellularAutomataState stateTime1 = statesTime1[j][k];
					CellularAutomataState stateTime2 = statesTime2[j][k];

					if (TwoDiasesState.S.equals(stateTime1) && TwoDiasesState.V.equals(stateTime2)) {
						delta++;
					}

					if (TwoDiasesState.S.equals(stateTime2)) {
						s++;
					}
				}
			}
			
			double d = s;
			double upslon = (delta == 0 && d == 0) ? 0 : (delta / d);
			values.add(upslon);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() ;
	}

}
