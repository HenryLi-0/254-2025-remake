package org.sciborgs1155.robot.turret;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class TurretVisualizer implements Sendable, AutoCloseable {
  private final Mechanism2d mech;
  private final MechanismRoot2d root;
  private final MechanismLigament2d turret;

  public TurretVisualizer(Color8Bit color) {
    mech = new Mechanism2d(3, 3);
    root = mech.getRoot("Center", 1.5, 1.5);
    turret = root.append(new MechanismLigament2d("Turret", 90, 0, 5, color));
  }

  /**
   * Set the turret visualizer's angle.
   *
   * @param angle The angle, in degrees.
   */
  public void setAngle(double angle) {
    turret.setAngle(angle);
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    mech.initSendable(builder);
  }

  @Override
  public void close() throws Exception {
    mech.close();
  }
}
