package shipsimulator.data;

import java.util.Random;

public class Kinematic {

  private double speed; // m/s, between 0 and 20
  private double course; // degrees, between 0 and 360
  private double latitude; // decimal degrees, between 34.0000 and 44.0000
  private double longitude; // decimal degrees, between 23.0000 and 42.2000

  public Kinematic(double speed, double course, double latitude, double longitude) {
    setSpeed(speed);
    setCourse(course);
    setLatitude(latitude);
    setLongitude(longitude);
  }

  // Static factory method for random kinematic data
  public static Kinematic createRandom() {
    Random random = new Random();
    double speed = random.nextDouble() * 20.0;
    double course = random.nextDouble() * 360.0;
    double latitude = 34.0 + random.nextDouble() * 10.0;
    double longitude = 23.0 + random.nextDouble() * 19.2;

    return new Kinematic(speed, course, latitude, longitude);
  }

  // Method to update kinematic data with small random changes
  public void updateRandomly() {
    Random random = new Random();

    // Small changes to simulate real movement
    this.speed = Math.max(0, Math.min(20, speed + (random.nextDouble() - 0.5) * 2));
    this.course = (course + (random.nextDouble() - 0.5) * 20) % 360;
    if (course < 0) {
      course += 360;
    }

    this.latitude = Math.max(34.0, Math.min(44.0, latitude + (random.nextDouble() - 0.5) * 0.01));
    this.longitude = Math.max(23.0, Math.min(42.2, longitude + (random.nextDouble() - 0.5) * 0.01));
  }

  // Getters and setters with validation
  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    if (speed < 0 || speed > 20) {
      throw new IllegalArgumentException("Speed must be between 0 and 20 m/s");
    }
    this.speed = speed;
  }

  public double getCourse() {
    return course;
  }

  public void setCourse(double course) {
    if (course < 0 || course >= 360) {
      throw new IllegalArgumentException("Course must be between 0 and 360 degrees");
    }
    this.course = course;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    if (latitude < 34.0 || latitude > 44.0) {
      throw new IllegalArgumentException("Latitude must be between 34.0000 and 44.0000");
    }
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    if (longitude < 23.0 || longitude > 42.2) {
      throw new IllegalArgumentException("Longitude must be between 23.0000 and 42.2000");
    }
    this.longitude = longitude;
  }

  @Override
  public String toString() {
    return String.format("Kinematic{speed=%.2f m/s, course=%.1f°, lat=%.4f, lon=%.4f}",
        speed, course, latitude, longitude);
  }
}