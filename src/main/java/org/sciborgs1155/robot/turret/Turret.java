package org.sciborgs1155.robot.turret;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Seconds;
import static org.sciborgs1155.robot.Constants.PERIOD;
import static org.sciborgs1155.robot.turret.TurretConstants.*;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import org.sciborgs1155.robot.Robot;

public class Turret extends SubsystemBase implements AutoCloseable {
  private final TurretIO hardware;

  private final ProfiledPIDController pid =
      new ProfiledPIDController(
          kP,
          kI,
          kD,
          new TrapezoidProfile.Constraints(
              MAX_VELOCITY.in(DegreesPerSecond), MAX_ACCELERATION.in(DegreesPerSecondPerSecond)));

  private final SimpleMotorFeedforward feedback = new SimpleMotorFeedforward(kS, kV, kA);

  @Logged
  private final TurretVisualizer positionVisualizer =
      new TurretVisualizer(new Color8Bit("#ff8000"));

  @Logged
  private final TurretVisualizer setpointVisualizer =
      new TurretVisualizer(new Color8Bit("#ff00ff"));

  public static Turret create() {
    return Robot.isReal() ? new Turret(new RealTurret()) : new Turret(new SimTurret());
  }

  public static Turret none() {
    return new Turret(new NoTurret());
  }

  public Turret(TurretIO hardware) {
    this.hardware = hardware;
  }

  public Command goTo(DoubleSupplier goal) {
    return Commands.run(
        () -> {
          update(goal.getAsDouble());
        });
  }

  public Command goTo(double goal) {
    return Commands.run(
        () -> {
          update(goal);
        });
  }

  public Command goTo(Supplier<Rotation2d> rotation) {
    return goTo(() -> rotation.get().getDegrees());
  }

  @Logged
  public boolean atGoal() {
    return pid.atGoal();
  }

  /**
   * Move the turret to a rotation.
   *
   * @param goal The goal rotation, in degrees.
   */
  private void update(double goal) {
    double target = MathUtil.clamp(goal, MIN_ANGLE.in(Degrees), MAX_ANGLE.in(Degrees));
    State previousSetpoint = pid.getSetpoint();
    double fb = pid.calculate(hardware.position(), target);
    double accel = (pid.getSetpoint().velocity - previousSetpoint.velocity) / PERIOD.in(Seconds);
    double ff = feedback.calculate(pid.getSetpoint().velocity, accel);
    hardware.setVoltage(fb + ff);
  }

  @Override
  public void periodic() {
    positionVisualizer.setAngle(hardware.position());
    setpointVisualizer.setAngle(pid.getGoal().position);
  }

  @Override
  public void close() throws Exception {
    hardware.close();
    positionVisualizer.close();
    setpointVisualizer.close();
  }
}
