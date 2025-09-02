package org.sciborgs1155.robot.shooter;

import static org.sciborgs1155.robot.Ports.Shooter.*;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

public class RealShooter implements ShooterIO {
  private final TalonFX topShooter;
  private final TalonFX bottomShooter;
  private final TalonFX hood;

  public RealShooter() {
    topShooter = new TalonFX(TOP_SHOOTER);
    bottomShooter = new TalonFX(BOTTOM_SHOOTER);

    bottomShooter.setControl(new Follower(TOP_SHOOTER, true));

    hood = new TalonFX(HOOD);
  }

  @Override
  public void setFlywheelVoltage(double voltage) {
    topShooter.setVoltage(voltage);
  }

  @Override
  public double flywheelVelocity() {
    return topShooter.getVelocity().getValueAsDouble();
  }

  @Override
  public void setHoodVoltage(double voltage) {
    hood.setVoltage(voltage);
  }

  @Override
  public double hoodPosition() {
    return hood.getPosition().getValueAsDouble();
  }

  @Override
  public void close() throws Exception {
    topShooter.close();
    bottomShooter.close();
    hood.close();
  }
}
