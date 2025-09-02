package org.sciborgs1155.robot.shooter;

import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Seconds;
import static org.sciborgs1155.robot.Constants.PERIOD;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import org.sciborgs1155.robot.shooter.ShooterConstants.Flywheel;

// might not be needed

public class SimShooter implements ShooterIO {
  private final FlywheelSim flywheelSim;

  public SimShooter() {
    flywheelSim =
        new FlywheelSim(
            LinearSystemId.identifyVelocitySystem(Flywheel.kV, Flywheel.kA),
            DCMotor.getFalcon500(2));
  }

  @Override
  public void setFlywheelVoltage(double voltage) {
    flywheelSim.setInputVoltage(voltage);
    flywheelSim.update(PERIOD.in(Seconds));
  }

  @Override
  public double flywheelVelocity() {
    return flywheelSim.getAngularVelocity().in(RotationsPerSecond);
  }

  @Override
  public void setHoodVoltage(double voltage) {}

  @Override
  public double hoodPosition() {
    return 0.0;
  }

  @Override
  public void close() throws Exception {}
}
