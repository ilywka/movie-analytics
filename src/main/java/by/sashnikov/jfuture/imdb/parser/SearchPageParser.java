package by.sashnikov.jfuture.imdb.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Ilya_Sashnikau
 */
public abstract class SearchPageParser extends Parser {

  public SearchPageParser(String pageUrl) {
    super(pageUrl);
  }

  public Integer totalItemsFound() {
    Elements desc = getDocument().getElementsByClass("desc");
    for (Element navElement : desc) {
      Elements spanElements = navElement.getElementsByTag("span");
      for (Element span : spanElements) {
        String value = span.text();
        Pattern totalResultSinglePagePattern = Pattern.compile("([\\d,]*) \\w*\\.");
        Matcher singlePagePatternMatcher = totalResultSinglePagePattern.matcher(value);
        if (singlePagePatternMatcher.find()) {
          return Integer.valueOf(singlePagePatternMatcher.group(1).replace(",", ""));
        }
        Pattern totalResultMultiplePagePattern = Pattern.compile("\\d*-\\d* of ([\\d,]*) \\w*\\.");
        Matcher matcher = totalResultMultiplePagePattern.matcher(value);
        if (matcher.find()) {
          return Integer.valueOf(matcher.group(1).replace(",", ""));
        }
      }
    }
    throw new RuntimeException("Total movie parsing error.");
  }
}
