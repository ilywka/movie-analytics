package by.sashnikov.jfuture.imdb.movie.page;

/**
 * @author Ilya_Sashnikau
 */
public class MoviePageDTO {

  public final String link;
  public final Double rating;

  public MoviePageDTO(String link, Double rating) {
    this.link = link;
    this.rating = rating;
  }
}
