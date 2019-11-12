package by.sashnikov.jfuture;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Set;
import by.sashnikov.jfuture.imdb.ReleaseDynamicsData;
import by.sashnikov.jfuture.model.Country;
import by.sashnikov.jfuture.model.Genre;
import by.sashnikov.jfuture.model.Movie;
import by.sashnikov.jfuture.plot.JfreePlot;

/**
 * @author Ilya_Sashnikau
 */
public class MovieReleaseDynamics {

  private static final LocalDate SEARCH_PERIOD_START = Year.of(2017).atDay(1)
      .with(TemporalAdjusters.firstDayOfYear());
  private static final LocalDate SEARCH_PERIOD_END = Year.of(2019).atDay(1)
      .with(TemporalAdjusters.lastDayOfYear());

  public static void createDynamicsCharts() {
    for (Genre genre : Genre.values()) {
      Set<Movie> genreMovies = pullGenreData(genre);
      JfreePlot.releaseDynamicsPlot(genre, genreMovies);
    }
  }

  private static Set<Movie> pullGenreData(Genre genre) {
    Set<Movie> genreMovies = new HashSet<>();
    for (Country country : Country.values()) {
      ReleaseDynamicsData releaseDynamicsData = new ReleaseDynamicsData(
          country,
          genre,
          SEARCH_PERIOD_START,
          SEARCH_PERIOD_END);
      Set<Movie> movies = releaseDynamicsData.fetchData();
      genreMovies.addAll(movies);
    }
    return genreMovies;
  }
}
