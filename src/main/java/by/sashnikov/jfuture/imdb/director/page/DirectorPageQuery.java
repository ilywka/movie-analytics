package by.sashnikov.jfuture.imdb.director.page;

import by.sashnikov.jfuture.imdb.ParseUtil;
import by.sashnikov.jfuture.imdb.Query;

/**
 * @author Ilya_Sashnikau
 */
public class DirectorPageQuery implements Query<DirectorPageDTO> {

  private final DirectorPageParser parser;

  public DirectorPageQuery(String directorId) {
    this.parser = new DirectorPageParser(ParseUtil.directorPageUrl(directorId));
  }

  @Override
  public DirectorPageDTO getData() {
    return new DirectorPageDTO(parser.getMovieLinks());
  }
}
