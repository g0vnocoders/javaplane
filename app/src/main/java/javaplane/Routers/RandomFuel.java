package javaplane.Routers;

import javaplane.Objects.Plane;

public class RandomFuel implements Router {
    public void reset(Plane plane) {
        plane.leftTank.fuel = (plane.leftTank.capacity - 250) * Math.random() + 250;//200 має бути в запасі + 50 запасу
        double randDiff = (Math.random()  - 0.5) * 200;
        plane.rightTank.fuel = plane.leftTank.fuel + randDiff;
    }
}
