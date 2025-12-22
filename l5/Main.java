/*
 * Laboratory Work #5: Inheritance and Polymorphism
 * Student ID: 7, C13=7 (Railway rolling stock)
 */

import java.util.Arrays;
import java.util.Comparator;

abstract class RailwayCar {
    private String carNumber;
    private int seats;
    private int comfortLevel;
    private double ticketPrice;
    private int baggageCapacity;
    
    public RailwayCar(String carNumber, int seats, int comfortLevel, 
                     double ticketPrice, int baggageCapacity) {
        if (seats < 0 || comfortLevel < 0 || ticketPrice < 0 || baggageCapacity < 0) {
            throw new IllegalArgumentException("Values cannot be negative");
        }
        this.carNumber = carNumber;
        this.seats = seats;
        this.comfortLevel = comfortLevel;
        this.ticketPrice = ticketPrice;
        this.baggageCapacity = baggageCapacity;
    }
    
    public String getCarNumber() {
        return carNumber;
    }
    
    public int getSeats() {
        return seats;
    }
    
    public int getComfortLevel() {
        return comfortLevel;
    }
    
    public double getTicketPrice() {
        return ticketPrice;
    }
    
    public int getBaggageCapacity() {
        return baggageCapacity;
    }
    
    public abstract String getCarType();
    
    @Override
    public String toString() {
        return String.format("%s (#%s): seats=%d, comfort=%d, price=%.2f UAH, baggage=%dkg", 
                           getCarType(), carNumber, seats, comfortLevel, 
                           ticketPrice, baggageCapacity);
    }
}

class ThirdClassCar extends RailwayCar {
    private boolean hasAirConditioning;
    private int openBerths;
    
    public ThirdClassCar(String carNumber, int seats, double ticketPrice, 
                        int baggageCapacity, boolean hasAirConditioning, int openBerths) {
        super(carNumber, seats, 3, ticketPrice, baggageCapacity);
        this.hasAirConditioning = hasAirConditioning;
        this.openBerths = openBerths;
    }
    
    public boolean hasAirConditioning() {
        return hasAirConditioning;
    }
    
    public int getOpenBerths() {
        return openBerths;
    }
    
    @Override
    public String getCarType() {
        return "Third Class";
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(", AC=%b, openBerths=%d", 
                                               hasAirConditioning, openBerths);
    }
}

class SecondClassCar extends RailwayCar {
    private int numberOfCompartments;
    private boolean hasBedding;
    
    public SecondClassCar(String carNumber, int seats, double ticketPrice, 
                         int baggageCapacity, int numberOfCompartments, boolean hasBedding) {
        super(carNumber, seats, 6, ticketPrice, baggageCapacity);
        this.numberOfCompartments = numberOfCompartments;
        this.hasBedding = hasBedding;
    }
    
    public int getNumberOfCompartments() {
        return numberOfCompartments;
    }
    
    public boolean hasBedding() {
        return hasBedding;
    }
    
    @Override
    public String getCarType() {
        return "Second Class";
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(", compartments=%d, bedding=%b", 
                                               numberOfCompartments, hasBedding);
    }
}

class FirstClassCar extends RailwayCar {
    private boolean hasShower;
    private boolean hasTV;
    private boolean hasMeals;
    
    public FirstClassCar(String carNumber, int seats, double ticketPrice, 
                        int baggageCapacity, boolean hasShower, boolean hasTV, boolean hasMeals) {
        super(carNumber, seats, 9, ticketPrice, baggageCapacity);
        this.hasShower = hasShower;
        this.hasTV = hasTV;
        this.hasMeals = hasMeals;
    }
    
    public boolean hasShower() {
        return hasShower;
    }
    
    public boolean hasTV() {
        return hasTV;
    }
    
    public boolean hasMeals() {
        return hasMeals;
    }
    
    @Override
    public String getCarType() {
        return "First Class";
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(", shower=%b, TV=%b, meals=%b", 
                                               hasShower, hasTV, hasMeals);
    }
}

class PassengerTrain {
    private String trainNumber;
    private String route;
    private RailwayCar[] cars;
    private int carCount;
    
