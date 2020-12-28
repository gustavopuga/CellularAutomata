package br.edu.sp.mackenzie.ppgeec;

public enum VacinationPercentual {

	ZERO(0), FIVE(0.05), TEN(0.1), FIFTEEN(0.15), TWENTY(0.2), TWENTY_FIVE(0.25), THIRTY(0.3);

	private final double value;

	private VacinationPercentual(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

}