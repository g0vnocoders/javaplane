package javaplane.Interactors;

import javaplane.Objects.Plane;

public class FuelControlsStrict extends FuelControls{
    private Plane plane;
    private int state = 0; // кроки виконання дій на симуляторі
    /*
     * 0 - початковий стан, маємо дизбаланс палива який треба усунути
     * ↓ увімкнути кран кільцювання, інші дії заборонені
     * 1 
     * ↓ відключення насосів бака з меншим паливом, відключення неправильних насосів заборонити
     * 2,3
     * ↓ увімкнення насосів. перевірка на дизбаланс палива
     * 4,5
     * ↓ закрити кран кільцювання
     * 6
     * виведення часу виконання операцій на симуляторі
     */
    public FuelControlsStrict(Plane plane) {
        super(plane);
        this.plane = plane;
    }
    //методи унікальні для строгого режиму
    //false якщо лівий, true якщо правий
    public boolean pumpToOff(){
        if(plane.leftTank.fuel > plane.rightTank.fuel)
            return false;
        else
            return true;
    }
    public void checkPumpState(){
        if(state==0) throw new IllegalStateException("Спочатку увімкніть кран кільцювання");
        if(state==5) throw new IllegalStateException("Вимкніть кран кільцювання");
    }
    //модифіковані методи вільного режиму 
    public void toggleRing(){
        if(state == 3) throw new IllegalStateException("Спочатку увімкніть насоси, а потім вимкніть кран кільцювання");
        if(state != 0 && state != 5) throw new IllegalStateException("Дизбаланс палива не усунуто");
        plane.isRingOn = !plane.isRingOn;
        state++;
    }
    public void toggleLeftPump1(){
        checkPumpState();
        if(!pumpToOff()) throw new IllegalStateException("Вимкніть насоси бака з меншим паливом");
        //
        if(state == 2 && !plane.leftPump.isOn) throw new IllegalStateException("Вимкніть інший насос");
        if(state == 4 && plane.leftPump.isOn) throw new IllegalStateException("Увімкніть інший насос");
        
        if(state == 3 && !plane.getFuelBalanceGood()) throw new IllegalStateException("Дизбаланс палива не усунуто, насос вимкнутий зарано");
        plane.leftPump.isOn = !plane.leftPump.isOn;
        state++;
    }
    public void toggleLeftPump2(){
        checkPumpState();
        if(!pumpToOff()) throw new IllegalStateException("Вимкніть насоси бака з меншим паливом");
        //
        if(state == 2 && !plane.leftPump2.isOn) throw new IllegalStateException("Вимкніть інший насос");
        if(state == 4 && plane.leftPump2.isOn) throw new IllegalStateException("Увімкніть інший насос");
        
        if(state == 3 && !plane.getFuelBalanceGood()) throw new IllegalStateException("Дизбаланс палива не усунуто, насос вимкнутий зарано");
        plane.leftPump2.isOn = !plane.leftPump2.isOn;
        state++;
    }
    public void toggleRightPump1(){
        checkPumpState();
        if(pumpToOff()) throw new IllegalStateException("Вимкніть насоси бака з меншим паливом");
        //
        if(state == 2 && !plane.rightPump.isOn) throw new IllegalStateException("Вимкніть інший насос");
        if(state == 4 && plane.rightPump.isOn) throw new IllegalStateException("Увімкніть інший насос");
        
        if(state == 3 && !plane.getFuelBalanceGood()) throw new IllegalStateException("Дизбаланс палива не усунуто, насос вимкнутий зарано");
        plane.rightPump.isOn = !plane.rightPump.isOn;
        state++;
    }
    public void toggleRightPump2(){
        checkPumpState();
        if(pumpToOff()) throw new IllegalStateException("Вимкніть насоси бака з меншим паливом");
        //
        if(state == 2 && !plane.rightPump2.isOn) throw new IllegalStateException("Вимкніть інший насос");
        if(state == 4 && plane.rightPump2.isOn) throw new IllegalStateException("Увімкніть інший насос");
        
        if(state == 3 && !plane.getFuelBalanceGood()) throw new IllegalStateException("Дизбаланс палива не усунуто, насос вимкнутий зарано");
        plane.rightPump2.isOn = !plane.rightPump2.isOn;
        state++;
    }

    //це у нашій лабораторній не використовується
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
