package by.sashnikov.jfuture.plot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import by.sashnikov.jfuture.TopDirectors.DirectorData;
import by.sashnikov.jfuture.model.Genre;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * @author Ilya_Sashnikau
 */
public class JfreePlot {

  public static final String CHARTS_DIRECTORY_NAME = "charts";

  public static void releaseDynamicsPlot(Map<Genre, Map<Year, Map<String, Long>>> data) {
    for (Entry<Genre, Map<Year, Map<String, Long>>> dataEntry : data.entrySet()) {
      createPlotFile(dataEntry.getKey(), createDataset(dataEntry.getValue()));
    }
  }

  private static void createPlotFile(Genre genre, DefaultCategoryDataset dataset) {
    JFreeChart chart = ChartFactory.createBarChart(
        genre.toString().toLowerCase() + " movies release dynamics",
        "Year", "Movies number",
        dataset, PlotOrientation.VERTICAL,
        true, true, false);

    createChartFile(genre.toString().toLowerCase() + "_stats", chart);
  }

  private static void createChartFile(String fileName, JFreeChart chart) {
    int width = 1080;    /* Width of the image */
    int height = 720;   /* Height of the image */

    Path chartsPath = Paths.get(CHARTS_DIRECTORY_NAME);
    File chartFile = new File(chartsPath + "/" + fileName + ".png");
    try {
      ChartUtils.saveChartAsPNG(chartFile, chart, width, height);
      System.out.println("\rNew chart file created: " + chartFile.getAbsolutePath());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  private static DefaultCategoryDataset createDataset(
      Map<Year, Map<String, Long>> yearCountryData) {
    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (Entry<Year, Map<String, Long>> yearCountryEntry : yearCountryData.entrySet()) {
      Year year = yearCountryEntry.getKey();
      for (Entry<String, Long> countryValueEntry : yearCountryEntry.getValue().entrySet()) {
        String country = countryValueEntry.getKey();
        Long value = countryValueEntry.getValue();
        dataset.addValue(value, country, year);
      }
    }
    return dataset;
  }

  public static void topDirectorsPlot(List<DirectorData> directors) {
    DefaultCategoryDataset dataset = createDataset(directors);
    JFreeChart chart = ChartFactory.createBarChart(
        "Directors average movie rating",
        "Name", "Average Rating",
        dataset, PlotOrientation.HORIZONTAL,
        true, true, false);
    createChartFile("directors_rating", chart);
  }

  private static DefaultCategoryDataset createDataset(List<DirectorData> directors) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (DirectorData director : directors) {
      dataset.addValue(director.averageRating, "", director.name);
    }
    return dataset;
  }


  public static void createChartsDirectory() throws IOException {
    Path path = Paths.get(CHARTS_DIRECTORY_NAME);
    if (!Files.exists(path)) {
      Path chartsPath = Files.createDirectory(path);
      System.out.println("Charts directory created: " + chartsPath.toAbsolutePath());
    } else {
      System.out.println("Charts directory exists: " + path.toAbsolutePath());
    }
  }
}
