package javaplane.Presenters;

import java.awt.Rectangle;

import javaplane.Decorators.App;
import javaplane.Event.BBClickListener;
import javaplane.Graphics.EventManager;
import javaplane.Interactors.FuelControls;
import javaplane.Routers.ResetPlane;

class Controls {
    public String name;
    public Rectangle pos;
    public BBClickListener callback;
    public Controls(String name, Rectangle pos, BBClickListener callback) {
        this.name = name;
        this.pos = pos;
        this.callback = callback;
    }
}
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
        //лівий пожежний кран кришка
        Controls[] controls = new Controls[] {
            new Controls("Лівий ПК кришка", new Rectangle(89, 13, 178, 67), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleLeftEngine();
                    app.layerManager.toggleLayerState("1.png");
                    app.repaint();
                }
            }),
            new Controls("Правий ПК кришка", new Rectangle(613, 13, 200, 89), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleLeftEngine();
                    app.layerManager.toggleLayerState("1.png");
                    app.repaint();
                }
            }),
            new Controls("Лівий ПК", new Rectangle(115, 129, 68, 171), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleLeftEngine();
                    app.layerManager.toggleLayerState("1.png");
                    app.repaint();
                }
            }),
            new Controls("Правий ПК", new Rectangle(639, 129, 68, 171), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleLeftEngine();
                    app.layerManager.toggleLayerState("1.png");
                    app.repaint();
                }
            }),
            new Controls("Кільцювання", new Rectangle(354, 371, 122, 117), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleLeftEngine();
                    app.layerManager.toggleLayerState("1.png");
                    app.repaint();
                }
            }),
            new Controls("Лівий Насос 1", new Rectangle(94, 503, 126, 122), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleLeftEngine();
                    app.layerManager.toggleLayerState("1.png");
                    app.repaint();
                }
            }),
            new Controls("Лівий Насос 2", new Rectangle(254, 503, 126, 122), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleLeftEngine();
                    app.layerManager.toggleLayerState("1.png");
                    app.repaint();
                }
            }),
            new Controls("Правий Насос 1", new Rectangle(445, 503, 126, 122), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleLeftEngine();
                    app.layerManager.toggleLayerState("1.png");
                    app.repaint();
                }
            }),
            new Controls("Правий Насос 2", new Rectangle(596, 503, 126, 122), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleLeftEngine();
                    app.layerManager.toggleLayerState("1.png");
                    app.repaint();
                }
            }),
        };
        
        for (Controls el : controls) {
            eventManager.registerClickEvent(el.name, el.pos, el.callback);
        }
        
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
