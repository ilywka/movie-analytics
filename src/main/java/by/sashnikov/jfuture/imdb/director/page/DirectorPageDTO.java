package by.sashnikov.jfuture.imdb.director.page;

import java.util.Set;

/**
 * @author Ilya_Sashnikau
 */
public class DirectorPageDTO {
  public final Set<String> movieLinks;

  DirectorPageDTO(Set<String> movieLinks) {
    this.movieLinks = movieLinks;
  }
}
