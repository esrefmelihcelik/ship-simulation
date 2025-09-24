package simulator.generator;


import java.util.ArrayList;
import java.util.List;
import simulator.data.IdentityData;
import simulator.data.KinematicData;
import simulator.data.ShipData;

public class ShipDataGenerator {

  private KinematicDataGenerator kinematicGenerator;

  private IdentityDataGenerator identityGenerator;

  public ShipDataGenerator(KinematicDataGenerator kinematicGenerator, IdentityDataGenerator identityGenerator) {
    this.kinematicGenerator = kinematicGenerator;
    this.identityGenerator = identityGenerator;
  }

  public List<ShipData> generateInitialShips(int numberOfShips) {
    List<ShipData> ships = new ArrayList<>(numberOfShips);

    for (int i = 1; i <= numberOfShips; i++) {
      KinematicData kinematic = kinematicGenerator.generateInitialData();
      IdentityData identity = identityGenerator.generateIdentityData(i);

      ships.add(new ShipData(i, kinematic, identity));
    }

    return ships;
  }
}