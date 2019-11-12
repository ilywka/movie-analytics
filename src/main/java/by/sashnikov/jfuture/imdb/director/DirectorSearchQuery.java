package by.sashnikov.jfuture.imdb.director;

import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Set;
import by.sashnikov.jfuture.imdb.ParseUtil;
import by.sashnikov.jfuture.imdb.SearchQuery;
import by.sashnikov.jfuture.model.Director;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

/**
 * @author Ilya_Sashnikau
 */
public class DirectorSearchQuery implements SearchQuery<Director> {

  private static final String BASE_SEARCH_URL = "https://www.imdb.com/search/name/";

  private final DirectorSearchPageParser parser;

  public DirectorSearchQuery() {
    this.parser = new DirectorSearchPageParser(buildSearchQueryUrl());
  }

  private String buildSearchQueryUrl() {
    try {
      URIBuilder requestUrl = ParseUtil.createRequestUrl(BASE_SEARCH_URL, SearchParameter.values());
      return requestUrl.build().toString();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Set<Director> getData() {
    DirectorSearchDTO directorSearchDTO = parser.directorsData();
    return null;
  }

  @Override
  public Iterator<SearchQuery<Director>> iterator() {
    return new Itr();
  }

  enum SearchParameter implements NameValuePair {
    GROUPS("groups", "oscar_best_director_nominees,best_director_winner"),
    ADULT("adult", "include");

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

    @Override
    public boolean hasNext() {
      return false;
    }

    @Override
    public SearchQuery<Director> next() {
      return null;
    }
  }
}
