package simulator.data;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

public class ShipData implements Serializable {
  private static final long serialVersionUID = 1L;

  private final int shipId;
  private final AtomicReference<KinematicData> kinematicData;
  private final AtomicReference<IdentityData> identityData;
  private volatile long lastUpdateTimestamp;

  public ShipData(int shipId, KinematicData kinematicData, IdentityData identityData) {
    this.shipId = shipId;
    this.kinematicData = new AtomicReference<>(kinematicData);
    this.identityData = new AtomicReference<>(identityData);
    this.lastUpdateTimestamp = System.currentTimeMillis();
  }

  // Getters
  public int getShipId() { return shipId; }

  public KinematicData getKinematicData() {
    return kinematicData.get();
  }

  public IdentityData getIdentityData() {
    return identityData.get();
  }

  public long getLastUpdateTimestamp() {
    return lastUpdateTimestamp;
  }

  // Atomic updates for thread safety
  public boolean updateKinematicData(KinematicData newData) {
    boolean updated = kinematicData.compareAndSet(kinematicData.get(), newData);
    if (updated) {
      lastUpdateTimestamp = System.currentTimeMillis();
    }
    return updated;
  }

  public boolean updateIdentityData(IdentityData newData) {
    boolean updated = identityData.compareAndSet(identityData.get(), newData);
    if (updated) {
      lastUpdateTimestamp = System.currentTimeMillis();
    }
    return updated;
  }

  @Override
  public String toString() {
    return String.format("Ship[ID:%d, Kinematic:%s, Identity:%s]",
        shipId, kinematicData.get(), identityData.get());
  }
}