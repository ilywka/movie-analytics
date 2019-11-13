package by.sashnikov.jfuture.imdb;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author Ilya_Sashnikau
 */
public class ParseUtil {

  public static final String IMDB_URL = "https://www.imdb.com";
  private static final String DIRECTOR_URL_BASE = "https://www.imdb.com/name/";

  public static Document getDocumentQuietly(String url) {
    int i = 0;
    while (true) {
      try {
        return Jsoup.connect(url)
            .get();
      } catch (IOException e) {
        i++;
        if (i == 5) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  public static String extractPath(String hrefDirtyValue) {
    int redundantParamsStart = hrefDirtyValue.lastIndexOf('?');
    if (redundantParamsStart == -1) {
      return hrefDirtyValue;
    } else {
      return hrefDirtyValue.substring(0, redundantParamsStart);
    }
  }

  public static URIBuilder createRequestUrl(String baseUrl, NameValuePair[] parameters)
      throws URISyntaxException {
    URIBuilder uriBuilder = new URIBuilder(baseUrl);
    uriBuilder.addParameters(Arrays.asList(parameters));
    return uriBuilder;
  }

  public static String directorPageUrl(String directorId) {
    return DIRECTOR_URL_BASE.concat(directorId);
  }

  public static Integer getFirstGroupNumericValue(Pattern pattern, String value) {
    Matcher singlePagePatternMatcher = pattern.matcher(value);
    if (singlePagePatternMatcher.find()) {
      return Integer.valueOf(singlePagePatternMatcher.group(1).replace(",", ""));
    }
    return null;
  }
}
