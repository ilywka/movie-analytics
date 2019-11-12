package by.sashnikov.jfuture.model;

/**
 * @author Ilya_Sashnikau
 */
public enum Genre {
  SCI_FI("sci-fi"),
  COMEDY("comedy"),
  DRAMA("drama"),
  HORROR("horror"),
  WESTERN("western"),;

  public final String parameterName;

  Genre(String parameterName) {
    this.parameterName = parameterName;
  }
}
