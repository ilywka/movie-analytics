package by.sashnikov.jfuture.imdb;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author Ilya_Sashnikau
 */
public class ParseUtil {

  public static final String IMDB_URL = "https://www.imdb.com";

  public static Document getDocumentQuietly(String url) {
    try {
      System.out.println(url);
      return Jsoup.connect(url).get();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static URIBuilder createRequestUrl(String baseUrl, NameValuePair[] parameters)
      throws URISyntaxException {
    URIBuilder uriBuilder = new URIBuilder(baseUrl);
    uriBuilder.addParameters(Arrays.asList(parameters));
    return uriBuilder;
  }
}
