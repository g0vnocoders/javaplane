package javaplane.Presenters;

import java.awt.Rectangle;

import javaplane.Decorators.App;
import javaplane.Event.BBClickListener;
import javaplane.Graphics.EventManager;
import javaplane.Interactors.FuelControls;
import javaplane.Routers.ResetPlane;

public class PlanePresenter {
    private App app;//decorator
    private ResetPlane resetPlane;//router
    private FuelControls fuelControls;//interactor
    private EventManager eventManager;//event manager
    public void setDebug(Boolean debug) {
        this.eventManager.showHitBoxes = debug;
    }

    public PlanePresenter(App app, ResetPlane resetPlane, FuelControls fuelControls) {
        this.app = app;
        this.resetPlane = resetPlane;
        this.fuelControls = fuelControls;
        this.eventManager = new EventManager(this.app);
        bindDecoratorToPresenter();
        bindResetRouterToPresenter();
    }
    public void bindDecoratorToPresenter() {        
        //лівий пожежний кран
        eventManager.registerClickEvent("Лівий ПК", new Rectangle(0, 0, 100, 100), 
            new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleLeftEngine();
                    app.layerManager.toggleLayerState("1.png");
                    app.repaint();
                }
            }
        );
    }
    public void bindResetRouterToPresenter() {
        //кнопка reset
        eventManager.registerClickEvent("reset", new Rectangle(500, 0, 100, 100), 
            new BBClickListener() {
                public void onClick() {
                    resetPlane.reset(fuelControls.getPlane());
                    app.repaint();
                }
            }
        );
    }
}
