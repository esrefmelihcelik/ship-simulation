package shipsimulator.service;

import com.shipsimulator.data.Identity;
import com.shipsimulator.data.Kinematic;
import com.shipsimulator.data.Ship;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ShipManager {

  private final Map<Long, Ship> ships;
  private final List<String> shipNames;
  private final Random random;

  public ShipManager(List<String> shipNames) {
    this.ships = new ConcurrentHashMap<>();
    this.shipNames = new ArrayList<>(shipNames);
    this.random = new Random();
  }

  public void generateInitialShips(int numberOfShips) {
    ships.clear();

    for (int i = 0; i < numberOfShips; i++) {
      long shipId = generateUniqueShipId();
      String shipName = getRandomShipName();

      Identity identity = Identity.createRandom(shipName);
      Kinematic kinematic = Kinematic.createRandom();

      Ship ship = new Ship(shipId, identity, kinematic);
      ships.put(shipId, ship);
    }
  }

  public void updateShips(double kinematicUpdatePercent, double identityUpdatePercent) {
    ships.values().parallelStream().forEach(ship -> {
      // Update kinematic data based on percentage
      if (random.nextDouble() * 100 < kinematicUpdatePercent) {
        ship.getKinematic().updateRandomly();
      }

      // Update identity data based on percentage (less frequent)
      if (random.nextDouble() * 100 < identityUpdatePercent) {
        updateIdentityRandomly(ship.getIdentity());
      }
    });
  }

  private void updateIdentityRandomly(Identity identity) {
    // Only update certain fields randomly to maintain realism
    if (random.nextDouble() < 0.3) { // 30% chance to update call sign
      identity.setCallSign(Identity.createRandom(null).getCallSign());
    }
  }

  private long generateUniqueShipId() {
    long shipId;
    do {
      shipId = random.nextInt(99000) + 1000L; // Between 1000 and 100000
    } while (ships.containsKey(shipId));
    return shipId;
  }

  private String getRandomShipName() {
    if (shipNames.isEmpty()) {
      return "Unknown Ship";
    }
    return shipNames.get(random.nextInt(shipNames.size()));
  }

  public Map<Long, Ship> getShips() {
    return Collections.unmodifiableMap(ships);
  }

  public int getShipCount() {
    return ships.size();
  }

  public Ship getShip(long shipId) {
    return ships.get(shipId);
  }
}