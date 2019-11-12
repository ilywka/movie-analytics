package by.sashnikov.jfuture.imdb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import by.sashnikov.jfuture.util.ProgressBarUtil;

/**
 * @author Ilya_Sashnikau
 */
public abstract class DataFetcher<T> {

  public Set<T> fetchData() {
    List<SearchQuery<T>> allSearchQueries = new ArrayList<>(createAllSearchQueries());
    String taskName = getTaskName();
    Map<T, T> data = ProgressBarUtil
        .wrapStream(allSearchQueries.parallelStream(), taskName)
        .map(SearchQuery::getData)
        .flatMap(Collection::stream)
        .filter(Objects::nonNull)
        .collect(Collectors.toConcurrentMap(Function.identity(), Function.identity()));
    return data.keySet();
  }

  private Set<SearchQuery<T>> createAllSearchQueries() {
    SearchQuery<T> searchQuery = createSearchQuery();

    Set<SearchQuery<T>> searchPageQueries = new HashSet<>();
    for (SearchQuery<T> sc : searchQuery) {
      searchPageQueries.add(sc);
    }

    return searchPageQueries;
  }

  abstract SearchQuery<T> createSearchQuery();

  abstract String getTaskName();
}
