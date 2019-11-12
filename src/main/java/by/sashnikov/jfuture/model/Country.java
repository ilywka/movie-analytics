package by.sashnikov.jfuture.model;

/**
 * @author Ilya_Sashnikau
 */
public enum Country {
  USA("us"),CHINA("cn");

  public final String searchParam;

  Country(String searchParam) {
    this.searchParam = searchParam;
  }
}
