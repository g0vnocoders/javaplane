package javaplane.Routers;

import javaplane.Decorators.App;
import javaplane.Objects.Plane;

public class ResetPlane implements Router {

    public void reset(Plane plane) {
        plane.isRingOn = false;
        plane.leftTank.fuel = plane.leftTank.capacity;
        plane.rightTank.fuel = plane.rightTank.capacity;
        plane.leftPump.isOn = true;
        plane.leftPump2.isOn = true;
        plane.rightPump.isOn = true;
        plane.rightPump2.isOn = true;

        plane.rightEngineValve = true;
        plane.leftEngineValve = true;
    }
    public void resetView(App app){
        app.layerManager.disableAllLayers();
        app.layerManager.setLayerState("coverleft.png", true);
        app.layerManager.setLayerState("coverright.png", true);
        //двигуни увімкнені
        app.layerManager.setLayerState("greenleft.png", true);
        app.layerManager.setLayerState("greenright.png", true);
        //насоси увімкнені
        app.layerManager.setLayerState("pump1on.png", true);
        app.layerManager.setLayerState("pump2on.png", true);
        app.layerManager.setLayerState("pump3on.png", true);
        app.layerManager.setLayerState("pump4on.png", true);

        app.repaint();
    }
    
}
