package by.sashnikov.jfuture.imdb.movie.search;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import by.sashnikov.jfuture.imdb.ParseUtil;
import by.sashnikov.jfuture.imdb.SearchQuery;
import by.sashnikov.jfuture.imdb.movie.release.MovieRelasesDTO;
import by.sashnikov.jfuture.imdb.movie.release.MovieReleasesQuery;
import by.sashnikov.jfuture.model.Country;
import by.sashnikov.jfuture.model.Genre;
import by.sashnikov.jfuture.model.Movie;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

/**
 * @author Ilya_Sashnikau
 */
public class MovieSearchQuery implements SearchQuery<Movie> {

  private static final String BASE_SEARCH_URL = "https://www.imdb.com/search/title/";
  private static final String RELEASE_DATE_PARAM_NAME = "release_date";
  private static final String GENRE_PARAM_NAME = "genres";
  private static final String START_PARAM_NAME = "start";
  private static final String COUNTRIES_PARAM_NAME = "countries";
  private static final String QUERY_SIZE_PARAM_NAME = "count";
  private static final int QUERY_SIZE = 50;

  private final MovieSearchPageParser movieSearchPageParser;
  private final LocalDate startReleaseDate;
  private final LocalDate endReleaseDate;
  private final Genre genre;
  private final Country country;
  private final int start;

  public MovieSearchQuery(LocalDate startReleaseDate, LocalDate endReleaseDate, Genre genre,
      Country country) {
    this.startReleaseDate = startReleaseDate;
    this.endReleaseDate = endReleaseDate;
    this.genre = genre;
    this.country = country;
    this.start = 1;
    this.movieSearchPageParser = createParser(startReleaseDate, endReleaseDate, genre, country,
        this.start);
  }

  private MovieSearchQuery(LocalDate startReleaseDate, LocalDate endReleaseDate, Genre genre,
      Country country, Integer start) {
    this.startReleaseDate = startReleaseDate;
    this.endReleaseDate = endReleaseDate;
    this.genre = genre;
    this.country = country;
    this.start = start;
    this.movieSearchPageParser = createParser(startReleaseDate, endReleaseDate, genre, country,
        start);
  }

  @Override
  public Set<Movie> getData() {
    Set<MovieSearchDTO> movieSearchDTOS = movieSearchPageParser.getPageData();

    return movieSearchDTOS.stream()
        .peek(this::fillYear)
        .filter(movieSearchDTO -> movieSearchDTO.getReleaseYear() != null)
        .map(
            movieSearchDTO ->
                new Movie(
                    movieSearchDTO.getLink(),
                    movieSearchDTO.getReleaseYear(),
                    genre,
                    country)
        )
        .collect(Collectors.toSet());
  }

  private void fillYear(MovieSearchDTO movieSearchDTO) {
    Year releaseYear = movieSearchDTO.getReleaseYear();
    if (releaseYear == null) {
      MovieReleasesQuery movieReleasesQuery = new MovieReleasesQuery(movieSearchDTO.getLink());
      MovieRelasesDTO data = movieReleasesQuery.getData();
      for (Entry<String, Year> countryReleaseYearEntry : data.countryReleaseYear.entrySet()) {
        String countryName = countryReleaseYearEntry.getKey();
        if (this.country.name().equalsIgnoreCase(countryName)) {
          movieSearchDTO.setReleaseYear(countryReleaseYearEntry.getValue());
        }
      }
    }
  }

  private MovieSearchPageParser createParser(LocalDate startReleaseDate,
      LocalDate endReleaseDate, Genre genre, Country country, int start) {
    try {
      String url = buildSearchQueryUrl(startReleaseDate, endReleaseDate, genre, country, start);
      return new MovieSearchPageParser(url);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private String buildSearchQueryUrl(LocalDate startReleaseDate, LocalDate endReleaseDate,
      Genre genre, Country country, int start) throws URISyntaxException {
    URIBuilder uriBuilder = ParseUtil.createRequestUrl(BASE_SEARCH_URL, SearchParameter.values());

    uriBuilder
        .addParameter(RELEASE_DATE_PARAM_NAME,
            buildReleaseDateParameter(startReleaseDate, endReleaseDate))
        .addParameter(GENRE_PARAM_NAME, genre.parameterName)
        .addParameter(START_PARAM_NAME, Integer.toString(start))
        .addParameter(QUERY_SIZE_PARAM_NAME, Integer.toString(QUERY_SIZE))
        .addParameter(COUNTRIES_PARAM_NAME, country.searchParam);

    return uriBuilder.build().toString();
  }

  private String buildReleaseDateParameter(LocalDate startReleaseDate, LocalDate endReleaseDate) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String dateFrom = startReleaseDate.format(dateTimeFormatter);
    String dateTo = endReleaseDate.format(dateTimeFormatter);
    return String.join(",", dateFrom, dateTo);
  }

  @Override
  public Iterator<SearchQuery<Movie>> iterator() {
    return new Itr();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MovieSearchQuery)) {
      return false;
    }
    MovieSearchQuery that = (MovieSearchQuery) o;
    return Objects.equals(startReleaseDate, that.startReleaseDate) &&
        Objects.equals(endReleaseDate, that.endReleaseDate) &&
        genre == that.genre &&
        country == that.country &&
        Objects.equals(start, that.start);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startReleaseDate, endReleaseDate, genre, country, start);
  }

  enum SearchParameter implements NameValuePair {
    TITLE_TYPE("title_type", "feature"),
    ADULT("adult", "include"),
    COUNT("count", Integer.toString(QUERY_SIZE)),
    REF("ref_", "adv_nxt");

    private final String parameterName;
    private final String parameterValue;

    SearchParameter(String name, String value) {
      this.parameterName = name;
      this.parameterValue = value;
    }

    @Override
    public String getName() {
      return parameterName;
    }

    @Override
    public String getValue() {
      return parameterValue;
    }
  }

  private class Itr implements Iterator<SearchQuery<Movie>> {

    private int currentPage = MovieSearchQuery.this.start / QUERY_SIZE;
    private MovieSearchQuery nextQuery = MovieSearchQuery.this;
    private int totalPages = movieSearchPageParser.totalResultAmount() / QUERY_SIZE + 1;

    @Override
    public boolean hasNext() {
      return currentPage != totalPages;
    }

    @Override
    public SearchQuery<Movie> next() {
      MovieSearchQuery q = nextQuery;
      nextQuery = new MovieSearchQuery(
          startReleaseDate,
          endReleaseDate,
          genre,
          country,
          ++currentPage * QUERY_SIZE + 1
      );
      return q;
    }
  }
}
