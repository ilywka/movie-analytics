package by.sashnikov.jfuture;

import java.util.HashSet;
import java.util.Set;
import by.sashnikov.jfuture.imdb.TopDirectorsData;
import by.sashnikov.jfuture.model.Director;

/**
 * @author Ilya_Sashnikau
 */
public class TopDirectors {

  public static void createCharts() {
    Set<Director> genreMovies = pullDirectorsData();
//    JfreePlot.releaseDynamicsPlot(genre, genreMovies);
  }

  private static Set<Director> pullDirectorsData() {
    TopDirectorsData releaseDynamicsData = new TopDirectorsData();
    Set<Director> movies = releaseDynamicsData.fetchData();
    return new HashSet<>(movies);
  }
}
