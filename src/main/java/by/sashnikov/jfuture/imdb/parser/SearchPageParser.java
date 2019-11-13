package by.sashnikov.jfuture.imdb.parser;

import static by.sashnikov.jfuture.imdb.ParseUtil.getFirstGroupNumericValue;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.jsoup.nodes.Element;

/**
 * @author Ilya_Sashnikau
 */
public abstract class SearchPageParser extends Parser {

  private static final Pattern SINGLE_SEARCH_PAGE_PATTERN = Pattern.compile("([\\d,]*) \\w*\\.");
  private static final Pattern MULTIPLE_SEARCH_PAGE_PATTERN = Pattern
      .compile("\\d*-\\d* of ([\\d,]*) \\w*\\.");

  public SearchPageParser(String pageUrl) {
    super(pageUrl);
  }

  public Integer totalItemsFound() {
    return totalItemsElementsStream()
        .map(element -> parseTotalItemsText(element.text()))
        .filter(Objects::nonNull)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Total items parsing error."));
  }

  protected Stream<Element> totalItemsElementsStream() {
    return getDocument().getElementsByClass("desc")
        .stream()
        .flatMap(element -> element.getElementsByTag("span").stream());
  }

  protected Integer parseTotalItemsText(String value) {
    return Optional.ofNullable(getFirstGroupNumericValue(singleSearchPagePattern(), value))
        .orElseGet(() -> getFirstGroupNumericValue(multipleSearchPagePattern(), value));
  }

  protected Pattern singleSearchPagePattern() {
    return SINGLE_SEARCH_PAGE_PATTERN;
  }

  protected Pattern multipleSearchPagePattern() {
    return MULTIPLE_SEARCH_PAGE_PATTERN;
  }
}
