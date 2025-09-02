package org.sciborgs1155.robot.shooter;

public interface ShooterIO extends AutoCloseable {
  /**
   * Set the voltage for the flywheel.
   *
   * @param voltage The voltage.
   */
  void setFlywheelVoltage(double voltage);

  /**
   * The velocity of the flywheel in RPM.
   *
   * @return The velocity in RPM.
   */
  double flywheelVelocity();

  /**
   * Sets the voltage for the hood.
   *
   * @param voltage The voltage.
   */
  void setHoodVoltage(double voltage);

  /**
   * The position of the hood in rotations.
   *
   * @retur The position in rotations.
   */
  double hoodPosition();
}
