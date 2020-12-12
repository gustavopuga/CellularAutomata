package br.edu.sp.mackenzie.ppgeec.ca.util;

import br.edu.sp.mackenzie.ppgeec.ca.state.Probabilities;

public class KCalculator {

	public static double getK1(double n) {
		
//		double value = 15/(200*n);
//		return calculateK(n, value);
		return Probabilities.K1;
	}
	
	public static double getK2(double N) {
		
//		double value = 2.25/(14*N);
//		return calculateK(N, value);
		return Probabilities.K2;
	}
	
	public static double getK3(double N) {
		
//		double value = 15/(10*N);
//		return calculateK(N, value);
		return Probabilities.K3;
	}
	
	public static double getK4(double N) {
		
//		double value = 2.25/(14*N);
//		return 1.2*calculateK(N, value);	
		return Probabilities.K4;
	}

	public static double getK5(double N) {
		
//		double value = 2.25/(14*N);
//		return calculateK(N, value);
		return Probabilities.K5;
	}
	
	private static double calculateK(double n, double value) {
		return (-1/n) * Math.log(1 - value);
	}

}
