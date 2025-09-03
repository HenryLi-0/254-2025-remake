package org.sciborgs1155.robot.turret;

public class NoTurret implements TurretIO {

  @Override
  public void setVoltage(double power) {}

  @Override
  public double position() {
    return 0.0;
  }

  @Override
  public void close() throws Exception {}
}
