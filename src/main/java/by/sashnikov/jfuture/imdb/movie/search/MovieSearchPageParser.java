package by.sashnikov.jfuture.imdb.movie.search;

import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import by.sashnikov.jfuture.imdb.ParseUtil;
import by.sashnikov.jfuture.imdb.Parser;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

/**
 * @author Ilya_Sashnikau
 */
class MovieSearchPageParser extends Parser {

  private static final Pattern YEAR_PATTERN = Pattern.compile("\\((\\d*)\\)");

  private Set<MovieSearchDTO> data;

  MovieSearchPageParser(String url) {
    super(url);
  }

  public Integer totalResultAmount() {
    Elements desc = getDocument().getElementsByClass("desc");
    for (Element navElement : desc) {
      Elements spanElements = navElement.getElementsByTag("span");
      for (Element span : spanElements) {
        for (Node spanNode : span.childNodes()) {
          if (spanNode instanceof TextNode) {
            String value = ((TextNode) spanNode).text();
            Pattern totalResultPattern = Pattern.compile("([\\d,]*) titles\\.");
            Matcher matcher = totalResultPattern.matcher(value);
            if (matcher.find()) {
              return Integer.valueOf(matcher.group(1).replace(",", ""));
            }
          }
        }
      }
    }
    throw new RuntimeException("Total movie parsing error.");
  }

  public Set<MovieSearchDTO> getPageData() {
    if (data == null) {
      data = parsePage();
    }
    return data;
  }

  private Set<MovieSearchDTO> parsePage() {
    Elements itemHeaders = getDocument().getElementsByClass("lister-item-header");
    Set<MovieSearchDTO> movieDTOs = new HashSet<>(itemHeaders.size());

    for (Element itemHeader : itemHeaders) {
      MovieSearchDTO movie = parseMovie(itemHeader);
      movieDTOs.add(movie);
    }
    return movieDTOs;
  }

  private MovieSearchDTO parseMovie(Element itemHeader) {
    String link = parseMovieLink(itemHeader);
    Year year = parseYear(itemHeader);
    return new MovieSearchDTO(link, year);
  }


  private String parseMovieLink(Element itemHeader) {
    Elements linkElement = itemHeader.getElementsByAttribute("href");
    String href = linkElement.attr("href");
    String moviePath = extractPath(href);
    return ParseUtil.IMDB_URL.concat(moviePath);
  }

  private String extractPath(String hrefDirtyValue) {
    int redundantParamsStart = hrefDirtyValue.lastIndexOf('?');
    return hrefDirtyValue.substring(0, redundantParamsStart);
  }

  private Year parseYear(Element itemHeader) {
    Elements yearElements = itemHeader
        .getElementsByClass("lister-item-year text-muted unbold");
    return parseYear(yearElements);
  }

  private Year parseYear(Elements yearElements) {
    return Optional.ofNullable(yearElements.get(0))
        .map(Node::childNodes)
        .filter(Predicate.not(List::isEmpty))
        .map(nodes -> nodes.get(0))
        .filter(node -> node instanceof TextNode)
        .map(TextNode.class::cast)
        .map(TextNode::text)
        .map(this::parseYearString)
        .map(Year::parse)
        .orElse(null);
  }

  private String parseYearString(String rawYear) {
    Matcher matcher = YEAR_PATTERN.matcher(rawYear);
    if (matcher.find()) {
      return matcher.group(1);
    } else {
      return null;
    }
  }

}
