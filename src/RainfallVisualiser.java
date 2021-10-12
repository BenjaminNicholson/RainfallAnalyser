
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javafx.stage.Stage;


public class RainfallVisualiser extends Application {


    private BarChart<String, Double> showBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Years");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount");
        BarChart barChart = new BarChart(xAxis, yAxis);
        while (!textio.TextIO.eof()) {
            String[] fileContents = textio.TextIO.getln().trim().split(",");
            double monthlyTotal = Double.parseDouble(fileContents[2]);
            String year = fileContents[0];

            XYChart.Series data = new XYChart.Series();
            data.setName("Total Monthly rainfall");

            data.getData().add(new XYChart.Data(year, monthlyTotal));
            barChart.getData().add(data);
        }
        return barChart;
    }

    @Override
    public void start(Stage stage) {
        int width = 218 * 6 + 40;
        int height = 500;
        Canvas canvas = new Canvas(width, height);
        BorderPane root = new BorderPane(canvas);
        root.setStyle("-fx-border-width: 4px; -fx-border-color: #444");
        Button monthlyTotals = new Button("Monthly Totals");
        HBox buttonBox = new HBox(monthlyTotals);

        root.setBottom(buttonBox);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Rainfall Visualiser");
        stage.show();
        stage.setResizable(true);
        monthlyTotals.setOnAction(event -> root.setCenter(showBarChart()));
    }


    public static void main(String[] args) {

        String path = "src/MountSheridanStationCNS_analysed.csv";
        textio.TextIO.readFile(path);
        launch();
    }


}  // end class ShapeDraw

