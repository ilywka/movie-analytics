package by.sashnikov.jfuture.model;

import java.time.Year;
import java.util.Objects;

/**
 * @author Ilya_Sashnikau
 */
public class Movie {

  public final String link;
  public final Year year;
  public final Genre genre;
  public final Country country;
  public final Double rating;

  public Movie(String link, Year year, Genre genre, Country country) {
    this.link = link;
    this.year = year;
    this.genre = genre;
    this.country = country;
    this.rating = null;
  }

  public Movie(String link, Double rating) {
    this.link = link;
    this.rating = rating;
    this.year = null;
    this.genre = null;
    this.country = null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Movie)) {
      return false;
    }
    Movie movie = (Movie) o;
    return Objects.equals(link, movie.link) &&
        Objects.equals(year, movie.year) &&
        genre == movie.genre &&
        country == movie.country &&
        Objects.equals(rating, movie.rating);
  }

  @Override
  public int hashCode() {
    return Objects.hash(link, year, genre, country, rating);
  }
}
