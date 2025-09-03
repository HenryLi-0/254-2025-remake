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
import org.sciborgs1155.robot.shooter.ShooterConstants.Hood;

public class Shooter extends SubsystemBase {
  private final ShooterIO hardware;
  private final PIDController flywheelPIDController;
  private final SimpleMotorFeedforward flywheelFF;
  private final PIDController hoodPIDController;
  private final SimpleMotorFeedforward hoodFF;

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

    hoodPIDController = new PIDController(Hood.kP, Hood.kI, Hood.kD);
    hoodFF = new SimpleMotorFeedforward(Hood.kS, Hood.kV, Hood.kA);
  }

  /**
   * Sets the flywheel goal velocity.
   *
   * @param goal The target velocity.
   */
  public Command setFlywheelGoal(double goal) {
    return Commands.runOnce(() -> setFlywheelSetpoint(goal));
  }

  /**
   * Sets the hood goal position.
   *
   * @param goal The target position.
   */
  public Command setHoodGoal(double goal) {
    return Commands.runOnce(() -> setHoodSetpoint(goal));
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

  /** Sets the hood setpoint position. */
  private void setHoodSetpoint(double goal) {
    hoodPIDController.setSetpoint(goal);
  }

  /** Updates the hood feedback and feedforward. */
  private void hoodUpdate() {
    double fb = hoodPIDController.calculate(hardware.hoodPosition());
    double ff = hoodFF.calculate(hoodPIDController.getSetpoint());
    hardware.setHoodVoltage(MathUtil.clamp(fb + ff, -12, 12));
    LoggingUtils.log("hood voltage", MathUtil.clamp(fb + ff, -12, 12));
    LoggingUtils.log("hood position actual", hardware.hoodPosition());
  }

  @Override
  public void periodic() {
    flywheelUpdate();
    hoodUpdate();
    LoggingUtils.log("flywheel velocity setpoint", flywheelPIDController.getSetpoint());
    LoggingUtils.log("hood position setpoint", hoodPIDController.getSetpoint());
  }
}
