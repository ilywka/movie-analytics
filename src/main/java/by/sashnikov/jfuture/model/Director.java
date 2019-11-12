package by.sashnikov.jfuture.model;

import java.util.Set;

/**
 * @author Ilya_Sashnikau
 */
public class Director {

  public final String link;
  public final Set<Movie> movies;

  public Director(String link, Set<Movie> movies) {
    this.link = link;
    this.movies = movies;
  }
}
