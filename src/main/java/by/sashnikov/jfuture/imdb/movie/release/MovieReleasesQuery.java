package by.sashnikov.jfuture.imdb.movie.release;

import java.time.Year;
import java.util.Map;

/**
 * @author Ilya_Sashnikau
 */
public class MovieReleasesQuery {

  private static final String RELEASE_PAGE_PATH = "releaseinfo";

  private final MovieReleasesPageParser movieSearchPageParser;
  private final String movieLink;

  public MovieReleasesQuery(String movieLink) {
    this.movieLink = movieLink;
    this.movieSearchPageParser = new MovieReleasesPageParser(movieLink.concat(RELEASE_PAGE_PATH));
  }

  public MovieReleasesDTO getData() {
    Map<String, Year> stringYearMap = movieSearchPageParser.parseCountryReleaseYear();
    return new MovieReleasesDTO(movieLink, stringYearMap);
  }

}
