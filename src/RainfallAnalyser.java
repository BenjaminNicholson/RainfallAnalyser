import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RainfallAnalyser {

    public static void main(String[] args) {
        Object next;
        for (List<String> strings : fileReader()) {
            next = strings;
            System.out.println(next);
        }
    }

    public static List<List<String>> fileReader() {
        List<List<String>> rainfallValues = new ArrayList<>();
        try (CSVReader fileInput = new CSVReader(new FileReader(
                "C:\\Users\\bensk\\IdeaProjects\\RainfallAnalyser\\src\\MountSheridanStationCNS.csv"))) {
            String[] values;

            while ((values = fileInput.readNext()) != null) {
                rainfallValues.add(Arrays.asList(values));
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return rainfallValues;
    }
}
