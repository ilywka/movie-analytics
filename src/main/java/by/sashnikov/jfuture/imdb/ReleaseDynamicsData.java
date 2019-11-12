package by.sashnikov.jfuture.imdb;

import java.time.LocalDate;
import by.sashnikov.jfuture.imdb.movie.search.MovieSearchQuery;
import by.sashnikov.jfuture.model.Country;
import by.sashnikov.jfuture.model.Genre;
import by.sashnikov.jfuture.model.Movie;

/**
 * @author Ilya_Sashnikau
 */
public class ReleaseDynamicsData extends DataFetcher<Movie> {

  private Country country;
  private Genre genre;
  private LocalDate startDate;
  private LocalDate endDate;

  public ReleaseDynamicsData(Country country, Genre genre, LocalDate startDate, LocalDate endDate) {
    this.country = country;
    this.genre = genre;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  String getTaskName() {
    return String.format("Pulling %s genre movie data for %s country. ", genre, country);
  }

  @Override
  SearchQuery<Movie> createSearchQuery() {
    return new MovieSearchQuery(startDate, endDate, genre, country);
  }
}
