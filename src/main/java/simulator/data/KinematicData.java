package simulator.data;

import java.io.Serializable;

public class KinematicData implements Serializable {
  private static final long serialVersionUID = 1L;

  private final double speed; // m/s
  private final double course; // degrees
  private final double latitude; // decimal degrees
  private final double longitude; // decimal degrees

  public KinematicData(double speed, double course, double latitude, double longitude) {
    this.speed = speed;
    this.course = course;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  // Getters
  public double getSpeed() { return speed; }
  public double getCourse() { return course; }
  public double getLatitude() { return latitude; }
  public double getLongitude() { return longitude; }

  @Override
  public String toString() {
    return String.format("Kinematic[Speed:%.2f m/s, Course:%.1f°, Lat:%.6f, Lon:%.6f]",
        speed, course, latitude, longitude);
  }
}