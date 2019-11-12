package by.sashnikov.jfuture.imdb;

import by.sashnikov.jfuture.imdb.director.search.DirectorSearchQuery;
import by.sashnikov.jfuture.model.Director;

/**
 * @author Ilya_Sashnikau
 */
public class TopDirectorsData extends Data<Director> {

  String getTaskName() {
    return "Pulling directors data.";
  }

  @Override
  SearchQuery<Director> createSearchQuery() {
    return new DirectorSearchQuery();
  }
}
