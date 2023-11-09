package javaplane.Interactors;

import javaplane.Objects.Plane;
import javaplane.Presenters.PlanePresenter;

public class FuelControls {
    private Plane plane;
    public FuelControls(Plane plane) {
        this.plane = plane;
    }
    public void toggleRing(){
        plane.isRingOn = !plane.isRingOn;
    }
    public void toggleLeftPump1(){
        plane.leftPump.isOn = !plane.leftPump.isOn;
    }
    public void toggleLeftPump2(){
        plane.leftPump2.isOn = !plane.leftPump2.isOn;
    }
    public void toggleRightPump1(){
        plane.rightPump.isOn = !plane.rightPump.isOn;
    }
    public void toggleRightPump2(){
        plane.rightPump2.isOn = !plane.rightPump2.isOn;
    }
    public void toggleLeftEngine(){
        if(leftEngineCover)
        plane.leftEngineValve = !plane.leftEngineValve;
    }
    public void toggleRightEngine(){
        if(rightEngineCover)
        plane.rightEngineValve = !plane.rightEngineValve;
    }

    public void tick(double dt) {
        plane.tick(dt);
    }

    //геттери
    public Boolean getFuelDisbalance() {
        return plane.getFuelDisbalance();
    }
    public Boolean getFuelBalanceGood() {
        return plane.getFuelBalanceGood();
    }
    public double getLeftTankFuel() {
        return plane.leftTank.fuel;
    }
    public double getRightTankFuel() {
        return plane.rightTank.fuel;
    }
    public Boolean getLeftPump1() {
        return plane.leftPump.isOn;
    }
    public Boolean getLeftPump2() {
        return plane.leftPump2.isOn;
    }
    public Boolean getRightPump1() {
        return plane.rightPump.isOn;
    }
    public Boolean getRightPump2() {
        return plane.rightPump2.isOn;
    }
    public Boolean getLeftEngine() {
        return plane.leftEngineValve;
    }
    public Boolean getRightEngine() {
        return plane.rightEngineValve;
    }
    public Boolean getRing() {
        return plane.isRingOn;
    }

    //кришечки на пожежних кранах
    private Boolean leftEngineCover = false;
    private Boolean rightEngineCover = false;
    public void toggleLeftEngineCover(){
        leftEngineCover = !leftEngineCover;
    }
    public void toggleRightEngineCover(){
        rightEngineCover = !rightEngineCover;
    }
    public Boolean getLeftEngineCover() {
        return leftEngineCover;
    }
    public Boolean getRightEngineCover() {
        return rightEngineCover;
    }
    //використовувати тільки для тестів
    public Plane getPlane() {
        return plane;
    }
}
