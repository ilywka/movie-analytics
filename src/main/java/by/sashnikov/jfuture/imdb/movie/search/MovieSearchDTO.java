package by.sashnikov.jfuture.imdb.movie.search;

import java.time.Year;

/**
 * @author Ilya_Sashnikau
 */
class MovieSearchDTO {

  private String link;
  private Year releaseYear;

  public MovieSearchDTO(String link, Year releaseYear) {
    this.link = link;
    this.releaseYear = releaseYear;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public Year getReleaseYear() {
    return releaseYear;
  }

  public void setReleaseYear(Year releaseYear) {
    this.releaseYear = releaseYear;
  }
}
