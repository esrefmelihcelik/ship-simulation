package simulator.generator;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import simulator.data.IdentityData;

public class IdentityDataGenerator {

  private static final String[] SHIP_TYPES = {
      "Container Ship", "Tanker", "Bulk Carrier", "Passenger Ship",
      "Fishing Vessel", "Tugboat", "Research Vessel", "Naval Ship",
      "Cruise Ship", "Ro-Ro", "LNG Carrier", "Offshore Supply"
  };

  private static final String[] SHIP_NAMES = {
      "Atlantic Star", "Pacific Dawn", "Mediterranean Queen", "Indian Ocean",
      "Blue Whale", "Sea Explorer", "Ocean Voyager", "Maritime Spirit",
      "Trade Wind", "Horizon", "Navigator", "Pioneer",
      "Enterprise", "Challenger", "Discovery", "Endeavour"
  };

  private final Random random = ThreadLocalRandom.current();
  private int shipNameIndex = 0;
  private int shipTypeIndex = 0;

  public IdentityData generateIdentityData(int shipId) {
    return new IdentityData(
        generateIMO(shipId),
        generateMMSI(shipId),
        generateCallSign(shipId),
        generateShipName(),
        generateShipType()
    );
  }

  private String generateIMO(int shipId) {
    return String.format("IMO%07d", 9000000 + shipId);
  }

  private String generateMMSI(int shipId) {
    return String.format("%09d", 200000000 + shipId);
  }

  private String generateCallSign(int shipId) {
    return String.format("CALL%04d", shipId);
  }

  private synchronized String generateShipName() {
    String name = SHIP_NAMES[shipNameIndex % SHIP_NAMES.length] +
        " " + (shipNameIndex / SHIP_NAMES.length + 1);
    shipNameIndex++;
    return name;
  }

  private synchronized String generateShipType() {
    String type = SHIP_TYPES[shipTypeIndex % SHIP_TYPES.length];
    shipTypeIndex = (shipTypeIndex + 1) % SHIP_TYPES.length;
    return type;
  }
}