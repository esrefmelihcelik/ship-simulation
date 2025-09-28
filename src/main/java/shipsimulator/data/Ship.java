package shipsimulator.data;

public class Ship {

  private long shipId; // between 1000 and 100000
  private Identity identity; // cannot be empty
  private Kinematic kinematic; // cannot be empty

  public Ship(long shipId) {
    this.shipId = shipId;
  }

  public Ship(long shipId, Identity identity, Kinematic kinematic) {
    this.shipId = shipId;
    this.identity = identity;
    this.kinematic = kinematic;
  }

  public long getShipId() {
    return shipId;
  }

  public void setShipId(long shipId) {
    this.shipId = shipId;
  }

  public Identity getIdentity() {
    return identity;
  }

  public void setIdentity(Identity identity) {
    this.identity = identity;
  }

  public Kinematic getKinematic() {
    return kinematic;
  }

  public void setKinematic(Kinematic kinematic) {
    this.kinematic = kinematic;
  }

}