package by.sashnikov.jfuture.imdb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import by.sashnikov.jfuture.util.ProgressBarUtil;

/**
 * @author Ilya_Sashnikau
 */
public abstract class Data<T> {

  public Set<T> data() {
    List<SearchQuery<T>> allSearchQueries = new ArrayList<>(createAllSearchQueries());
    String taskName = getTaskName();

    ConcurrentMap<Set<T>, Set<T>> data = ProgressBarUtil.wrapStream(allSearchQueries.parallelStream(), taskName)
        .map(SearchQuery::getData)
        .collect(Collectors.toConcurrentMap(Function.identity(), Function.identity()));
    return data.keySet().stream().flatMap(Collection::stream).filter(Objects::nonNull).collect(
        Collectors.toSet());
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
