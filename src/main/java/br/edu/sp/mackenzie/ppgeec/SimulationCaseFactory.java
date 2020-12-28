package br.edu.sp.mackenzie.ppgeec;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SimulationCaseFactory {

	public static Map<VacinationPercentual, List<SimulationCase>> createCases() {

		double ks = 1d;
		double epsilons = 0.1d;
		double betas = 0.5d;
		double gammas = 0.2d;
		
		double[] beta2s = { 0.5d, 1d/3d, 0.25d, 0.2d };
		double[] gamma2s = { 0.2d, 0.3d, 0.4d, 0.5d };

		
		Map<VacinationPercentual, List<SimulationCase>> caseMap = new LinkedHashMap<>();
		int index = 1;
		for (VacinationPercentual v : VacinationPercentual.values()) {
			List<SimulationCase> cases = new ArrayList<>();
			for (double gamma2 : gamma2s) {
				for (double beta2 : beta2s) {
					SimulationCase simulationCase = new SimulationCase();
					simulationCase.setName("Simulação " + index);
					simulationCase.setB1(betas);
					simulationCase.setB2(betas);
					simulationCase.setBeta(betas);
					simulationCase.setBeta1(betas);
					simulationCase.setBeta2(beta2);

					simulationCase.setC1(gammas);
					simulationCase.setC2(gammas);
					simulationCase.setGamma(gammas);
					simulationCase.setGamma1(gammas);
					simulationCase.setGamma2(gamma2);

					simulationCase.setE(epsilons);
					simulationCase.setE1(epsilons);
					simulationCase.setE2(epsilons);
					simulationCase.setEpsilon(epsilons);

					simulationCase.setK1(ks);
					simulationCase.setK2(ks);
					simulationCase.setK3(ks);
					simulationCase.setK4(ks);
					simulationCase.setK5(ks);

					simulationCase.setV(v.getValue());

					cases.add(simulationCase);
					index++;
				}
				caseMap.put(v, cases);
			}
		}
		return caseMap;
	}

}
