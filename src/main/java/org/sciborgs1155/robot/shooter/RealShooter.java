package org.sciborgs1155.robot.shooter;

import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static org.sciborgs1155.robot.Ports.Shooter.*;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import org.sciborgs1155.robot.shooter.ShooterConstants.Flywheel;
import org.sciborgs1155.robot.shooter.ShooterConstants.Hood;

public class RealShooter implements ShooterIO {
  private final TalonFX topShooter;
  private final TalonFX bottomShooter;
  private final TalonFX hood;

  public RealShooter() {
    topShooter = new TalonFX(TOP_SHOOTER);
    bottomShooter = new TalonFX(BOTTOM_SHOOTER);

    bottomShooter.setControl(new Follower(TOP_SHOOTER, true));
    topShooter
        .getConfigurator()
        .apply(new FeedbackConfigs().withSensorToMechanismRatio(Flywheel.GEARING));

    hood = new TalonFX(HOOD);
    hood.getConfigurator().apply(new FeedbackConfigs().withSensorToMechanismRatio(Hood.GEARING));
  }

  @Override
  public void setFlywheelVoltage(double voltage) {
    topShooter.setVoltage(voltage);
  }

  @Override
  public double flywheelVelocity() {
    return topShooter.getVelocity().getValue().in(RotationsPerSecond);
  }

  @Override
  public void setHoodVoltage(double voltage) {
    hood.setVoltage(voltage);
  }

  @Override
  public double hoodPosition() {
    return hood.getPosition().getValue().in(Rotations);
  }

  @Override
  public void close() throws Exception {
    topShooter.close();
    bottomShooter.close();
    hood.close();
  }
}
