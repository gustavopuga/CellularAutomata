package br.edu.mackenzie;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public enum PopulationState implements Comparable<PopulationState>{

    
    SUSCEPTIBLE(0, "Suscetível", 0.6), INFECTIOUS(1, "Infectado", 0.3), RECOVERY(2, "Recuperado", 0.1);

    private final static double K = 1;
    private final Map<PopulationState, List<PopulationState>> graph = new HashMap<>();
    
    private final int value;
    private final String description;
    private Double probability;
    
    private PopulationState(int value, String description, double probability) {
	this.value = value;
	this.description = description;
	this.probability = probability;
	initGraph();
    }

    private void initGraph() {
	
	List<PopulationState> susceptibleVertex = new ArrayList<PopulationState>();
	susceptibleVertex.add(INFECTIOUS);
	graph.put(SUSCEPTIBLE, orderVertex(susceptibleVertex));
	
	List<PopulationState> infectiousVertex = new ArrayList<PopulationState>();
	infectiousVertex.add(RECOVERY);
	infectiousVertex.add(SUSCEPTIBLE);
	graph.put(INFECTIOUS, orderVertex(infectiousVertex));
	
	List<PopulationState> recoveryVertex = new ArrayList<PopulationState>();
	recoveryVertex.add(SUSCEPTIBLE);
	graph.put(RECOVERY, orderVertex(recoveryVertex));
    }
    
    private List<PopulationState> orderVertex(List<PopulationState> vertex) {
	
	Comparator<PopulationState> probabilityComparator = (state1, state2) -> Double.compare(state2 == null ? -1 : state2.probability, state1 == null ? -1 : state1.probability);
	return vertex.stream().sorted(probabilityComparator).collect(Collectors.toList());
    }
    
    public int getValue() {
	return value;
    }

    public String getDescription() {
	return description;
    }

    public PopulationState applyRule(int neighborhood) {

	double probability = 1 - Math.pow(Math.E, -1 * K * neighborhood);

	Optional<PopulationState> newState = Optional.of(this);
	List<PopulationState> possibleStates = this.graph.get(this);
	if (possibleStates != null) {
	    newState = possibleStates.stream().filter(Objects::nonNull).filter(state -> probability > state.probability).findFirst();
	}
	return newState.orElse(this);
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
