package br.edu.mackenzie;

public enum PopulationState {

    SUSCEPTIBLE(0, "Suscetível"), INFECTIOUS(1, "Infectado"), RECOVERY(2, "Recuperado");

    private final static double K = 1;
    private final static double RECOVERY_PROBABILITY = 0.6;
    private final static double INFECTIOUS_PROBABILITY = 0.3;
    private final static double SUSCEPTIBLE_PROBABILITY = 0.1;

    private final int value;
    private final String description;

    private PopulationState(int value, String description) {
	this.value = value;
	this.description = description;
    }

    public int getValue() {
	return value;
    }

    public String getDescription() {
	return description;
    }

    public PopulationState applyRule(int neighborhood) {

	PopulationState newState = this;
	double probability = 1 - Math.pow(Math.E, -K * neighborhood);

	switch (this) {

	case SUSCEPTIBLE:
	    if (probability < INFECTIOUS_PROBABILITY) {
		newState = INFECTIOUS;
	    }
	    break;

	case INFECTIOUS:
	    if (probability < RECOVERY_PROBABILITY) {
		newState = RECOVERY;
	    }
	    
	    if (probability < SUSCEPTIBLE_PROBABILITY) {
		newState = SUSCEPTIBLE;
	    }
	    break;

	case RECOVERY:
	    if (probability < SUSCEPTIBLE_PROBABILITY) {
		newState = SUSCEPTIBLE;
	    }
	    
	    if (probability < SUSCEPTIBLE_PROBABILITY) {
		newState = SUSCEPTIBLE;
	    }
	    break;

	}

	return newState;
    }

    public static PopulationState get(int value) {
	for (PopulationState populationState : values()) {
	    if (populationState.value == value) {
		return populationState;
	    }
	}
	return null;
    }
}
