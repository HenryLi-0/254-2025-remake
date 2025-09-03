package org.sciborgs1155.robot.turret;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;

public final class TurretConstants {
  public static final Angle MAX_ANGLE = Degrees.of(120);
  public static final Angle MIN_ANGLE = Degrees.of(-120);

  // not actual, also probably overkill for the purpose of a turret
  public static final AngularVelocity MAX_VELOCITY = DegreesPerSecond.of(100);
  public static final AngularAcceleration MAX_ACCELERATION = DegreesPerSecondPerSecond.of(10000);

  public static final Angle STARTING_ANGLE = Degrees.of(0);

  // not sure?
  public static final double GEARING = 1;

  // not tuned
  public static final double kP = 5.0;
  public static final double kI = 0.0;
  public static final double kD = 0.7;

  public static final double kS = 0.0;
  public static final double kV = 0.0;
  public static final double kA = 0.0;
}
