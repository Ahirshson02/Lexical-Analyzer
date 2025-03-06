import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        // Print a message
        System.out.println("Hello, World!");

        // Create a Scanner object for user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        
        // Read user input
        String name = scanner.nextLine();

        // Greet the user
        System.out.println("Hello, " + name + "! Welcome to Java.");

        // Close the scanner
        scanner.close();
    }
}
