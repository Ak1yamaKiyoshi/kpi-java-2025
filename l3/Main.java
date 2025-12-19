/*
 * Laboratory Work #3: Classes in Java
 * Student ID: 7, C11=7 (Furniture)
 */

import java.util.Arrays;
import java.util.Comparator;

class Furniture {
    private String type;
    private String material;
    private double price;
    private int weight;
    private String color;
    
    public Furniture(String type, String material, double price, int weight, String color) {
        this.type = type;
        this.material = material;
        this.price = price;
        this.weight = weight;
        this.color = color;
    }
    
    public String getType() {
        return type;
    }
    
    public String getMaterial() {
        return material;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getWeight() {
        return weight;
    }
    
    public String getColor() {
        return color;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        Furniture furniture = (Furniture) obj;
        return Double.compare(furniture.price, price) == 0 
            && weight == furniture.weight
            && type.equals(furniture.type)
            && material.equals(furniture.material)
            && color.equals(furniture.color);
    }
    
    @Override
    public String toString() {
        return String.format("Furniture{type='%s', material='%s', price=%.2f, weight=%d, color='%s'}",
                           type, material, price, weight, color);
    }
}

public class Main {
    
    public static void main(String[] args) {
        try {
            int studentId = 7;
            int c11 = studentId % 11;
            
            System.out.printf("Student ID: %d, C11=%d (Furniture)\n\n", studentId, c11);
            
            Furniture[] furnitureArray = {
                new Furniture("Chair", "Wood", 150.50, 8, "Brown"),
                new Furniture("Table", "Metal", 320.00, 25, "Black"),
                new Furniture("Sofa", "Leather", 850.75, 45, "Gray"),
                new Furniture("Bed", "Wood", 450.00, 35, "White"),
                new Furniture("Cabinet", "Wood", 280.30, 30, "Brown"),
                new Furniture("Desk", "Metal", 220.00, 20, "Black")
            };
            
            System.out.println("Original array:");
            printArray(furnitureArray);
            
            Arrays.sort(furnitureArray, new Comparator<Furniture>() {
                public int compare(Furniture f1, Furniture f2) {
                    int priceCompare = Double.compare(f1.getPrice(), f2.getPrice());
                    if (priceCompare != 0) {
                        return priceCompare;
                    }
                    return Integer.compare(f2.getWeight(), f1.getWeight());
                }
            });
            
            System.out.println("\nSorted array (price ascending, weight descending):");
            printArray(furnitureArray);
            
            Furniture searchFurniture = new Furniture("Sofa", "Leather", 850.75, 45, "Gray");
            int foundIndex = findFurniture(furnitureArray, searchFurniture);
            
            System.out.println("\nSearching for: " + searchFurniture);
            if (foundIndex != -1) {
                System.out.println("Found at index: " + foundIndex);
            } else {
                System.out.println("Not found");
            }
            
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Null pointer error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
    
    static void printArray(Furniture[] array) {
        if (array == null) {
            throw new NullPointerException("Array cannot be null");
        }
        
        for (int i = 0; i < array.length; i++) {
            System.out.println(i + ": " + array[i]);
        }
    }
    
    static int findFurniture(Furniture[] array, Furniture target) {
        if (array == null || target == null) {
            throw new NullPointerException("Array and target cannot be null");
        }
        
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        
        return -1;
    }
}