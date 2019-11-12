package by.sashnikov.jfuture.imdb;

import java.util.Set;

/**
 * Search query for any data, query result is paged.
 *
 * @author Ilya_Sashnikau
 */
public interface SearchQuery<T> extends Query<Set<T>>, Iterable<SearchQuery<T>> {
}
