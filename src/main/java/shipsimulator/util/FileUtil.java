package shipsimulator.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUtil {

  public static List<String> readShipNames(String filePath) {
    List<String> shipNames = new ArrayList<>();
    try {
      Path path = Path.of(
          Objects.requireNonNull(FileUtil.class.getClassLoader().getResource(filePath)).toURI());
      shipNames = Files.readAllLines(path);
      // Filter out empty lines and trim names
      shipNames.removeIf(name -> name.trim().isEmpty());
    } catch (IOException e) {
      System.err.println("Error reading ship names file: " + e.getMessage());
      // Add some default names if file reading fails
      shipNames.add("Ocean Voyager");
      shipNames.add("Sea Explorer");
      shipNames.add("Marine Carrier");
    } catch (URISyntaxException e) {
      System.err.println("Error reading ship names file: " + e.getMessage());
    }
    return shipNames;
  }
}