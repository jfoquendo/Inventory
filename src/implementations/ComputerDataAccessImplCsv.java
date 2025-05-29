// data_access/file/ComputerDataAccessImplCsv.java
package implementations;


import Interfaces.ComputerDataAccess;
import Aplicacion.model.Computer;
import Aplicacion.model.User; // Necesario si Computer tiene un User asignado
import implementations.UserServiceImpl; // Para obtener el User asignado
import Interfaces.UserService; // Para obtener el User asignado

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ComputerDataAccessImplCsv implements ComputerDataAccess {

    private static final String CSV_FILE_PATH = "Inventario.csv";
    private static final String CSV_DELIMITER = ";"; 
    private AtomicLong nextId;

    
    private final UserService userService;
    

    public ComputerDataAccessImplCsv(UserService userService) {
        this.userService = userService;
        File file = new File(CSV_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
                writeCsvHeader();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo CSV: " + e.getMessage());
            }
        }
        this.nextId = new AtomicLong(readMaxId() + 1);
    }

    private void writeCsvHeader() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, false))) { // false para sobrescribir
            writer.write("id,active,serial,model,processor,ram,hardDrive,assignedUsername,userIdentification,userPosition,costCenter,area,management,location,contract,operatingSystem\n");
        } catch (IOException e) {
            System.err.println("Error al escribir la cabecera CSV: " + e.getMessage());
        }
    }


    private long readMaxId() {
        long maxId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            reader.readLine(); // Saltar la línea de cabecera
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(CSV_DELIMITER);
                if (parts.length > 0) {
                    try {
                        long currentId = Long.parseLong(parts[0]);
                        if (currentId > maxId) {
                            maxId = currentId;
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar líneas con ID no numérico
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV para obtener el ID máximo: " + e.getMessage());
        }
        return maxId;
    }

    @Override
    public Computer get(Long id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            reader.readLine(); // Saltar la línea de cabecera
            while ((line = reader.readLine()) != null) {
                Computer computer = parseCsvLineToComputer(line);
                if (computer != null && computer.getId().equals(id)) {
                    return computer;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Computer> getAll() {
        List<Computer> computers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                Computer computer = parseCsvLineToComputer(line);
                if (computer != null) {
                    computers.add(computer);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }
        return computers;
    }

    @Override
    public void save(Computer computer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, true))) { // true para añadir al final
            long id = nextId.getAndIncrement();
            computer.setId(id);
            writer.write(formatComputerToCsvLine(computer));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo CSV: " + e.getMessage());
        }
    }

    @Override
    public void update(Computer computer) {
        List<Computer> allComputers = getAll(); // Leer todos los datos
        boolean found = false;
        for (int i = 0; i < allComputers.size(); i++) {
            if (allComputers.get(i).getId().equals(computer.getId())) {
                allComputers.set(i, computer); // Reemplazar el objeto
                found = true;
                break;
            }
        }

        if (found) {
            // Reescribir todo el archivo con los datos actualizados
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, false))) { // false para sobrescribir
                writeCsvHeader(); // Reescribir la cabecera
                for (Computer c : allComputers) {
                    writer.write(formatComputerToCsvLine(c));
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error al actualizar el archivo CSV: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró la computadora con ID " + computer.getId() + " para actualizar.");
        }
    }

    @Override
    public void delete(Long id) {
        List<Computer> allComputers = getAll();
        List<Computer> updatedComputers = allComputers.stream()
                                                    .filter(c -> !c.getId().equals(id))
                                                    .collect(Collectors.toList());

        if (updatedComputers.size() < allComputers.size()) { // Si se eliminó al menos uno
            // Reescribir todo el archivo sin el elemento eliminado
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, false))) { // false para sobrescribir
                writeCsvHeader(); // Reescribir la cabecera
                for (Computer c : updatedComputers) {
                    writer.write(formatComputerToCsvLine(c));
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error al eliminar del archivo CSV: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró la computadora con ID " + id + " para eliminar.");
        }
    }

    
    private String formatComputerToCsvLine(Computer computer) {
        // Asegúrate de que todos los campos se manejen, incluyendo los de Device
        // y que los campos que puedan contener comas se encierren entre comillas si usas un parser CSV más avanzado.
        // Aquí, para simplificar, asumimos que los campos no tienen comas.
        String assignedUsername = (computer.getAssignedUser() != null) ? computer.getAssignedUser().getUsername() : "";

        return String.join(CSV_DELIMITER,
                String.valueOf(computer.getId()),
                String.valueOf(computer.isActive()),
                escapeCsv(computer.getSerial()),
                escapeCsv(computer.getModel()),
                escapeCsv(computer.getProcessor()),
                escapeCsv(computer.getRam()),
                escapeCsv(computer.getHardDrive()),
                escapeCsv(assignedUsername), 
                escapeCsv(computer.getUserIdentification()),
                escapeCsv(computer.getUserPosition()),
                escapeCsv(computer.getCostCenter()),
                escapeCsv(computer.getArea()),
                escapeCsv(computer.getManagement()),
                escapeCsv(computer.getLocation()),
                escapeCsv(computer.getContract()),
                escapeCsv(computer.getOperatingSystem())
        );
    }

    private Computer parseCsvLineToComputer(String line) {
        String[] parts = line.split(CSV_DELIMITER, -1); 
        if (parts.length < 16) { 
            System.err.println("Línea CSV incompleta o mal formada: " + line);
            return null;
        }
        try {
            Computer computer = new Computer();
            computer.setId(Long.parseLong(parts[0]));
            computer.setActive(Boolean.parseBoolean(parts[1]));
            computer.setSerial(unescapeCsv(parts[2]));
            computer.setModel(unescapeCsv(parts[3]));
            computer.setProcessor(unescapeCsv(parts[4]));
            computer.setRam(unescapeCsv(parts[5]));
            computer.setHardDrive(unescapeCsv(parts[6]));
            String assignedUsername = unescapeCsv(parts[7]);
           
            if (!assignedUsername.isEmpty()) {
                User assignedUser = userService.getUserByUsername(assignedUsername); 
                if (assignedUser != null) {
                    computer.setAssignedUser(assignedUser);
                } 
            } else {
                computer.setAssignedUser(null);
            }

            computer.setUserIdentification(unescapeCsv(parts[8]));
            computer.setUserPosition(unescapeCsv(parts[9]));
            computer.setCostCenter(unescapeCsv(parts[10]));
            computer.setArea(unescapeCsv(parts[11]));
            computer.setManagement(unescapeCsv(parts[12]));
            computer.setLocation(unescapeCsv(parts[13]));
            computer.setContract(unescapeCsv(parts[14]));
            computer.setOperatingSystem(unescapeCsv(parts[15]));

            return computer;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error al parsear línea CSV a Computer: " + line + " - " + e.getMessage());
            return null;
        }
    }

    // Simple escape/unescape para CSV (no maneja comillas dentro de campos con comas)
    private String escapeCsv(String value) {
        return value != null ? value.replace(CSV_DELIMITER, "\\" + CSV_DELIMITER) : "";
    }

    private String unescapeCsv(String value) {
        return value != null ? value.replace("\\" + CSV_DELIMITER, CSV_DELIMITER) : "";
    }
}
