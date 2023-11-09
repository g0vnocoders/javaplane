package javaplane.Routers;

import javaplane.Objects.Plane;

public class ResetPlane implements Router {

    public void reset(Plane plane) {
        plane.isRingOn = false;
        plane.leftTank.fuel = plane.leftTank.capacity;
        plane.rightTank.fuel = plane.rightTank.capacity;
        plane.leftPump.isOn = false;
        plane.leftPump2.isOn = false;
        plane.rightPump.isOn = false;
        plane.rightPump2.isOn = false;

        plane.rightEngineValve = true;
        plane.leftEngineValve = true;
    }
    
}
