package javaplane.Objects;
/*
 * Паливний насос
 * Витрачає паливо з бака якщо увімкнений
 * Передає паливо двигуну(для простоти його не моделюємо)
 * Приймає відкриття/закриття крану кільцювання 
 */
public class Pump {
    public FuelTank tank;//бак з паливом який підключений до насосу
    public String pumpType;//лівий чи правий
    public double fuelConsumptiondx;//літри/сек - скільки палива витрачає насос
    public boolean isOn = false;//увімкнений чи вимкнений

    //конструктор насосу, приймає 
    //String pumpType тип насосу, 
    //FuelTank tank бак з паливом який підключений до насосу, 
    //double fuelConsumptiondx скільки палива витрачає насос
    public Pump(String pumpType, FuelTank tank, double fuelConsumptiondx) {
        this.pumpType = pumpType;
        this.tank = tank;
        this.fuelConsumptiondx = fuelConsumptiondx;
    }
    /**
     * Витрачає паливо з бака якщо увімкнений
     * @param dt - час витрачання палива в секундах
     * @return кількість палива що витрачено
     * @throws NoFuelException - якщо палива немає
     */
    public double consume(double dt) {
        if(!isOn)//якщо насос не включений то не витрачаємо паливо
            return 0;
        return tank.consume(fuelConsumptiondx) * dt;// літри/сек * сек = літри
    }
}
