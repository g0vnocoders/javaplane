package javaplane.Presenters;

import java.awt.Rectangle;

import javaplane.Decorators.App;
import javaplane.Event.BBClickListener;
import javaplane.Event.RepaintListener;
import javaplane.Graphics.EventManager;
import javaplane.Interactors.FuelControls;
import javaplane.Routers.ResetPlane;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.*;
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
    private Timer timer; //timer
    public void setDebug(Boolean debug) {
        this.eventManager.showHitBoxes = debug;
    }

    public PlanePresenter(App app, ResetPlane resetPlane, FuelControls fuelControls) {
        this.app = app;
        this.resetPlane = resetPlane;
        this.fuelControls = fuelControls;
        this.eventManager = new EventManager(this.app);
        bindDecoratorToPresenter();
        bindRouterToPresenter();
        resetPlane.reset(fuelControls.getPlane());
        resetPlane.resetView(app);
    }
    public void bindDecoratorToPresenter() {        
        //перемальовка семисегментного дисплея
        app.repaintListeners.add(new RepaintListener() {
            public void repaintRequested(java.awt.Graphics g) {
                g.drawString(String.valueOf(fuelControls.getLeftTankFuel()), 180, 850);
                g.drawString(String.valueOf(fuelControls.getRightTankFuel()), 535, 850);
            };
        });
        //start
        app.start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {
                        //похідна від часу залежно від таймеру
                        double dt = System.currentTimeMillis() - scheduledExecutionTime();
                        fuelControls.tick(dt);
                        app.repaint();
                    }
                }, 0, 1000);
            }
        });
        


        //додаємо обробники кліків на кнопки
        Controls[] controls = new Controls[] {
            new Controls("Лівий ПК кришка", new Rectangle(89, 13, 178, 67), new BBClickListener() {
                public void onClick() {
                    Boolean prev = app.layerManager.getLayerState("coverleft.png");
                    app.layerManager.toggleLayerState("coverleft.png");
                    app.layerManager.setLayerState("switchleftoff.png", prev);
                    app.layerManager.setLayerState("switchlefton.png", false);
                    app.repaint();
                }
            }),
            new Controls("Правий ПК кришка", new Rectangle(613, 13, 200, 89), new BBClickListener() {
                public void onClick() {
                    Boolean prev = app.layerManager.getLayerState("coverright.png");
                    app.layerManager.toggleLayerState("coverright.png");
                    app.layerManager.setLayerState("switchrightoff.png", prev);
                    app.layerManager.setLayerState("switchrighton.png", false);
                    app.repaint();
                }
            }),
            new Controls("Лівий ПК", new Rectangle(115, 129, 68, 171), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleLeftEngine();
                    app.layerManager.toggleLayerState("switchleftoff.png");
                    app.layerManager.toggleLayerState("switchlefton.png");
                    app.repaint();
                }
            }),
            new Controls("Правий ПК", new Rectangle(639, 129, 68, 171), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleLeftEngine();
                    app.layerManager.toggleLayerState("switchrightoff.png");
                    app.layerManager.toggleLayerState("switchrighton.png");
                    app.repaint();
                }
            }),
            new Controls("Кільцювання", new Rectangle(354, 371, 122, 117), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleRing();
                    app.layerManager.toggleLayerState("ringon.png");
                    app.repaint();
                }
            }),
            new Controls("Лівий Насос 1", new Rectangle(94, 503, 126, 122), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleLeftPump1();
                    app.layerManager.toggleLayerState("pump1on.png");
                    app.repaint();
                }
            }),
            new Controls("Лівий Насос 2", new Rectangle(254, 503, 126, 122), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleLeftPump2();
                    app.layerManager.toggleLayerState("pump2on.png");
                    app.repaint();
                }
            }),
            new Controls("Правий Насос 1", new Rectangle(445, 503, 126, 122), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleRightPump1();
                    app.layerManager.toggleLayerState("pump3on.png");
                    app.repaint();
                }
            }),
            new Controls("Правий Насос 2", new Rectangle(596, 503, 126, 122), new BBClickListener() {
                public void onClick() {
                    fuelControls.toggleRightPump2();
                    app.layerManager.toggleLayerState("pump4on.png");
                    app.repaint();
                }
            }),
        };
        
        for (Controls el : controls) {
            eventManager.registerClickEvent(el.name, el.pos, el.callback);
        }
        
    }
    public void bindRouterToPresenter() {
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
