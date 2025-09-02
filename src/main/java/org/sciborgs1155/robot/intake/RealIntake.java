package org.sciborgs1155.robot.intake;

import static org.sciborgs1155.robot.Ports.Intake.*;

import com.ctre.phoenix6.hardware.TalonFX;
import org.sciborgs1155.lib.FaultLogger;

public class RealIntake implements IntakeIO {
  private final TalonFX motor;

  public RealIntake(boolean front) {
    motor = new TalonFX(front ? FRONT_INTAKE : BACK_INTAKE);

    FaultLogger.register(motor);
  }

  @Override
  public void stow() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'stow'");
  }

  @Override
  public void deploy() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deploy'");
  }

  @Override
  public void setPower(double power) {
    motor.set(power);
  }
}
