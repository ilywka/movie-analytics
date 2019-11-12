package by.sashnikov.jfuture.imdb.director.page;

import java.util.HashSet;
import java.util.Set;
import by.sashnikov.jfuture.imdb.ParseUtil;
import by.sashnikov.jfuture.imdb.parser.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author Ilya_Sashnikau
 */
class DirectorPageParser extends Parser {


  public DirectorPageParser(String directorLink) {
    super(directorLink);
  }


  public Set<String> getMovieLinks() {
    Document document = getDocument();
    Set<String> refs = new HashSet<>();
    for (Element element : document.getAllElements()) {
      if (element.id().startsWith("director")) {
        for (Element movieLink : element.getElementsByTag("b")) {
          for (Element href : movieLink.getElementsByAttribute("href")) {
            String movieRef = href.attr("href");
            refs.add(ParseUtil.IMDB_URL.concat(ParseUtil.extractPath(movieRef)));
          }
        }
      }
    }
    return refs;
  }
}
