package by.sashnikov.jfuture.imdb;

import by.sashnikov.jfuture.imdb.director.DirectorSearchQuery;
import by.sashnikov.jfuture.model.Director;

/**
 * @author Ilya_Sashnikau
 */
public class TopDirectorsData extends DataFetcher<Director> {

  String getTaskName() {
    return "Pulling directors data.";
  }

  @Override
  SearchQuery<Director> createSearchQuery() {
    return new DirectorSearchQuery();
  }
}
