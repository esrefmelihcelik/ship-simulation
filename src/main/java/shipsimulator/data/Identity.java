package shipsimulator.data;

import java.util.Random;

public class Identity {

  private String shipName;
  private int imoNumber;
  private int mmsiNumber;
  private String callSign;
  private ShipTypeCategory1 shipTypeCategory1;
  private ShipTypeCategory2 shipTypeCategory2;
  private ShipTypeCategory3 shipTypeCategory3;

  public Identity(String shipName, int imoNumber, int mmsiNumber, String callSign,
      ShipTypeCategory1 shipTypeCategory1, ShipTypeCategory2 shipTypeCategory2,
      ShipTypeCategory3 shipTypeCategory3) {
    setShipName(shipName);
    setImoNumber(imoNumber);
    setMmsiNumber(mmsiNumber);
    setCallSign(callSign);
    this.shipTypeCategory1 = shipTypeCategory1;
    this.shipTypeCategory2 = shipTypeCategory2;
    this.shipTypeCategory3 = shipTypeCategory3;
  }

  // Static factory method for random identity
  public static Identity createRandom(String shipName) {
    Random random = new Random();

    // Generate random numbers
    int imo = random.nextInt(9000000) + 1000000; // 7 digits
    int mmsi = random.nextInt(900000000) + 100000000; // 9 digits

    // Generate random call sign (2-5 characters)
    String callSign = generateRandomCallSign();

    // Random ship type hierarchy
    ShipTypeCategory2[] category2Values = ShipTypeCategory2.values();
    ShipTypeCategory2 randomCategory2 = category2Values[random.nextInt(category2Values.length)];
    ShipTypeCategory1 category1 = randomCategory2.getShipTypeCategory1();

    // Get compatible category3
    ShipTypeCategory3[] allCategory3 = ShipTypeCategory3.values();
    ShipTypeCategory3 compatibleCategory3 = null;
    for (ShipTypeCategory3 cat3 : allCategory3) {
      if (cat3.getShipTypeCategory2() == randomCategory2) {
        compatibleCategory3 = cat3;
        break;
      }
    }

    return new Identity(shipName, imo, mmsi, callSign, category1, randomCategory2,
        compatibleCategory3);
  }

  private static String generateRandomCallSign() {
    Random random = new Random();
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    int length = random.nextInt(4) + 2; // 2-5 characters
    StringBuilder callSign = new StringBuilder();
    for (int i = 0; i < length; i++) {
      callSign.append(chars.charAt(random.nextInt(chars.length())));
    }
    return callSign.toString();
  }

  // Getters and setters with validation
  public String getShipName() {
    return shipName;
  }

  public void setShipName(String shipName) {
    if (shipName != null && shipName.length() > 50) {
      throw new IllegalArgumentException("Ship name cannot exceed 50 characters");
    }
    this.shipName = shipName;
  }

  public int getImoNumber() {
    return imoNumber;
  }

  public void setImoNumber(int imoNumber) {
    if (imoNumber != 0 && (imoNumber < 1000000 || imoNumber > 9999999)) {
      throw new IllegalArgumentException("IMO number must be 7 digits");
    }
    this.imoNumber = imoNumber;
  }

  public int getMmsiNumber() {
    return mmsiNumber;
  }

  public void setMmsiNumber(int mmsiNumber) {
    if (mmsiNumber != 0 && (mmsiNumber < 100000000 || mmsiNumber > 999999999)) {
      throw new IllegalArgumentException("MMSI number must be 9 digits");
    }
    this.mmsiNumber = mmsiNumber;
  }

  public String getCallSign() {
    return callSign;
  }

  public void setCallSign(String callSign) {
    if (callSign != null && callSign.length() > 5) {
      throw new IllegalArgumentException("Call sign cannot exceed 5 characters");
    }
    this.callSign = callSign;
  }

  public ShipTypeCategory1 getShipTypeCategory1() {
    return shipTypeCategory1;
  }

  public void setShipTypeCategory1(ShipTypeCategory1 shipTypeCategory1) {
    this.shipTypeCategory1 = shipTypeCategory1;
  }

  public ShipTypeCategory2 getShipTypeCategory2() {
    return shipTypeCategory2;
  }

  public void setShipTypeCategory2(ShipTypeCategory2 shipTypeCategory2) {
    this.shipTypeCategory2 = shipTypeCategory2;
  }

  public ShipTypeCategory3 getShipTypeCategory3() {
    return shipTypeCategory3;
  }

  public void setShipTypeCategory3(ShipTypeCategory3 shipTypeCategory3) {
    this.shipTypeCategory3 = shipTypeCategory3;
  }

  @Override
  public String toString() {
    return String.format("Ship{name='%s', IMO=%d, MMSI=%d, CallSign='%s', Type=%s}",
        shipName, imoNumber, mmsiNumber, callSign, shipTypeCategory1);
  }
}