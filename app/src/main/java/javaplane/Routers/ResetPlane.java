package javaplane.Routers;

import javaplane.Decorators.App;
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
    public void resetView(App app){
        app.layerManager.disableAllLayers();
        app.layerManager.setLayerState("coverleft.png", true);
        app.layerManager.setLayerState("coverright.png", true);
        //початкова положення кранів
        //app.layerManager.setLayerState("switchleftoff.png", true);
        //app.layerManager.setLayerState("switchrightoff.png", true);

        app.repaint();
    }
    
}
