package org.sciborgs1155.robot.turret;

public class SimTurret implements TurretIO {
  private double position = 0;

  @Override
  public void setVoltage(double power) {
    position += power;
  }

  @Override
  public double position() {
    return position;
  }

  @Override
  public void close() throws Exception {}
}
