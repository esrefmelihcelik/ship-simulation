package simulator.service;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class DataStreamSimulator {
  private ScheduledExecutorService scheduler;
  private ScheduledFuture<?> simulationTask;
  private final AtomicBoolean running = new AtomicBoolean(false);
  private final Random random = new Random();

  private final ShipDataService shipDataService;

  private int kinematicUpdatePercentage = 30;
  private int identityUpdatePercentage = 5;
  private int updateIntervalMs = 1000;

  public DataStreamSimulator(ShipDataService shipDataService) {
    this.shipDataService = shipDataService;
  }

  public void startSimulation(int numberOfShips) {
    // Stop any existing simulation first
    stopSimulation();

    // Create a new scheduler for this simulation
    scheduler = Executors.newScheduledThreadPool(2);
    shipDataService.initializeShips(numberOfShips);
    running.set(true);

    // Schedule the simulation task
    simulationTask = scheduler.scheduleAtFixedRate(
        this::simulateDataStream,
        0,
        updateIntervalMs,
        TimeUnit.MILLISECONDS
    );

    System.out.println("Simulation started with " + numberOfShips + " ships");
    System.out.println("Update interval: " + updateIntervalMs + "ms");
    System.out.println("Kinematic updates: " + kinematicUpdatePercentage + "% of ships");
    System.out.println("Identity updates: " + identityUpdatePercentage + "% of ships");
  }

  public void stopSimulation() {
    running.set(false);

    // Cancel the scheduled task
    if (simulationTask != null) {
      simulationTask.cancel(false);
      simulationTask = null;
    }

    // Shutdown the scheduler
    if (scheduler != null) {
      scheduler.shutdown();
      try {
        if (!scheduler.awaitTermination(2, TimeUnit.SECONDS)) {
          scheduler.shutdownNow();
        }
      } catch (InterruptedException e) {
        scheduler.shutdownNow();
        Thread.currentThread().interrupt();
      }
      scheduler = null;
    }

    System.out.println("Simulation stopped");
  }

  private void simulateDataStream() {
    if (!running.get()) return;

    try {
      int shipCount = shipDataService.getShipCount();
      if (shipCount == 0) return;

      // Update kinematic data for random ships
      int kinematicUpdates = Math.max(1, (shipCount * kinematicUpdatePercentage) / 100);
      for (int i = 0; i < kinematicUpdates; i++) {
        int randomShipId = random.nextInt(shipCount) + 1;
        shipDataService.updateKinematicData(randomShipId);
      }

      // Update identity data for random ships (less frequent)
      if (random.nextInt(100) < 10) { // 10% chance to update identities
        int identityUpdates = Math.max(1, (shipCount * identityUpdatePercentage) / 1000);
        for (int i = 0; i < identityUpdates; i++) {
          int randomShipId = random.nextInt(shipCount) + 1;
          shipDataService.updateIdentityData(randomShipId);
        }
      }

      // Print statistics every 10 seconds
      if (System.currentTimeMillis() % 10000 < updateIntervalMs) {
        printStatistics();
      }
    } catch (Exception e) {
      // Log any exceptions but don't break the simulation
      System.err.println("Error in simulation: " + e.getMessage());
    }
  }

  private void printStatistics() {
    System.out.printf("[Stats] Active ships: %d, Total updates: %d%n",
        shipDataService.getShipCount(),
        shipDataService.getTotalUpdates());
  }

  // Configuration methods
  public void setKinematicUpdatePercentage(int percentage) {
    this.kinematicUpdatePercentage = Math.max(0, Math.min(100, percentage));
  }

  public void setIdentityUpdatePercentage(int percentage) {
    this.identityUpdatePercentage = Math.max(0, Math.min(100, percentage));
  }

  public void setUpdateIntervalMs(int intervalMs) {
    this.updateIntervalMs = Math.max(100, intervalMs);
  }

  public boolean isRunning() {
    return running.get();
  }

  // Add a cleanup method for application shutdown
  public void cleanup() {
    stopSimulation();
  }
}