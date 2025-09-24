package simulator.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import simulator.data.IdentityData;
import simulator.data.KinematicData;
import simulator.data.ShipData;
import simulator.generator.IdentityDataGenerator;
import simulator.generator.KinematicDataGenerator;

public class ShipDataService {
  private  Map<Integer, ShipData> ships;
  private  List<ShipData> shipList;
  private  AtomicInteger updateCount;

  private KinematicDataGenerator kinematicGenerator;

  private IdentityDataGenerator identityGenerator;

  public ShipDataService(KinematicDataGenerator kinematicGenerator, IdentityDataGenerator identityGenerator) {
    this.kinematicGenerator = kinematicGenerator;
    this.identityGenerator = identityGenerator;
    // ... initialize other fields
    ships = new ConcurrentHashMap<>();
    shipList = new CopyOnWriteArrayList<>();
    updateCount = new AtomicInteger(0);
  }

  public void initializeShips(int numberOfShips) {
    ships.clear();
    shipList.clear();
    updateCount.set(0);

    for (int i = 1; i <= numberOfShips; i++) {
      KinematicData kinematic = kinematicGenerator.generateInitialData();
      IdentityData identity = identityGenerator.generateIdentityData(i);
      ShipData ship = new ShipData(i, kinematic, identity);

      ships.put(i, ship);
      shipList.add(ship);
    }
  }

  public void updateKinematicData(int shipId) {
    ShipData ship = ships.get(shipId);
    if (ship != null) {
      KinematicData newKinematic = kinematicGenerator.generateUpdate(ship.getKinematicData());
      ship.updateKinematicData(newKinematic);
      updateCount.incrementAndGet();
    }
  }

  public void updateIdentityData(int shipId) {
    ShipData ship = ships.get(shipId);
    if (ship != null) {
      IdentityData newIdentity = identityGenerator.generateIdentityData(shipId);
      ship.updateIdentityData(newIdentity);
      updateCount.incrementAndGet();
    }
  }

  public ShipData getShip(int shipId) {
    return ships.get(shipId);
  }

  public List<ShipData> getAllShips() {
    return shipList;
  }

  public int getShipCount() {
    return ships.size();
  }

  public int getTotalUpdates() {
    return updateCount.get();
  }
}