package javaplane.Objects;

public class FuelTank {
    //ємність баку
    public double capacity = 100.0;
    //кількість палива в баку
    public double fuel = 100.0;
    public String tankType = "default";
    /**
     * Пробує витратити паливо
     * @param amount - кількість палива, яку хочемо витратити
     * @return кількість палива, яку вдалось витратити
     * @throws NoFuelException - якщо палива немає
     */
    public double consume(double amount) throws NoFuelException {
        if(amount > fuel){
            fuel = 0;
            throw new NoFuelException(tankType);
        }
        fuel -= amount;
        return amount;
    }
    /**
     * Обʼєкт - модель бака з паливом
     * @param tankType - який це бак, лівий правий?
     * @param capacity - ємність баку
     * @param fuel - кількість палива в баку
     */
    public FuelTank(String tankType, double capacity, double fuel) {
        this.tankType = tankType;
        this.capacity = capacity;
        this.fuel = capacity;
    }
}
