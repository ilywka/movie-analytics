package by.sashnikov.jfuture.imdb;

/**
 * Query for particular page data.
 *
 * @author Ilya_Sashnikau
 */
public interface Query<T> {

  T getData();

}
