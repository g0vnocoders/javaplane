package javaplane.Objects;
/*
 * Паливний насос
 * Витрачає паливо з бака якщо увімкнений
 * Передає паливо двигуну(для простоти його не моделюємо)
 * Приймає відкриття/закриття крану кільцювання 
 */
public class Pump {
    public FuelTank tank;
    public String pumpType;
    public double fuelConsumptiondx;
    public boolean isOn = false;

    public Pump(String pumpType, FuelTank tank, double fuelConsumptiondx) {
        this.pumpType = pumpType;
        this.tank = tank;
        this.fuelConsumptiondx = fuelConsumptiondx;
    }
    /**
     * Витрачає паливо з бака якщо увімкнений
     * @return кількість палива що витрачено
     * @throws NoFuelException - якщо палива немає
     * @throws PumpIsOffException - якщо насос вимкнений
     */
    public double consume(double dt) throws NoFuelException, PumpIsOffException {
        if(!isOn)
            return 0;//throw new PumpIsOffException(pumpType);
        return tank.consume(fuelConsumptiondx) * dt;
    }
}
