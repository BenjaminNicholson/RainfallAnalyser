

public class RainfallAnalyser {
    public static void main(String[] args) {
        String path = "src/MountSheridanStationCNS.csv";

        try {
            textio.TextIO.readFile(path);
            String savePath = generateSavePath(path);
            analyseDataset(savePath);

        } catch (Exception e) {
            System.out.println("ERROR: failed to process file");
        }
    }

    private static String generateSavePath(String path) {
        String[] pathElements = path.trim().split("/");
        String[] filenameElements = pathElements[1].trim()
                .split("\\.");
        return String.format("%s/%s_analysed.%s", pathElements[0],
                filenameElements[0], filenameElements[1]);
    }

    private static void analyseDataset(String savePath) {
        String[] header = extractRecord(); // ignore header record
        if (header == null) {
            System.out.println("ERROR: file is empty");
            return;
        }

        // setup accumulation
        double totalRainfall = 0.0;
        double minRainfall = Double.POSITIVE_INFINITY;
        double maxRainfall = 0.0;

        int currentMonth = 1; // sentinel
        int currentYear = 0; // sentinel

        String[] record = extractRecord(); // get record

        // setup output file
        textio.TextIO.writeFile(savePath);

        // create new records for save file
        while (record != null) {
            // convert important fields to correct data types
            int year = Integer.parseInt(record[2]);
            int month = Integer.parseInt(record[3]);
            int day = Integer.parseInt(record[4]);
            double rainfall = record.length < 6 ? 0 : Double.parseDouble(record[5]);

            if ((month < 1 || month > 12) || (day < 1 || day > 31)) {
                System.out.println("ERROR: failed to process file");
                return;
            }

            if (month != currentMonth) {
                // new month detected - save record and reset accumulation
                writeRecord(totalRainfall, minRainfall, maxRainfall, currentMonth, currentYear == 0 ? year : currentYear);

                currentMonth = month;
                currentYear = year;
                totalRainfall = 0;
                minRainfall = Double.POSITIVE_INFINITY;
                maxRainfall = 0;
                continue;
            }

            // update accumulation
            totalRainfall += rainfall;
            if (rainfall < minRainfall) minRainfall = rainfall;
            if (rainfall > maxRainfall) maxRainfall = rainfall;

            record = extractRecord(); // get record
        }

        if (currentMonth < 12) {
            // last month is incomplete - save record
            writeRecord(totalRainfall, minRainfall, maxRainfall, currentMonth, currentYear);
        }
    }

    private static void writeRecord(double totalRainfall, double minRainfall, double maxRainfall, int currentMonth, int year) {
        String newRecord = String.format("%d,%d,%1.2f,%1.2f,%1.2f%s", year, currentMonth,
                totalRainfall, minRainfall, maxRainfall, System.lineSeparator());
        textio.TextIO.putf(newRecord);
    }

    private static String[] extractRecord() {
        if (textio.TextIO.eof()) return null; // convert EOF to null

        String text = textio.TextIO.getln();
        return text.trim().split(",");
    }
}
