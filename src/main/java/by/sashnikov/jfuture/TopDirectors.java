package by.sashnikov.jfuture;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import by.sashnikov.jfuture.imdb.TopDirectorsData;
import by.sashnikov.jfuture.model.Director;
import by.sashnikov.jfuture.model.Movie;
import by.sashnikov.jfuture.plot.JfreePlot;

/**
 * @author Ilya_Sashnikau
 */
public class TopDirectors {

  public static void createCharts() {
    Set<Director> directors = pullDirectorsData();
    List<DirectorData> data = createDataMap(directors);
    JfreePlot.topDirectorsPlot(data);
  }

  private static List<DirectorData> createDataMap(Set<Director> directors) {
    return directors.stream()
        .filter(director -> director.movies.size() > 5)
        .map(director -> new DirectorData(director.name, averageRating(director.movies)))
        .filter(directorData -> directorData.averageRating != null)
        .sorted(Comparator.comparing(o -> o.averageRating))
        .limit(10)
        .collect(Collectors.toList());
  }

  private static Double averageRating(Set<Movie> movies) {
    double numerator = 0d;
    double denominator = 0d;
    for (Movie movie : movies) {
      if (movie.rating != null) {
        numerator += movie.rating;
        denominator++;
      }
    }
    return Double.compare(0d, denominator) != 0 ? numerator / denominator : null;
  }

  private static Set<Director> pullDirectorsData() {
    TopDirectorsData releaseDynamicsData = new TopDirectorsData();
    Set<Director> directors = releaseDynamicsData.data();
    return new HashSet<>(directors);
  }

  public static class DirectorData {
    public final String name;
    public final Double averageRating;

    public DirectorData(String name, Double averageRating) {
      this.name = name;
      this.averageRating = averageRating;
    }
  }
}
