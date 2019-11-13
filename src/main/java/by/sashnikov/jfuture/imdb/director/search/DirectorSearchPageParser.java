package by.sashnikov.jfuture.imdb.director.search;

import java.util.HashSet;
import java.util.Set;
import by.sashnikov.jfuture.imdb.ParseUtil;
import by.sashnikov.jfuture.imdb.parser.SearchPageParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Ilya_Sashnikau
 */
class DirectorSearchPageParser extends SearchPageParser {

  public DirectorSearchPageParser(String pageUrl) {
    super(pageUrl);
  }

  public Set<DirectorSearchDTO> directorsData() {
    Document document = getDocument();
    return parseDirectors(document);
  }

  private Set<DirectorSearchDTO> parseDirectors(Document document) {
    Set<DirectorSearchDTO> directors = new HashSet<>();

    Elements directorsData = document.getElementsByClass("lister-item-header");
    for (Element directorElement : directorsData) {
      Elements linkElements = directorElement.getElementsByAttribute("href");
      for (Element linkElement : linkElements) {
        String name = linkElement.text();
        String link = ParseUtil.extractPath(linkElement.attr("href"));
        String id = extractId(link);
        DirectorSearchDTO directorSearchDTO = new DirectorSearchDTO(id, name);
        directors.add(directorSearchDTO);
      }
    }
    return directors;
  }

  private String extractId(String link) {
    return link.substring(link.lastIndexOf('/') + 1);
  }
}
