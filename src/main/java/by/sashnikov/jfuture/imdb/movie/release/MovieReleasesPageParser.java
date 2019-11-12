package by.sashnikov.jfuture.imdb.movie.release;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import by.sashnikov.jfuture.imdb.Parser;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Ilya_Sashnikau
 */
public class MovieReleasesPageParser extends Parser {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
      .ofPattern("d MMMM yyyy");
  private static final DateTimeFormatter YEAR_FORMATTER = new DateTimeFormatterBuilder()
      .appendPattern("yyyy")
      .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
      .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
      .toFormatter();
  private static final DateTimeFormatter MONTH_FORMATTER = new DateTimeFormatterBuilder()
      .appendPattern("MMMM yyyy")
      .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
      .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
      .toFormatter();


  public MovieReleasesPageParser(String pageUrl) {
    super(pageUrl);
  }

  public Map<String, Year> parseCountryReleaseYear() {
    Map<String, Year> countryReleaseYear = new HashMap<>();
    Elements releasesData = getDocument()
        .getElementsByClass("ipl-zebra-list__item release-date-item");

    for (Element releaseElement : releasesData) {
      Elements countryNameElements = releaseElement
          .getElementsByClass("release-date-item__country-name");
      Elements releaseDateElements = releaseElement.getElementsByClass("release-date-item__date");

      String releaseCountryName = obtainReleaseCountryName(countryNameElements);
      LocalDate releaseDate = obtainReleaseDate(releaseDateElements);
      if (releaseDate != null && releaseCountryName != null) {
        countryReleaseYear.put(releaseCountryName, Year.from(releaseDate));
      }
    }
    return countryReleaseYear;
  }

  private static LocalDate obtainReleaseDate(Elements releaseDateElements) {
    String releaseDateStr = Optional.ofNullable(releaseDateElements.get(0))
        .filter(Element::hasText)
        .map(Element::text)
        .map(String::trim)
        .orElse(null);
    return parseDateString(releaseDateStr);
  }

  private static LocalDate parseDateString(String releaseDateStr) {
    LocalDate releaseLocalDate =
        Optional.ofNullable(parseDateString(releaseDateStr, DATE_FORMATTER))
            .or(() -> Optional.ofNullable(parseDateString(releaseDateStr, MONTH_FORMATTER)))
            .or(() -> Optional.ofNullable(parseDateString(releaseDateStr, YEAR_FORMATTER)))
            .orElse(null);
    return releaseLocalDate;
  }

  private static LocalDate parseDateString(String releaseDateStr, DateTimeFormatter formatter) {
    try {
      return LocalDate.parse(releaseDateStr, formatter);
    } catch (DateTimeParseException e) {
      return null;
    }
  }

  private static String obtainReleaseCountryName(Elements countryNameElements) {
    return Optional.ofNullable(countryNameElements.get(0))
        .filter(Element::hasText)
        .map(Element::text)
        .map(String::trim)
        .orElse(null);
  }

}
