package by.sashnikov.jfuture;

import java.io.IOException;
import by.sashnikov.jfuture.plot.JfreePlot;

public class App {
  public static void main(String[] args) throws IOException {
    JfreePlot.createChartsDirectory();
    MovieReleaseDynamics.createDynamicsCharts();
    TopDirectors.createCharts();
  }
}