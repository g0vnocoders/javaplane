package javaplane.Presenters;

import java.awt.Rectangle;

import javaplane.Audio.AudioManager;
import javaplane.Decorators.App;
import javaplane.Event.BBClickListener;
import javaplane.Event.RepaintListener;
import javaplane.Graphics.EventManager;
import javaplane.Graphics.Gauge;
import javaplane.Interactors.FuelControls;
import javaplane.Interactors.FuelControlsStrict;
import javaplane.Routers.RandomFuel;
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
    private RandomFuel randomFuel;//router
    private FuelControls fuelControls;//interactor
    private EventManager eventManager;//event manager
    private Timer timer; //timer
    public void setDebug(Boolean debug) {
        this.eventManager.showHitBoxes = debug;
    }

    public PlanePresenter(App app, ResetPlane resetPlane, FuelControls fuelControls) {
        this.app = app;
        this.resetPlane = resetPlane;
        this.randomFuel = new RandomFuel();
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
                g.setColor(java.awt.Color.BLACK);
                Gauge.drawCenteredString(g, fuelControls.getLeftTankFuel(), new Rectangle(205, 820, 40, 80));
                Gauge.drawCenteredString(g, fuelControls.getRightTankFuel(), new Rectangle(558, 820, 40, 80));


                double left = fuelControls.getPlane().leftTank.fuel / fuelControls.getPlane().leftTank.capacity;
                double right = fuelControls.getPlane().rightTank.fuel / fuelControls.getPlane().rightTank.capacity;
                Gauge.draw(g, 102, 658, left, app.layerManager.layers.get("gauge.png"));
                Gauge.draw(g, 451, 658, right, app.layerManager.layers.get("gauge.png"));
            };
        });
        //додаємо обробники кліків на кнопки
        Controls[] controls = new Controls[] {
            new Controls("Лівий ПК кришка", new Rectangle(89, 13, 178, 67), new BBClickListener() {
                public void onClick() {
                    Boolean prev = app.layerManager.getLayerState("coverleft.png");
                    app.layerManager.toggleLayerState("coverleft.png");
                    app.layerManager.setLayerState("switchlefton.png", prev);
                    app.layerManager.setLayerState("switchleftoff.png", false);
                    app.layerManager.setLayerState("greenleft.png", true);
                    app.layerManager.setLayerState("redleft.png", false);
                    app.repaint();
                }
            }),
            new Controls("Правий ПК кришка", new Rectangle(613, 13, 200, 89), new BBClickListener() {
                public void onClick() {
                    Boolean prev = app.layerManager.getLayerState("coverright.png");
                    app.layerManager.toggleLayerState("coverright.png");
                    app.layerManager.setLayerState("switchrighton.png", prev);
                    app.layerManager.setLayerState("switchrightoff.png", false);
                    app.layerManager.setLayerState("greenright.png", true);
                    app.layerManager.setLayerState("redright.png", false);
                    app.repaint();
                }
            }),
            new Controls("Лівий ПК", new Rectangle(115, 129, 68, 171), new BBClickListener() {
                public void onClick() {
                    AudioManager.play("switch");
                    //нічого не робити якщо кришка закрита
                    if (app.layerManager.getLayerState("coverleft.png")) {
                        return;
                    }
                    fuelControls.toggleLeftEngine();
                    app.layerManager.toggleLayerState("switchleftoff.png");
                    app.layerManager.toggleLayerState("switchlefton.png");
                    app.layerManager.toggleLayerState("greenleft.png");
                    app.layerManager.toggleLayerState("redleft.png");
                    AudioManager.play("crash");
                    app.repaint();
                }
            }),
            new Controls("Правий ПК", new Rectangle(639, 129, 68, 171), new BBClickListener() {
                public void onClick() {
                    AudioManager.play("switch");
                    //нічого не робити якщо кришка закрита
                    if (app.layerManager.getLayerState("coverright.png")) {
                        return;
                    }
                    fuelControls.toggleLeftEngine();
                    app.layerManager.toggleLayerState("switchrightoff.png");
                    app.layerManager.toggleLayerState("switchrighton.png");
                    app.layerManager.toggleLayerState("greenright.png");
                    app.layerManager.toggleLayerState("redright.png");
                    AudioManager.play("crash");
                    app.repaint();
                }
            }),
            new Controls("Кільцювання", new Rectangle(354, 371, 122, 117), new BBClickListener() {
                public void onClick() {
                    AudioManager.play("click");
                    fuelControls.toggleRing();
                    app.layerManager.toggleLayerState("ringon.png");
                    app.repaint();
                }
            }),
            new Controls("Лівий Насос 1", new Rectangle(94, 503, 126, 122), new BBClickListener() {
                public void onClick() {
                    AudioManager.play("click");
                    fuelControls.toggleLeftPump1();
                    app.layerManager.toggleLayerState("pump1on.png");
                    app.repaint();
                }
            }),
            new Controls("Лівий Насос 2", new Rectangle(254, 503, 126, 122), new BBClickListener() {
                public void onClick() {
                    AudioManager.play("click");
                    fuelControls.toggleLeftPump2();
                    app.layerManager.toggleLayerState("pump2on.png");
                    app.repaint();
                }
            }),
            new Controls("Правий Насос 1", new Rectangle(445, 503, 126, 122), new BBClickListener() {
                public void onClick() {
                    AudioManager.play("click");
                    fuelControls.toggleRightPump1();
                    app.layerManager.toggleLayerState("pump3on.png");
                    app.repaint();        
                }
            }),
            new Controls("Правий Насос 2", new Rectangle(596, 503, 126, 122), new BBClickListener() {
                public void onClick() {
                    AudioManager.play("click");
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
    private void resetApplication(){
        app.errText.setText("");
        try{
            timer.cancel();
        } catch (Exception ex) {}
        resetPlane.reset(fuelControls.getPlane());
        resetPlane.resetView(app);
        randomFuel.reset(fuelControls.getPlane());
        fuelControls.reset();
    }
    private void startTimer(){
        //запускаємо таймер
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public synchronized void run() {
                //похідна від часу залежно від таймеру
                double dt = System.currentTimeMillis() - scheduledExecutionTime();
                fuelControls.tick(dt);
                
                //balance/disbalance
                Boolean isDisbalance = app.layerManager.getLayerState("disbalanson.png");
                //дизбаланс став балансом
                if(isDisbalance && fuelControls.getFuelBalanceGood()){
                    app.layerManager.setLayerState("disbalanson.png", false);
                    app.layerManager.setLayerState("disbalansoff.png", true);
                }
                //баланс став дизбалансом
                if(!isDisbalance && fuelControls.getFuelDisbalance()){
                    app.layerManager.setLayerState("disbalanson.png", true);
                    app.layerManager.setLayerState("disbalansoff.png", false);
                }
                
                app.repaint();
            }
        }, 0, 1000);
    }
    public void bindRouterToPresenter() {
        //кнопка reset - ресет, не активує таймер
        eventManager.registerClickEvent("Reset", new Rectangle(850, 80, 100, 50), new BBClickListener() {
            public void onClick() {
                 resetApplication();
                //біндимо вільний інтерактор
                fuelControls = new FuelControls(fuelControls.getPlane());
                startTimer();
                app.repaint();
            }
        });
        eventManager.registerClickEvent("Start", new Rectangle(1050, 80, 100, 50), new BBClickListener() {
            public void onClick() {
                //reset 
                resetApplication();
                //біндимо строгий інтерактор
                fuelControls = new FuelControlsStrict(fuelControls.getPlane());
                //запускаємо таймер
                startTimer();
                app.repaint();
            }
        });
        
    }
}
