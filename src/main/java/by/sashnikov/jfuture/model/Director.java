package by.sashnikov.jfuture.model;

import java.util.Set;

/**
 * @author Ilya_Sashnikau
 */
public class Director {

  public final String link;
  public final String name;
  public final Set<Movie> movies;

  public Director(String link, String name, Set<Movie> movies) {
    this.link = link;
    this.name = name;
    this.movies = movies;
  }
}
