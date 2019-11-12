package by.sashnikov.jfuture.imdb.parser;

import by.sashnikov.jfuture.imdb.ParseUtil;
import org.jsoup.nodes.Document;

/**
 * @author Ilya_Sashnikau
 */
public abstract class Parser {

  private final String pageUrl;
  private Document document;

  public Parser(String pageUrl) {
    this.pageUrl = pageUrl;
  }

  protected Document getDocument() {
    if (document == null) {
      document = ParseUtil.getDocumentQuietly(pageUrl);
    }
    return document;
  }
}
