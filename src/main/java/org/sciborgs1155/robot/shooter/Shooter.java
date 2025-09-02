package org.sciborgs1155.robot.shooter;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.sciborgs1155.lib.LoggingUtils;
import org.sciborgs1155.robot.Robot;
import org.sciborgs1155.robot.shooter.ShooterConstants.Flywheel;

public class Shooter extends SubsystemBase {
  private final ShooterIO hardware;
  private final PIDController flywheelPIDController;
  private final SimpleMotorFeedforward flywheelFF;

  public static Shooter create() {
    return Robot.isReal() ? new Shooter(new RealShooter()) : new Shooter(new SimShooter());
  }

  public static Shooter none() {
    return new Shooter(new NoShooter());
  }

  public Shooter(ShooterIO hardware) {
    this.hardware = hardware;
    flywheelPIDController = new PIDController(Flywheel.kP, Flywheel.kI, Flywheel.kD);
    flywheelFF = new SimpleMotorFeedforward(Flywheel.kS, Flywheel.kV, Flywheel.kA);
  }

  /**
   * Sets the flywheel goal velocity.
   *
   * @param goal The target velocity.
   * @return
   */
  public Command setFlywheelGoal(double goal) {
    return Commands.sequence(
        Commands.runOnce(() -> setFlywheelSetpoint(goal)), Commands.run(() -> flywheelUpdate()));
  }

  /** Sets the flywheel setpoint velocity. */
  private void setFlywheelSetpoint(double goal) {
    flywheelPIDController.setSetpoint(goal);
  }

  /** Updates the flywheel feedback and feedforward. */
  private void flywheelUpdate() {
    double fb = flywheelPIDController.calculate(hardware.flywheelVelocity());
    double ff = flywheelFF.calculate(flywheelPIDController.getSetpoint());
    hardware.setFlywheelVoltage(MathUtil.clamp(fb + ff, -12, 12));
    LoggingUtils.log("flywheel voltage", MathUtil.clamp(fb + ff, -12, 12));
    LoggingUtils.log("flywheel velocity actual", hardware.flywheelVelocity());
  }

  @Override
  public void periodic() {
    flywheelUpdate();
    LoggingUtils.log("flywheel velocity setpoint", flywheelPIDController.getSetpoint());
  }
}
