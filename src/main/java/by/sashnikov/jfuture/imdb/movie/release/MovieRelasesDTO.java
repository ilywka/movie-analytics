package by.sashnikov.jfuture.imdb.movie.release;

import java.time.Year;
import java.util.Map;

/**
 * @author Ilya_Sashnikau
 */
public class MovieRelasesDTO {

  public final String movieLink;
  public final Map<String, Year> countryReleaseYear;

  public MovieRelasesDTO(String movieLink,
      Map<String, Year> countryReleaseYear) {
    this.movieLink = movieLink;
    this.countryReleaseYear = countryReleaseYear;
  }
}
