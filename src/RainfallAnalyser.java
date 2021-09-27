import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class RainfallAnalyser {
    public static final int MONTH_INDEX = 3;
    public static final int DAY_INDEX = 4;
    public static final int RAIN_INDEX = 5;

    public static void main(String[] args) {

        List<String[]> fileValues = new ArrayList<>();
        HashMap<String, Double> monthMap = new HashMap<>();
        HashMap<String, Double> dayMinMap = new HashMap<>();
        HashMap<String, Double> dayMaxMap = new HashMap<>();

        try {
            FileReader fileReader = new FileReader(
            "C:\\Users\\bensk\\IdeaProjects\\RainfallAnalyser\\src\\MountSheridanStationCNS.csv");
            CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
            fileValues = csvReader.readAll();


            for (String[] row : fileValues) {
                System.out.println(Arrays.toString(row));
                collectMonths(monthMap, row);
//                collectMinDays(dayMinMap, row);
//                collectMaxDays(dayMaxMap, row);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        System.out.println();

    }

    private static void collectMonths(HashMap<String, Double> monthMap, String[] row) {
        String rainString = row[RAIN_INDEX];
        String monthString = row[MONTH_INDEX];
        double rain = 0;
        try {
            rain = Double.parseDouble(rainString);
        } catch (Exception ignored) {}

        if (monthMap.containsKey(monthString)) {
            double oldRain = monthMap.get(monthString);
            double newRain = oldRain + rain;
            monthMap.put(monthString, newRain);
        }
        else {
            monthMap.put(monthString, rain);
        }
    }
}
