package br.edu.sp.mackenzie.ppgeec.edo.calculator;

import java.util.ArrayList;
import java.util.List;

import br.edu.sp.mackenzie.ppgeec.Constants;
import br.edu.sp.mackenzie.ppgeec.ca.state.CellularAutomataState;
import br.edu.sp.mackenzie.ppgeec.ca.state.TwoDiaseState;

public class YCalculator {

	public static double getMorteDoenca1(List<CellularAutomataState[][]> generations, int lastGenerations) {
		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					CellularAutomataState stateTime1 = statesTime1[j][k];
					CellularAutomataState stateTime2 = statesTime2[j][k];

					if ((TwoDiaseState.I1.equals(stateTime1) || TwoDiaseState.I12.equals(stateTime1))
							&& TwoDiaseState.S.equals(stateTime2)) {
						delta++;
					}
				}
			}

			values.add(delta);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() / Constants.NORMALIZE;
	}

	public static double getMorteDoenca2(List<CellularAutomataState[][]> generations, int lastGenerations) {
		int first = generations.size() - lastGenerations - 2;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			CellularAutomataState[][] statesTime2 = generations.get(count + 1);

			double delta = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					CellularAutomataState stateTime1 = statesTime1[j][k];
					CellularAutomataState stateTime2 = statesTime2[j][k];

					if ((TwoDiaseState.I2.equals(stateTime1) || TwoDiaseState.I21.equals(stateTime1)
							|| TwoDiaseState.I.equals(stateTime1)) && TwoDiaseState.S.equals(stateTime2)) {
						delta++;
					}
				}
			}

			values.add(delta);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() / Constants.NORMALIZE;
	}

	public static double getInfectados1(List<CellularAutomataState[][]> generations, int lastGenerations) {
		int first = generations.size() - lastGenerations - 1;
		if (first < 0) {
			first = 0;
		}

		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);

			double delta = 0;

			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					CellularAutomataState stateTime1 = statesTime1[j][k];

					if ((TwoDiaseState.I1.equals(stateTime1) || TwoDiaseState.I12.equals(stateTime1))) {
						delta++;
					}
				}
			}

			values.add(delta);

		}

		return values.stream().mapToDouble(a -> a).average().getAsDouble() / Constants.NORMALIZE;
	}

	public static double getInfectados2(List<CellularAutomataState[][]> generations, int lastGenerations) {
		int first = generations.size() - lastGenerations - 1;
		if (first < 0) {
			first = 0;
		}
		
		List<Double> values = new ArrayList<>();
		for (int count = first; count < (generations.size() - 1); count++) {
			CellularAutomataState[][] statesTime1 = generations.get(count);
			
			double delta = 0;
			
			for (int j = 0; j < statesTime1.length; j++) {
				for (int k = 0; k < statesTime1[j].length; k++) {
					CellularAutomataState stateTime1 = statesTime1[j][k];

					
					if ((TwoDiaseState.I2.equals(stateTime1) || TwoDiaseState.I21.equals(stateTime1) || TwoDiaseState.I.equals(stateTime1))) {
						delta++;
					}
				}
			}
			
			values.add(delta);
			
		}
		
		return values.stream().mapToDouble(a -> a).average().getAsDouble() / Constants.NORMALIZE;
	}
}
