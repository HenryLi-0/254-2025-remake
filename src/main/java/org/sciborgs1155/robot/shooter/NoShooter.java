package org.sciborgs1155.robot.shooter;

public class NoShooter implements ShooterIO {

  @Override
  public void setFlywheelVoltage(double voltage) {}

  @Override
  public double flywheelVelocity() {
    return 0.0;
  }

  @Override
  public void setHoodVoltage(double voltage) {}

  @Override
  public double hoodPosition() {
    return 0.0;
  }

  @Override
  public void close() throws Exception {}
}
