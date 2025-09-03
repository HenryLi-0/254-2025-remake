package org.sciborgs1155.robot.shooter;

public final class ShooterConstants {
  public final class Flywheel {
    // not sure if it should be the opposite?
    public static final double GEARING = 14 / 72;

    // not tuned
    public static final double kP = 6.0;
    public static final double kI = 0.0;
    public static final double kD = 0.2;

    // not tuned
    public static final double kS = 0.1;
    public static final double kV = 0.1;
    public static final double kA = 0.1;
  }

  public final class Hood {
    // no clue what the pdf meant
    public static final double GEARING = 16 / 52 * 14 / 42 * 14 / 510;

    // not tuned
    public static final double kP = 7.0;
    public static final double kI = 0.0;
    public static final double kD = 0.5;

    // not tuned
    public static final double kS = 0.1;
    public static final double kV = 0.1;
    public static final double kA = 0.1;
  }
}
