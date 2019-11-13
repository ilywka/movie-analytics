package by.sashnikov.jfuture.imdb.director.search;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import by.sashnikov.jfuture.imdb.ParseUtil;
import by.sashnikov.jfuture.imdb.SearchQuery;
import by.sashnikov.jfuture.model.Director;
import by.sashnikov.jfuture.model.Movie;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

/**
 * @author Ilya_Sashnikau
 */
public class DirectorSearchQuery implements SearchQuery<Director> {

  private static final String BASE_SEARCH_URL = "https://www.imdb.com/search/name/";
  private static final String START_PARAM_NAME = "start";
  private static final int QUERY_SIZE = 3;

  private final DirectorSearchPageParser parser;
  private int start;

  public DirectorSearchQuery() {
    this.start = 1;
    this.parser = new DirectorSearchPageParser(buildSearchQueryUrl(start));
  }

  private DirectorSearchQuery(int start) {
    this.start = start;
    this.parser = new DirectorSearchPageParser(buildSearchQueryUrl(start));
  }

  private String buildSearchQueryUrl(int start) {
    try {
      URIBuilder requestUrl = ParseUtil.createRequestUrl(BASE_SEARCH_URL, SearchParameter.values());
      requestUrl.addParameter(START_PARAM_NAME, Integer.toString(start));
      return requestUrl.build().toString();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Set<Director> getData() {
    Set<DirectorSearchDTO> directorDTOs = parser.directorsData();
    Map<Director, Director> data =
        directorDTOs.stream()
            .map(this::createData)
            .collect(Collectors.toMap(Function.identity(), Function.identity()));
    return data.keySet();
  }

  private Director createData(DirectorSearchDTO directorSearchDTO) {
    Set<Movie> directorMovies = new HashSet<>();
    DirectorMoviesSearchQuery searchQueries = new DirectorMoviesSearchQuery(directorSearchDTO.id);
    searchQueries.forEach(movieSearchQuery -> directorMovies.addAll(movieSearchQuery.getData()));
    return new Director(directorSearchDTO.id, directorSearchDTO.name, directorMovies);
  }

  @Override
  public Iterator<SearchQuery<Director>> iterator() {
    return new Itr();
  }

  enum SearchParameter implements NameValuePair {
    GROUPS("groups", "oscar_best_director_nominees,best_director_winner"),
    ADULT("adult", "include"),
    COUNT("count", Integer.toString(QUERY_SIZE));

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

  private class Itr implements Iterator<SearchQuery<Director>> {

    private int currentPage = DirectorSearchQuery.this.start / QUERY_SIZE;
    private DirectorSearchQuery nextQuery = DirectorSearchQuery.this;
    private int totalPages = parser.totalItemsFound() / QUERY_SIZE + 1;

    @Override
    public boolean hasNext() {
      return currentPage != totalPages;
    }

    @Override
    public SearchQuery<Director> next() {
      DirectorSearchQuery q = nextQuery;
      nextQuery = new DirectorSearchQuery(
          ++currentPage * QUERY_SIZE + 1
      );
      return q;
    }
  }
}
