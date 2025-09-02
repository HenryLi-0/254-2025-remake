package org.sciborgs1155.robot.intake;

import static org.sciborgs1155.robot.intake.IntakeConstants.*;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import org.sciborgs1155.robot.Robot;

public class Intake {
  private final IntakeIO hardware;

  public static Intake create(boolean front) {
    return Robot.isReal() ? new Intake(new RealIntake(front)) : new Intake(new NoIntake());
  }

  public Intake(IntakeIO hardware) {
    this.hardware = hardware;
  }

  /** Stows the 4-bar intake and stops the intake.. */
  public Command stow() {
    return Commands.run(() -> hardware.stow()).alongWith(stopIntake());
  }

  /** Deploys the 4-bar intake. */
  public Command deploy() {
    return Commands.run(() -> hardware.deploy());
  }

  /** Intakes. */
  public Command intake() {
    return Commands.run(() -> hardware.setPower(INTAKE_SPEED));
  }

  /** Stops the intake. */
  public Command stopIntake() {
    return Commands.run(() -> hardware.setPower(0));
  }
}
