package by.sashnikov.jfuture.imdb.movie.page;

import by.sashnikov.jfuture.imdb.Query;

/**
 * @author Ilya_Sashnikau
 */
public class MoviePageQuery implements Query<MoviePageDTO> {

  private final MoviePageParser parser;
  private final String pageLink;

  public MoviePageQuery(String pageLink) {
    this.pageLink = pageLink;
    this.parser = new MoviePageParser(pageLink);
  }

  @Override
  public MoviePageDTO getData() {
    Double rating = parser.parseRating();
    return new MoviePageDTO(pageLink, rating);
  }
}
