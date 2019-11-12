package by.sashnikov.jfuture.imdb;

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
    return document == null ? (document = ParseUtil.getDocumentQuietly(pageUrl)) : document;
  }
}
