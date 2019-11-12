package by.sashnikov.jfuture.imdb.director;

import java.util.Set;
import by.sashnikov.jfuture.imdb.Parser;
import org.jsoup.nodes.Document;

/**
 * @author Ilya_Sashnikau
 */
class DirectorSearchPageParser extends Parser {

  private Set<DirectorSearchDTO> data;

  public DirectorSearchPageParser(String pageUrl) {
    super(pageUrl);
  }

  public DirectorSearchDTO directorsData() {
    Document document = getDocument();
    return null;
  }
}
