package by.sashnikov.jfuture.imdb.movie.release;

import java.time.Year;
import java.util.Map;

/**
 * @author Ilya_Sashnikau
 */
public class MovieReleasesDTO {

  public final String  link;
  public final Map<String, Year> countryReleaseYear;

  public MovieReleasesDTO(String link,
      Map<String, Year> countryReleaseYear) {
    this.link = link;
    this.countryReleaseYear = countryReleaseYear;
  }

}
