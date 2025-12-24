/*
 * Laboratory Work #6: Collections in Java
 * Student ID: 7, C2=1 (Set), C3=1 (Singly linked list)
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RailwayCar other = (RailwayCar) obj;
        return carNumber.equals(other.carNumber);
    }
    
    @Override
    public int hashCode() {
        return carNumber.hashCode();
    }
    
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
    
    @Override
    public String getCarType() {
        return "Third Class";
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
    
    @Override
    public String getCarType() {
        return "Second Class";
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
    
    @Override
    public String getCarType() {
        return "First Class";
    }
}

class RailwayCarSet implements Set<RailwayCar> {
    
    private class Node {
        RailwayCar data;
        Node next;
        
        Node(RailwayCar data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private Node head;
    private int size;
    
    public RailwayCarSet() {
        this.head = null;
        this.size = 0;
    }
    
    public RailwayCarSet(RailwayCar car) {
        this();
        add(car);
    }
    
    public RailwayCarSet(Collection<? extends RailwayCar> collection) {
        this();
        addAll(collection);
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public boolean contains(Object o) {
        if (o == null || !(o instanceof RailwayCar)) {
            return false;
        }
        
        Node current = head;
        while (current != null) {
            if (current.data.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    @Override
    public Iterator<RailwayCar> iterator() {
        return new Iterator<RailwayCar>() {
            private Node current = head;
            
            @Override
            public boolean hasNext() {
                return current != null;
            }
            
            @Override
            public RailwayCar next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                RailwayCar data = current.data;
                current = current.next;
                return data;
            }
        };
    }
    
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node current = head;
        int index = 0;
        
        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }
        
        return array;
    }
    
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(
                a.getClass().getComponentType(), size);
        }
        
        Node current = head;
        int index = 0;
        
        while (current != null) {
            a[index++] = (T) current.data;
            current = current.next;
        }
        
        if (a.length > size) {
            a[size] = null;
        }
        
        return a;
    }
    
    @Override
    public boolean add(RailwayCar car) {
        if (car == null) {
            throw new NullPointerException("Car cannot be null");
        }
        
        if (contains(car)) {
            return false;
        }
        
        Node newNode = new Node(car);
        
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        
        size++;
        return true;
    }
    
    @Override
    public boolean remove(Object o) {
        if (o == null || !(o instanceof RailwayCar)) {
            return false;
        }
        
        if (head == null) {
            return false;
        }
        
        if (head.data.equals(o)) {
            head = head.next;
            size--;
            return true;
        }
        
        Node current = head;
        while (current.next != null) {
            if (current.next.data.equals(o)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        
        return false;
    }
    
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean addAll(Collection<? extends RailwayCar> c) {
        boolean modified = false;
        for (RailwayCar car : c) {
            if (add(car)) {
                modified = true;
            }
        }
        return modified;
    }
    
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<RailwayCar> it = iterator();
        
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        
        return modified;
    }
    
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object element : c) {
            if (remove(element)) {
                modified = true;
            }
        }
        return modified;
    }
    
    @Override
    public void clear() {
        head = null;
        size = 0;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node current = head;
        
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        
        sb.append("]");
        return sb.toString();
    }
}

public class Main {
    
    public static void main(String[] args) {
        try {
            int studentId = 7;
            int c2 = studentId % 2;
            int c3 = studentId % 3;
            
            System.out.printf("Student ID: %d, C2=%d (Set), C3=%d (Singly linked list)\n\n", 
                            studentId, c2, c3);
            
            System.out.println("=== Testing empty constructor ===");
            RailwayCarSet set1 = new RailwayCarSet();
            System.out.println("Empty set size: " + set1.size());
            
            System.out.println("\n=== Testing single object constructor ===");
            RailwayCar car1 = new ThirdClassCar("12-3456", 54, 450, 2160, true, 18);
            RailwayCarSet set2 = new RailwayCarSet(car1);
            System.out.println("Set with 1 car: " + set2);
            
            System.out.println("\n=== Testing collection constructor ===");
            java.util.List<RailwayCar> initialCars = new java.util.ArrayList<>();
            initialCars.add(new ThirdClassCar("12-3457", 54, 450, 2160, true, 18));
            initialCars.add(new SecondClassCar("22-1234", 36, 850, 1440, 9, true));
            initialCars.add(new FirstClassCar("14-5678", 18, 1500, 720, true, true, true));
            
            RailwayCarSet set3 = new RailwayCarSet(initialCars);
            System.out.println("Set from collection: " + set3);
            System.out.println("Size: " + set3.size());
            
            System.out.println("\n=== Testing add method ===");
            RailwayCarSet trainSet = new RailwayCarSet();
            trainSet.add(new ThirdClassCar("12-1111", 54, 450, 2160, true, 18));
            trainSet.add(new SecondClassCar("22-2222", 36, 850, 1440, 9, true));
            trainSet.add(new FirstClassCar("14-3333", 18, 1500, 720, true, true, true));
            
            System.out.println("Train set: " + trainSet);
            System.out.println("Size: " + trainSet.size());
            
            System.out.println("\n=== Testing duplicate add ===");
            boolean added = trainSet.add(new ThirdClassCar("12-1111", 54, 450, 2160, true, 18));
            System.out.println("Duplicate added: " + added);
            System.out.println("Size after duplicate: " + trainSet.size());
            
            System.out.println("\n=== Testing contains ===");
            RailwayCar searchCar = new SecondClassCar("22-2222", 36, 850, 1440, 9, true);
            System.out.println("Contains car 22-2222: " + trainSet.contains(searchCar));
            
            System.out.println("\n=== Testing remove ===");
            trainSet.remove(searchCar);
            System.out.println("After removing car 22-2222: " + trainSet);
            System.out.println("Size: " + trainSet.size());
            
            System.out.println("\n=== Testing iterator ===");
            System.out.println("Iterating through set:");
            int count = 1;
            for (RailwayCar car : trainSet) {
                System.out.println(count++ + ". " + car);
            }
            
            System.out.println("\n=== Testing clear ===");
            trainSet.clear();
            System.out.println("Size after clear: " + trainSet.size());
            System.out.println("Is empty: " + trainSet.isEmpty());
            
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Null pointer error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}