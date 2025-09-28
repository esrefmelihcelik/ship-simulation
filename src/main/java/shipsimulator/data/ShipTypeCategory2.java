package shipsimulator.data;

public enum ShipTypeCategory2 {

  TANKER(ShipTypeCategory1.SURFACE),
  CARGO(ShipTypeCategory1.SURFACE),
  FISHING(ShipTypeCategory1.SURFACE),
  PASSENGER(ShipTypeCategory1.SURFACE),
  SUBMARINE(ShipTypeCategory1.SUBSURFACE);

  private final ShipTypeCategory1 shipTypeCategory1;

  ShipTypeCategory2(ShipTypeCategory1 shipTypeCategory1) {
    this.shipTypeCategory1 = shipTypeCategory1;
  }

  public ShipTypeCategory1 getShipTypeCategory1() {
    return shipTypeCategory1;
  }
}
