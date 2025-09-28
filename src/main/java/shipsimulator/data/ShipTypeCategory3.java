package shipsimulator.data;

public enum ShipTypeCategory3 {

  TANKER_1(ShipTypeCategory2.TANKER), TANKER_2(ShipTypeCategory2.TANKER),
  CARGO_1(ShipTypeCategory2.CARGO), CARGO_2(ShipTypeCategory2.CARGO),
  FISHING_1(ShipTypeCategory2.FISHING), FISHING_2(ShipTypeCategory2.FISHING),
  PASSENGER_1(ShipTypeCategory2.PASSENGER), PASSENGER_2(ShipTypeCategory2.PASSENGER),
  SUBMARINE_1(ShipTypeCategory2.SUBMARINE), SUBMARINE_2(ShipTypeCategory2.SUBMARINE);

  private final ShipTypeCategory2 shipTypeCategory2;

  ShipTypeCategory3(ShipTypeCategory2 shipTypeCategory2) {
    this.shipTypeCategory2 = shipTypeCategory2;
  }

  public ShipTypeCategory2 getShipTypeCategory2() {
    return shipTypeCategory2;
  }
}