    public PassengerTrain(String trainNumber, String route, int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.trainNumber = trainNumber;
        this.route = route;
        this.cars = new RailwayCar[capacity];
        this.carCount = 0;
    }
    
    public void addCar(RailwayCar car) {
        if (car == null) {
            throw new NullPointerException("Car cannot be null");
        }
        if (carCount >= cars.length) {
            throw new IllegalStateException("Train is full");
        }
        cars[carCount++] = car;
    }
    
    public int getTotalPassengers() {
        int total = 0;
        for (int i = 0; i < carCount; i++) {
            total += cars[i].getSeats();
        }
        return total;
    }
    
    public int getTotalBaggageCapacity() {
        int total = 0;
        for (int i = 0; i < carCount; i++) {
            total += cars[i].getBaggageCapacity();
        }
        return total;
    }
    
    public void sortByComfortLevel() {
        Arrays.sort(cars, 0, carCount, new Comparator<RailwayCar>() {
            public int compare(RailwayCar c1, RailwayCar c2) {
                return Integer.compare(c1.getComfortLevel(), c2.getComfortLevel());
            }
        });
    }
    
    public RailwayCar[] findCarsByPassengerRange(int minPassengers, int maxPassengers) {
        if (minPassengers < 0 || maxPassengers < 0 || minPassengers > maxPassengers) {
            throw new IllegalArgumentException("Invalid passenger range");
        }
        
        int count = 0;
        for (int i = 0; i < carCount; i++) {
            int capacity = cars[i].getSeats();
            if (capacity >= minPassengers && capacity <= maxPassengers) {
                count++;
            }
        }
        
        RailwayCar[] result = new RailwayCar[count];
        int index = 0;
        
        for (int i = 0; i < carCount; i++) {
            int capacity = cars[i].getSeats();
            if (capacity >= minPassengers && capacity <= maxPassengers) {
                result[index++] = cars[i];
            }
        }
        
        return result;
    }
    
    public void printTrain() {
        System.out.println("Train #" + trainNumber + " " + route);
        System.out.println("Train composition:");
        for (int i = 0; i < carCount; i++) {
            System.out.println("Car " + (i + 1) + ": " + cars[i]);
        }
    }
}

public class Main {
    
    public static void main(String[] args) {
        try {
            int studentId = 7;
            int c13 = studentId % 13;
            
            System.out.printf("Student ID: %d, C13=%d (Railway rolling stock)\n\n", 
                            studentId, c13);
            
            PassengerTrain train = new PassengerTrain("745K", "Kyiv - Lviv", 10);
            
            train.addCar(new ThirdClassCar("12-3456", 54, 450, 2160, true, 18));
            train.addCar(new ThirdClassCar("12-3457", 54, 450, 2160, true, 18));
            train.addCar(new SecondClassCar("22-1234", 36, 850, 1440, 9, true));
            train.addCar(new SecondClassCar("22-1235", 36, 850, 1440, 9, true));
            train.addCar(new FirstClassCar("14-5678", 18, 1500, 720, true, true, true));
            train.addCar(new ThirdClassCar("12-3458", 52, 420, 2080, false, 16));
            
            train.printTrain();
            
            int totalPassengers = train.getTotalPassengers();
            int totalBaggage = train.getTotalBaggageCapacity();
            System.out.printf("\nTotal passenger capacity: %d\n", totalPassengers);
            System.out.printf("Total baggage capacity: %d kg\n", totalBaggage);
            
            train.sortByComfortLevel();
            System.out.println("\n=== Cars sorted by comfort level ===");
            train.printTrain();
            
            int minPassengers = 30;
            int maxPassengers = 40;
            RailwayCar[] foundCars = train.findCarsByPassengerRange(minPassengers, 
                                                                    maxPassengers);
            
            System.out.printf("\n=== Cars with seats from %d to %d ===\n", 
                            minPassengers, maxPassengers);
            if (foundCars.length == 0) {
                System.out.println("No cars found");
            } else {
                for (int i = 0; i < foundCars.length; i++) {
                    System.out.println((i + 1) + ". " + foundCars[i]);
                }
            }
            
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Null pointer error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}