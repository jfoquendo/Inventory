/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Aplicacion.model.Computer;
import Aplicacion.model.Role;
import Aplicacion.model.User;
import Aplicacion.model.CsvManager;
import Interfaces.ComputerService;
import Interfaces.TabletService;
import Interfaces.UserService;
import implementations.ComputerServiceImpl;
import implementations.TabletServiceImpl;
import implementations.UserServiceImpl;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    
    private static final CsvManager loadComputer = new CsvManager();
    private static final ComputerService computerService = new ComputerServiceImpl();
    private static final TabletService tabletService = new TabletServiceImpl();
    private static final UserService userService = new UserServiceImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadComputer.readComputer(userService);
        showMainMenu();
    }

    private static void showMainMenu() {
        int option;
        do {
            System.out.println("\n--- Inventory Management ---");
            System.out.println("1. User Management");
            System.out.println("2. Computer Management");
            System.out.println("3. Tablet Management");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> showUserMenu();
                case 2 -> showComputerMenu();
//              case 3 -> showTabletMenu();
                case 0 -> System.out.println("Exiting the application.");
                default -> System.out.println("Invalid option.");
            }
        } while (option != 0);
    }

    private static void showUserMenu() {
        int option;
        do {
            System.out.println("\n--- User Management ---");
            System.out.println("1. Add User");
            System.out.println("2. View All Users");
            System.out.println("3. View User by ID");
            System.out.println("4. Update User");
            System.out.println("5. Delete User");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> addUserConsole();
                case 2 -> viewAllUsersConsole();
                case 3 -> viewUserByIdConsole();
                case 4 -> updateUserConsole();
                case 5 -> deleteUserConsole();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        } while (true);
    }

    private static void addUserConsole() {
        System.out.println("\n--- Add User ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine(); // In a real application, hash this!
        System.out.println("Available Roles: ADMINISTRATOR, CONSULTATION, STANDARD");
        System.out.print("Role: ");
        String roleStr = scanner.nextLine().toUpperCase();
        Role role = Role.valueOf(roleStr);

        User newUser = new User(username, password, role);
        userService.addUser(newUser);
        System.out.println("User added successfully.");
    }

    private static void viewAllUsersConsole() {
        System.out.println("\n--- All Users ---");
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users registered.");
        } else {
            for (User user : users) {
                System.out.println("ID: " + user.getId() + ", Username: " + user.getUsername() + ", Role: " + user.getRole());
            }
        }
    }

    private static void viewUserByIdConsole() {
        System.out.println("\n--- View User by ID ---");
        System.out.print("Enter user ID: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        User user = userService.getUser(id);
        if (user != null) {
            System.out.println("ID: " + user.getId() + ", Username: " + user.getUsername() + ", Role: " + user.getRole());
        } else {
            System.out.println("No user found with that ID.");
        }
    }

    private static void updateUserConsole() {
        System.out.println("\n--- Update User ---");
        System.out.print("Enter the ID of the user to update: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        User existingUser = userService.getUser(id);
        if (existingUser != null) {
            System.out.print("New Username (" + existingUser.getUsername() + "): ");
            String newUsername = scanner.nextLine();
            if (!newUsername.isEmpty()) {
                existingUser.setUsername(newUsername);
            }
            System.out.print("New Password (current: hidden): "); // Remember hashing!
            String newPassword = scanner.nextLine();
            if (!newPassword.isEmpty()) {
                existingUser.setPassword(newPassword);
            }
            System.out.println("Available Roles: ADMINISTRATOR, CONSULTATION, STANDARD");
            System.out.print("New Role (" + existingUser.getRole() + "): ");
            String newRoleStr = scanner.nextLine().toUpperCase();
            if (!newRoleStr.isEmpty()) {
                existingUser.setRole(Role.valueOf(newRoleStr));
            }
            userService.updateUser(existingUser);
            System.out.println("User updated successfully.");
        } else {
            System.out.println("No user found with that ID.");
        }
    }

    private static void deleteUserConsole() {
        System.out.println("\n--- Delete User ---");
        System.out.print("Enter the ID of the user to delete: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        userService.removeUser(id);
        System.out.println("User deleted successfully.");
    }

    private static void showComputerMenu() {
        int option;
        do {
            System.out.println("\n--- Computer Management ---");
            System.out.println("1. Add Computer");
            System.out.println("2. View All Computers");
            System.out.println("3. View Computer by ID");
            System.out.println("4. Update Computer");
            System.out.println("5. Delete Computer");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> addComputerConsole();
                case 2 -> viewAllComputersConsole();
                case 3 -> viewComputerByIdConsole();
                case 4 -> updateComputerConsole();
                case 5 -> deleteComputerConsole();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        } while (true);
    }

    private static void addComputerConsole() {
        System.out.println("\n--- Add Computer ---");
        System.out.print("Active (true/false): ");
        boolean active = scanner.nextBoolean();
        scanner.nextLine();
        System.out.print("Serial: ");
        String serial = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Processor: ");
        String processor = scanner.nextLine();
        System.out.print("RAM: ");
        String ram = scanner.nextLine();
        System.out.print("Hard Drive: ");
        String hardDrive = scanner.nextLine();

        System.out.print("Assigned User ID (optional): ");
        Long userId = null;
        if (scanner.hasNextLong()) {
            userId = scanner.nextLong();
            scanner.nextLine();
        }
        User assignedUser = (userId != null) ? userService.getUser(userId) : null;

        System.out.print("User Identification: ");
        String userIdentification = scanner.nextLine();
        System.out.print("User Position: ");
        String userPosition = scanner.nextLine();
        System.out.print("Cost Center: ");
        String costCenter = scanner.nextLine();
        System.out.print("Area: ");
        String area = scanner.nextLine();
        System.out.print("Management: ");
        String management = scanner.nextLine();
        System.out.print("Location: ");
        String location = scanner.nextLine();
        System.out.print("Contract: ");
        String contract = scanner.nextLine();
        System.out.print("Operating System: ");
        String operatingSystem = scanner.nextLine();

        Computer newComputer = new Computer(active, serial, model, processor, ram, hardDrive, assignedUser, userIdentification, userPosition, costCenter, area, management, location, contract, operatingSystem);
        computerService.addDevice(newComputer);
        System.out.println("Computer added successfully.");
    }
    
     private static void viewAllComputersConsole() {
        System.out.println("\n--- All Computers ---");
        List<Computer> computers = computerService.getAllDevices();
        if (computers.isEmpty()) {
            System.out.println("No computers found.");
        } else {
            for (Computer computer : computers) {
                System.out.println("ID: " + computer.getId() + ", Active: " + computer.isActive() + ", Serial: " + computer.getSerial() + ", Model: " + computer.getModel() + ", Processor: " + computer.getProcessor() + ", RAM: " + computer.getRam() + ", Hard Drive: " + computer.getHardDrive() + ", OS: " + computer.getOperatingSystem() + (computer.getAssignedUser() != null ? ", Assigned to User ID: " + computer.getAssignedUser().getId() : ", Not assigned") + ", User ID: " + computer.getUserIdentification() + ", Position: " + computer.getUserPosition() + ", Cost Center: " + computer.getCostCenter() + ", Area: " + computer.getArea() + ", Management: " + computer.getManagement() + ", Location: " + computer.getLocation() + ", Contract: " + computer.getContract());
            }
        }
    }
     
     private static void viewComputerByIdConsole() {
        System.out.println("\n--- View Computer by ID ---");
        System.out.print("Enter computer ID: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        Computer computer = computerService.getDevice(id);
        if (computer != null) {
            System.out.println("--- Computer Details ---");
            System.out.println("ID: " + computer.getId());
            System.out.println("Active: " + computer.isActive());
            System.out.println("Serial: " + computer.getSerial());
            System.out.println("Model: " + computer.getModel());
            System.out.println("Processor: " + computer.getProcessor());
            System.out.println("RAM: " + computer.getRam());
            System.out.println("Hard Drive: " + computer.getHardDrive());
            System.out.println("Operating System: " + computer.getOperatingSystem());
            if (computer.getAssignedUser() != null) {
                System.out.println("Assigned to User ID: " + computer.getAssignedUser().getId());
            } else {
                System.out.println("Not assigned to a user.");
            }
            System.out.println("User Identification: " + computer.getUserIdentification());
            System.out.println("User Position: " + computer.getUserPosition());
            System.out.println("Cost Center: " + computer.getCostCenter());
            System.out.println("Area: " + computer.getArea());
            System.out.println("Management: " + computer.getManagement());
            System.out.println("Location: " + computer.getLocation());
            System.out.println("Contract: " + computer.getContract());
        } else {
            System.out.println("No computer found with that ID.");
        }
    }
     
    private static void updateComputerConsole() {
        System.out.println("\n--- Update Computer ---");
        System.out.print("Enter the ID of the computer to update: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        Computer existingComputer = computerService.getDevice(id);
        if (existingComputer != null) {
            System.out.println("--- Current Computer Details (Leave blank to keep current value) ---");
            System.out.println("Active: " + existingComputer.isActive());
            System.out.println("Serial: " + existingComputer.getSerial());
            System.out.println("Model: " + existingComputer.getModel());
            System.out.println("Processor: " + existingComputer.getProcessor());
            System.out.println("RAM: " + existingComputer.getRam());
            System.out.println("Hard Drive: " + existingComputer.getHardDrive());
            System.out.println("Operating System: " + existingComputer.getOperatingSystem());
            System.out.println("Assigned User ID: " + (existingComputer.getAssignedUser() != null ? existingComputer.getAssignedUser().getId() : "Not assigned"));
            System.out.println("User Identification: " + existingComputer.getUserIdentification());
            System.out.println("User Position: " + existingComputer.getUserPosition());
            System.out.println("Cost Center: " + existingComputer.getCostCenter());
            System.out.println("Area: " + existingComputer.getArea());
            System.out.println("Management: " + existingComputer.getManagement());
            System.out.println("Location: " + existingComputer.getLocation());
            System.out.println("Contract: " + existingComputer.getContract());

            System.out.print("New Active (true/false): ");
            String activeStr = scanner.nextLine();
            if (!activeStr.isEmpty()) {
                existingComputer.setActive(Boolean.parseBoolean(activeStr));
            }
            System.out.print("New Serial: ");
            String serial = scanner.nextLine();
            if (!serial.isEmpty()) {
                existingComputer.setSerial(serial);
            }
            System.out.print("New Model: ");
            String model = scanner.nextLine();
            if (!model.isEmpty()) {
                existingComputer.setModel(model);
            }
            System.out.print("New Processor: ");
            String processor = scanner.nextLine();
            if (!processor.isEmpty()) {
                existingComputer.setProcessor(processor);
            }
            System.out.print("New RAM: ");
            String ram = scanner.nextLine();
            if (!ram.isEmpty()) {
                existingComputer.setRam(ram);
            }
            System.out.print("New Hard Drive: ");
            String hardDrive = scanner.nextLine();
            if (!hardDrive.isEmpty()) {
                existingComputer.setHardDrive(hardDrive);
            }
            System.out.print("New Operating System: ");
            String operatingSystem = scanner.nextLine();
            if (!operatingSystem.isEmpty()) {
                existingComputer.setOperatingSystem(operatingSystem);
            }
            System.out.print("New Assigned User ID (leave blank to keep current): ");
            String assignedUserIdStr = scanner.nextLine();
            if (!assignedUserIdStr.isEmpty()) {
                try {
                    Long newUserId = Long.valueOf(assignedUserIdStr);
                    existingComputer.setAssignedUser(userService.getUser(newUserId));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid User ID format.");
                }
            }
            System.out.print("New User Identification: ");
            String userIdentification = scanner.nextLine();
            if (!userIdentification.isEmpty()) {
                existingComputer.setUserIdentification(userIdentification);
            }
            System.out.print("New User Position: ");
            String userPosition = scanner.nextLine();
            if (!userPosition.isEmpty()) {
                existingComputer.setUserPosition(userPosition);
            }
            System.out.print("New Cost Center: ");
            String costCenter = scanner.nextLine();
            if (!costCenter.isEmpty()) {
                existingComputer.setCostCenter(costCenter);
            }
            System.out.print("New Area: ");
            String area = scanner.nextLine();
            if (!area.isEmpty()) {
                existingComputer.setArea(area);
            }
            System.out.print("New Management: ");
            String management = scanner.nextLine();
            if (!management.isEmpty()) {
                existingComputer.setManagement(management);
            }
            System.out.print("New Location: ");
            String location = scanner.nextLine();
            if (!location.isEmpty()) {
                existingComputer.setLocation(location);
            }
            System.out.print("New Contract: ");
            String contract = scanner.nextLine();
            if (!contract.isEmpty()) {
                existingComputer.setContract(contract);
            }

            computerService.updateDevice(existingComputer);
            System.out.println("Computer updated successfully.");

        } else {
            System.out.println("No computer found with that ID.");
        }
    }
    
    private static void deleteComputerConsole() {
        System.out.println("\n--- Delete Computer ---");
        System.out.print("Enter the ID of the computer to delete: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        computerService.removeDevice(id);
        System.out.println("Computer deleted successfully.");
    }
  
}