import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class RainfallAnalyser {
//    public static final int INDEX_YEAR = 2;
    public static final int INDEX_MONTH = 3;
    public static final int INDEX_DAY = 4;
    public static final int INDEX_RAINFALL = 5;

    public static void main(String[] args) {

        HashMap<String, Double> monthHashMap = new HashMap<>();
        HashMap<String, Double> dayMinHasHMap = new HashMap<>();
        HashMap<String, Double> dayMaxHashMap = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(
                    "C:\\Users\\bensk\\IdeaProjects\\RainfallAnalyser\\src\\MountSheridanStationCNS.csv");
            CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
            List<String[]> fileContents = csvReader.readAll();

            for (String[] row : fileContents) {
                System.out.println(Arrays.toString(row));
                insertMonths(monthHashMap, row);
                insertDay(dayMinHasHMap, row, true);
                insertDay(dayMaxHashMap, row, false);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        System.out.println(monthHashMap);
        System.out.println(dayMinHasHMap);
        System.out.println(dayMaxHashMap);

        String pathToFile = "C:\\Users\\bensk\\IdeaProjects\\RainfallAnalyser\\src\\MonthHashMap.csv";
        String[] header = {"Month", "Total Rainfall"};
        writeToCSV(monthHashMap, pathToFile, header);

        pathToFile = "C:\\Users\\bensk\\IdeaProjects\\RainfallAnalyser\\src\\MinDayHashMap.csv";
        header = new String[]{"Month-day", "Min Rainfall"};
        writeToCSV(dayMinHasHMap, pathToFile, header);

        pathToFile = "C:\\Users\\bensk\\IdeaProjects\\RainfallAnalyser\\src\\MaxDayHashMap.csv";
        header = new String[]{"Month-day", "Max Rainfall"};
        writeToCSV(dayMaxHashMap, pathToFile, header);
    }

    private static void writeToCSV(HashMap<String, Double> monthHashMap, String pathToFile, String[] header) {
        try {
            FileWriter writer = new FileWriter(pathToFile);
            CSVWriter  csvWriter = new CSVWriter(writer);
            csvWriter.writeNext(header);
            for (Map.Entry<String, Double> entry : monthHashMap.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                String[] row = {key, value.toString()};
                csvWriter.writeNext(row);
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void insertDay(HashMap<String, Double> monthMap, String[] row, boolean isMin) {
        String monthString = row[INDEX_MONTH];
        String rainfallString = row[INDEX_RAINFALL];
        String dayString = row[INDEX_DAY];
        String keyString = monthString + "-day-" + dayString;
        double rain = 0;
        try {
            rain = Double.parseDouble(rainfallString);
        } catch (Exception ignored) {}

        if (monthMap.containsKey(keyString)) {
            double oldRainfall = monthMap.get(keyString);
            double newRainfall;
            if (isMin) {
                newRainfall = Math.min(oldRainfall, rain);
            } else {
                newRainfall = Math.max(oldRainfall, rain);
            }
            monthMap.put(keyString, newRainfall);
        } else {
            monthMap.put(keyString, rain);
        }

    }

    private static void insertMonths(HashMap<String, Double> monthMap, String[] row) {
        String monthString = row[INDEX_MONTH];
        String rainfallString = row[INDEX_RAINFALL];
        String keyString = "month- " + monthString + " rainfall " ;
        double rain = 0;

        try {
            rain = Double.parseDouble(rainfallString);
        } catch (Exception ignored) {
        }

        if (monthMap.containsKey(keyString)) {
            double oldRain = monthMap.get(keyString);
            double newRain = oldRain + rain;
            monthMap.put(keyString, newRain);
        } else {
            monthMap.put(keyString, rain);
        }
    }

}
