package br.edu.sp.mackenzie.ppgeec.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CSVUtils {

	public static void createCSVFile(String[] headers, List<CSVChart1> values, String fileName) throws IOException {
		FileWriter out = new FileWriter(fileName + ".csv");
		try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers))) {
			for (CSVChart1 csv : values) {
				printer.printRecord(csv.getNome(), csv.getPopulacao(), csv.getTempo());
				printer.flush();
			}
		}
	}

	public static void createCSVFile(String[] headers, Map<Long, List<Double>> map, String fileName) throws IOException {
		FileWriter out = new FileWriter(fileName + ".csv");
		try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers))) {
			for (Long key : map.keySet()) {
				List<Number> record = new ArrayList<>();
				record.add(key);
				record.addAll(map.get(key));
				printer.printRecord(record);
				printer.flush();
			}
		}
	}
}
