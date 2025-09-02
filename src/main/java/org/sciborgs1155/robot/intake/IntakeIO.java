package org.sciborgs1155.robot.intake;

public interface IntakeIO {

  /** Stows the 4-bar intake. */
  void stow();

  /** Deploys the 4-bar intake. */
  void deploy();

  /** Sets the ground intake roller power. */
  void setPower(double power);
}
