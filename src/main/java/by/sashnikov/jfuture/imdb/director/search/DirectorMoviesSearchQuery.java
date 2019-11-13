package by.sashnikov.jfuture.imdb.director.search;

import java.util.Iterator;
import java.util.Set;
import by.sashnikov.jfuture.imdb.SearchQuery;
import by.sashnikov.jfuture.model.Movie;

/**
 * @author Ilya_Sashnikau
 */
public class DirectorMoviesSearchQuery implements SearchQuery<Movie> {

  public static final String SEARCH_URL_PATTERN = "https://www.imdb.com/filmosearch/"
      + "?explore=title_type"
      + "&role=%s"
      + "&ref_=filmo_ref_typ"
      + "&sort=num_votes,desc"
      + "&mode=detail"
      + "&page=%s"
      + "&job_type=director"
      + "&title_type=movie";
  private static final Integer QUERY_SIZE = 50;
  private final String directorId;
  private final int page;
  private final DirectorMoviesSearchPageParser parser;

  public DirectorMoviesSearchQuery(String directorId) {
    this.directorId = directorId;
    this.page = 1;
    this.parser = new DirectorMoviesSearchPageParser(buildSearchUrl(directorId, page));
  }

  private DirectorMoviesSearchQuery(String directorId, int page) {
    this.directorId = directorId;
    this.page = page;
    this.parser = new DirectorMoviesSearchPageParser(buildSearchUrl(directorId, page));
  }

  private String buildSearchUrl(String directorId, int page) {
    return String.format(SEARCH_URL_PATTERN, directorId, Integer.toString(page));
  }

  @Override
  public Set<Movie> getData() {
    return parser.movies();
  }

  @Override
  public Iterator<SearchQuery<Movie>> iterator() {
    return new Itr();
  }

  private class Itr implements Iterator<SearchQuery<Movie>> {

    private int currentPage = DirectorMoviesSearchQuery.this.page - 1;
    private DirectorMoviesSearchQuery nextQuery = DirectorMoviesSearchQuery.this;
    private int totalPages = parser.totalItemsFound() / QUERY_SIZE + 1;

    @Override
    public boolean hasNext() {
      return currentPage != totalPages;
    }

    @Override
    public SearchQuery<Movie> next() {
      DirectorMoviesSearchQuery q = nextQuery;
      nextQuery = new DirectorMoviesSearchQuery(
          DirectorMoviesSearchQuery.this.directorId,
          ++currentPage
      );
      return q;
    }
  }
}
