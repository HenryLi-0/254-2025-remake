package org.sciborgs1155.robot.shooter;

import org.sciborgs1155.robot.Robot;

public class Shooter {
    private final ShooterIO hardware;

    public static Shooter create() {
        return Robot.isReal() ? new Shooter(new RealShooter()) : new Shooter(new SimShooter());
    }

    public static Shooter none() {
        return new Shooter(new NoShooter());
    }

    public Shooter(ShooterIO hardware) {
        this.hardware = hardware;
    }

}
