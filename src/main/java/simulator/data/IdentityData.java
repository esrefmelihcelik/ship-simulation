package simulator.data;

import java.io.Serializable;

public class IdentityData implements Serializable {
  private static final long serialVersionUID = 1L;

  private final String imo;
  private final String mmsi;
  private final String callSign;
  private final String shipName;
  private final String shipType;

  public IdentityData(String imo, String mmsi, String callSign, String shipName, String shipType) {
    this.imo = imo;
    this.mmsi = mmsi;
    this.callSign = callSign;
    this.shipName = shipName;
    this.shipType = shipType;
  }

  // Getters
  public String getImo() { return imo; }
  public String getMmsi() { return mmsi; }
  public String getCallSign() { return callSign; }
  public String getShipName() { return shipName; }
  public String getShipType() { return shipType; }

  @Override
  public String toString() {
    return String.format("Identity[IMO:%s, MMSI:%s, CallSign:%s, Name:%s, Type:%s]",
        imo, mmsi, callSign, shipName, shipType);
  }
}