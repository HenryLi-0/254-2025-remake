package org.sciborgs1155.robot.turret;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Rotations;
import static org.sciborgs1155.robot.Ports.Turret.*;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.hardware.TalonFX;

public class RealTurret implements TurretIO {
  private final TalonFX motor;

  public RealTurret() {
    motor = new TalonFX(MOTOR);

    motor
        .getConfigurator()
        .apply(
            new FeedbackConfigs()
                .withSensorToMechanismRatio(TurretConstants.GEARING)
                .withFeedbackRotorOffset(TurretConstants.STARTING_ANGLE.in(Rotations)));
  }

  @Override
  public void setVoltage(double power) {
    motor.setVoltage(power);
  }

  @Override
  public double position() {
    return motor.getPosition().getValue().in(Degrees);
  }

  @Override
  public void close() throws Exception {
    motor.close();
  }
}
