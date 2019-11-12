package by.sashnikov.jfuture.util;

import java.util.stream.Stream;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;

/**
 * @author Ilya_Sashnikau
 */
public class ProgressBarUtil {

  public static <T> Stream<T> wrapStream(Stream<T> stream, String taskName) {
    return ProgressBar.wrap(stream, progressBarBuilder(taskName));
  }

  private static ProgressBarBuilder progressBarBuilder(String taskName) {
    return new ProgressBarBuilder()
        .setStyle(ProgressBarStyle.ASCII)
        .setTaskName(taskName)
        .setUpdateIntervalMillis(1000)
        .setUnit(" Search page", 1);
  }
}
