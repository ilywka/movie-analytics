package by.sashnikov.jfuture.imdb.movie.page;

import by.sashnikov.jfuture.imdb.parser.Parser;
import org.jsoup.nodes.Element;

/**
 * @author Ilya_Sashnikau
 */
public class MoviePageParser extends Parser {

  public MoviePageParser(String pageUrl) {
    super(pageUrl);
  }

  public Double parseRating() {
    for (Element ratingElement : getDocument()
        .getElementsByAttributeValue("itemprop", "ratingValue")) {
      String rating = ratingElement.text();
      try {
        return Double.valueOf(rating);
      } catch (NumberFormatException ignored) {
      }
    }
    return null;
  }
}
