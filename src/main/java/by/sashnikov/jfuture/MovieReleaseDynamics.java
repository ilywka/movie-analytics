package by.sashnikov.jfuture;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
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
    Map<Genre, Map<Year, Map<String, Long>>> dataMap = new EnumMap<>(Genre.class);
    for (Genre genre : Genre.values()) {
      Set<Movie> genreMovies = pullGenreData(genre);
      dataMap.put(genre, groupReleaseDynamicsData(genreMovies));
    }
    JfreePlot.releaseDynamicsPlot(dataMap);
  }

  private static Map<Year, Map<String, Long>> groupReleaseDynamicsData(Set<Movie> movies) {
    return movies.stream()
        .collect(
            Collectors
                .groupingBy(
                    movie -> movie.year,
                    TreeMap::new
                    ,
                    Collectors.groupingBy(
                        movie -> movie.country.toString(),
                        Collectors.counting()
                    )));
  }

  private static Set<Movie> pullGenreData(Genre genre) {
    Set<Movie> genreMovies = new HashSet<>();
    for (Country country : Country.values()) {
      ReleaseDynamicsData releaseDynamicsData = new ReleaseDynamicsData(
          country,
          genre,
          SEARCH_PERIOD_START,
          SEARCH_PERIOD_END);
      Set<Movie> movies = releaseDynamicsData.data();
      genreMovies.addAll(movies);
    }
    return genreMovies;
  }
}
