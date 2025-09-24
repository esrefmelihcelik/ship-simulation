package simulator.generator;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import simulator.data.KinematicData;

public class KinematicDataGenerator {
  private static final double MIN_LATITUDE = -90.0;
  private static final double MAX_LATITUDE = 90.0;
  private static final double MIN_LONGITUDE = -180.0;
  private static final double MAX_LONGITUDE = 180.0;
  private static final double MIN_SPEED = 0.0; // m/s
  private static final double MAX_SPEED = 20.0; // m/s (~40 knots)
  private static final double MIN_COURSE = 0.0;
  private static final double MAX_COURSE = 360.0;

  private final Random random = ThreadLocalRandom.current();

  public KinematicData generateInitialData() {
    return new KinematicData(
        generateSpeed(),
        generateCourse(),
        generateLatitude(),
        generateLongitude()
    );
  }

  public KinematicData generateUpdate(KinematicData current) {
    // Small random changes to simulate movement
    double newSpeed = Math.max(0, current.getSpeed() + (random.nextDouble() - 0.5) * 2);
    double newCourse = (current.getCourse() + (random.nextDouble() - 0.5) * 10 + 360) % 360;

    // Calculate new position based on speed and course
    double[] newPosition = calculateNewPosition(
        current.getLatitude(), current.getLongitude(),
        newSpeed, newCourse
    );

    return new KinematicData(
        Math.min(newSpeed, MAX_SPEED),
        newCourse,
        newPosition[0],
        newPosition[1]
    );
  }

  private double[] calculateNewPosition(double lat, double lon, double speed, double course) {
    // Simple position calculation (approximate)
    double distance = speed * 60.0; // distance in meters for 1 minute
    double bearing = Math.toRadians(course);

    double latRad = Math.toRadians(lat);
    double lonRad = Math.toRadians(lon);

    double earthRadius = 6371000; // meters
    double delta = distance / earthRadius;

    double newLat = Math.asin(Math.sin(latRad) * Math.cos(delta) +
        Math.cos(latRad) * Math.sin(delta) * Math.cos(bearing));
    double newLon = lonRad + Math.atan2(Math.sin(bearing) * Math.sin(delta) * Math.cos(latRad),
        Math.cos(delta) - Math.sin(latRad) * Math.sin(newLat));

    newLat = Math.toDegrees(newLat);
    newLon = Math.toDegrees(newLon);

    // Normalize coordinates
    newLat = Math.max(MIN_LATITUDE, Math.min(MAX_LATITUDE, newLat));
    newLon = (newLon + 540) % 360 - 180; // Normalize longitude

    return new double[]{newLat, newLon};
  }

  private double generateSpeed() {
    return MIN_SPEED + random.nextDouble() * (MAX_SPEED - MIN_SPEED);
  }

  private double generateCourse() {
    return MIN_COURSE + random.nextDouble() * (MAX_COURSE - MIN_COURSE);
  }

  private double generateLatitude() {
    return MIN_LATITUDE + random.nextDouble() * (MAX_LATITUDE - MIN_LATITUDE);
  }

  private double generateLongitude() {
    return MIN_LONGITUDE + random.nextDouble() * (MAX_LONGITUDE - MIN_LONGITUDE);
  }
}