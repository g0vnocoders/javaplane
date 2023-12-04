package javaplane.Routers;

import javaplane.Decorators.App;
import javaplane.Objects.Plane;

public class RandomFuel implements Router {
    public void reset(Plane plane) {
        plane.leftTank.fuel = (plane.leftTank.capacity - 500) * Math.random() + 250;//200 має бути дизбалансом + 50 запасу
        double randDiff = (Math.random()  - 0.5) * 50;
        if(randDiff>0) 
            randDiff += 200;
        else 
            randDiff -= 200;
        
        plane.rightTank.fuel = plane.leftTank.fuel + randDiff;
    }
    public void resetView(App app){}
}
