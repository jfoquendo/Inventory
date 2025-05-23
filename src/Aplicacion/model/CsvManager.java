package Aplicacion.model;

import java.io.*;
import Aplicacion.model.Computer;
import Interfaces.ComputerService;
import java.util.List;
import Aplicacion.model.User;
import Interfaces.UserService;
import implementations.UserServiceImpl;
import Aplicacion.model.Role;

public class CsvManager {
    private static final UserService userService = new UserServiceImpl();
    

    public CsvManager() {
    }
    
    public void readComputer(UserService userService) {
        String archivo = "inventario.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = reader.readLine()) != null) {
                if (primeraLinea) { 
                    primeraLinea = false; 
                    continue;
                }

                String[] datos = linea.split(";"); // Separar por comas
                boolean state = Boolean.parseBoolean(datos[0]);
                String serial = datos[1];
                String model = datos[2];
                String processor = datos[3];
                String ram = datos[4];
                String disk = datos[5];
                String username = datos[6];
                String id = datos[7];
                String userposition = datos[8];
                String costcenter = datos[9];
                String area = datos[10];
                String management = datos[11];
                String location = datos[12];
                String contract = datos[13];
                String OperationSystem = "Windows 11";
                
                User assignedUser = new User(username,"123456",Role.USER);
                
                Computer loadComputer = new Computer(state, serial, model, processor, ram,disk, assignedUser, id, userposition, costcenter, area, management, location, contract, OperationSystem);
                
                
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
