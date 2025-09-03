package org.sciborgs1155.robot.turret;

public interface TurretIO extends AutoCloseable {

  /**
   * Sets the voltage of the turret motor.
   *
   * @param power The voltage.
   */
  void setVoltage(double power);

  /**
   * Returns the current position of the turret.
   *
   * @return The current position, in degrees.
   */
  double position();
}
