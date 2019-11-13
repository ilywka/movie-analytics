package by.sashnikov.jfuture.model;

import java.util.Objects;
import java.util.Set;

/**
 * @author Ilya_Sashnikau
 */
public class Director {

  public final String id;
  public final String name;
  public final Set<Movie> movies;

  public Director(String id, String name, Set<Movie> movies) {
    this.id = id;
    this.name = name;
    this.movies = movies;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Director)) {
      return false;
    }
    Director director = (Director) o;
    return Objects.equals(id, director.id) &&
        Objects.equals(name, director.name) &&
        Objects.equals(movies, director.movies);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, movies);
  }
}
