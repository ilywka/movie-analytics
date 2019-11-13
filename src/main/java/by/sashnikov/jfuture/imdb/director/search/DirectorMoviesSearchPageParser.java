package by.sashnikov.jfuture.imdb.director.search;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import by.sashnikov.jfuture.imdb.parser.SearchPageParser;
import by.sashnikov.jfuture.model.Movie;
import org.jsoup.nodes.Element;

/**
 * @author Ilya_Sashnikau
 */
public class DirectorMoviesSearchPageParser extends SearchPageParser {

  private static final Pattern SINGLE_SEARCH_PAGE_PATTERN = Pattern.compile("([\\d,]*) title[s]*");
  private static final Pattern MULTIPLE_SEARCH_PAGE_PATTERN = Pattern
      .compile("of ([\\d,]*) titles");

  private Set<Movie> movies;

  public DirectorMoviesSearchPageParser(String url) {
    super(url);
  }

  public Set<Movie> movies() {
    if (movies == null) {
      movies = parseMovies();
    }
    return movies;
  }

  private Set<Movie> parseMovies() {
    Set<Movie> movies = new HashSet<>();
    getDocument().getElementsByClass("lister-item-content")
        .forEach(element -> movies.add(parseMovie(element)));
    return movies;
  }

  private Movie parseMovie(Element movieElement) {
    String link = parseLink(movieElement);
    Double rating = parseRating(movieElement);
    return new Movie(link, rating);
  }

  private String parseLink(Element movieElement) {
    for (Element movieLinkElement : movieElement.getElementsByAttribute("href")) {
      return movieLinkElement.attr("href");
    }
    return null;
  }

  private Double parseRating(Element movieElement) {
    for (Element ratingElement : movieElement
        .getElementsByClass("inline-block ratings-imdb-rating")) {
      String ratingText = ratingElement.attr("data-value");
      try {
        return Double.valueOf(ratingText);
      } catch (NumberFormatException ignored) {
      }
    }
    return null;
  }

  protected Stream<Element> totalItemsElementsStream() {
    return getDocument().getElementsByClass("desc").stream()
        .flatMap(element -> element.getAllElements().stream());
  }

  @Override
  protected Pattern singleSearchPagePattern() {
    return SINGLE_SEARCH_PAGE_PATTERN;
  }

  @Override
  protected Pattern multipleSearchPagePattern() {
    return MULTIPLE_SEARCH_PAGE_PATTERN;
  }
}
