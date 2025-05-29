package implementations;

import Interfaces.UserDataAccess;
import Aplicacion.model.User;
import Aplicacion.model.Role;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class UserDataAccessImplCsv implements UserDataAccess {

    private static final String CSV_FILE_PATH = "Usuarios.csv";
    private static final String CSV_DELIMITER = ";";
    private AtomicLong nextId;

    private Map<String, User> usersCacheByUsername; // Cache para buscar por nombre de usuario eficientemente

    public UserDataAccessImplCsv() {
        File file = new File(CSV_FILE_PATH);
        this.nextId = new AtomicLong(1); // Valor inicial
        if (!file.exists() || file.length() == 0) { // Si no existe o está vacío
            try {
                file.createNewFile();
                writeCsvHeader();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo CSV de usuarios: " + e.getMessage());
            }
        }
        // Leer el ID máximo después de asegurar que el archivo existe y tiene cabecera
        this.nextId = new AtomicLong(readMaxId() + 1);
        loadUsersIntoCache(); // Cargar usuarios en caché al iniciar
    }

    private void writeCsvHeader() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, false))) {
            writer.write("id,username,password,role\n");
        } catch (IOException e) {
            System.err.println("Error al escribir la cabecera CSV de usuarios: " + e.getMessage());
        }
    }

    private long readMaxId() {
        long maxId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            reader.readLine(); // Saltar la línea de cabecera
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(CSV_DELIMITER, -1);
                if (parts.length > 0 && !parts[0].isEmpty()) {
                    try {
                        long currentId = Long.parseLong(parts[0]);
                        if (currentId > maxId) {
                            maxId = currentId;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Advertencia: Línea con ID no numérico en CSV de usuarios: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV de usuarios para obtener el ID máximo: " + e.getMessage());
        }
        return maxId;
    }

    private void loadUsersIntoCache() {
        usersCacheByUsername = new HashMap<>();
        List<User> allUsers = getAll();
        for (User user : allUsers) {
            if (user != null) { // Asegurarse de que el parseo fue exitoso
                usersCacheByUsername.put(user.getUsername(), user);
            }
        }
    }

    @Override
    public User get(Long id) {
        // En una implementación real con caché, podríamos buscar en caché por ID también
        List<User> allUsers = getAll(); // Llamar a getAll para leer del archivo
        return allUsers.stream()
                       .filter(user -> user.getId().equals(id))
                       .findFirst()
                       .orElse(null);
    }

    @Override
    public User getByUsername(String username) {
        // Intentar obtener de la caché primero
        User user = usersCacheByUsername.get(username);
        if (user != null) {
            return user;
        }
        // Si no está en caché (quizás se modificó el archivo externo o la caché está desactualizada),
        // recargar la caché y reintentar.
        loadUsersIntoCache();
        return usersCacheByUsername.get(username);
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            reader.readLine(); // Saltar la línea de cabecera
            while ((line = reader.readLine()) != null) {
                User user = parseCsvLineToUser(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer todos los usuarios del archivo CSV: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, true))) {
            long id = nextId.getAndIncrement();
            user.setId(id);
            writer.write(formatUserToCsvLine(user));
            writer.newLine();
            loadUsersIntoCache(); // Actualizar caché después de guardar
        } catch (IOException e) {
            System.err.println("Error al guardar en el archivo CSV de usuarios: " + e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        List<User> allUsers = getAll();
        boolean found = false;
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getId().equals(user.getId())) {
                allUsers.set(i, user);
                found = true;
                break;
            }
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, false))) {
                writeCsvHeader();
                for (User u : allUsers) {
                    writer.write(formatUserToCsvLine(u));
                    writer.newLine();
                }
                loadUsersIntoCache(); // Actualizar caché después de actualizar
            } catch (IOException e) {
                System.err.println("Error al actualizar el archivo CSV de usuarios: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró el usuario con ID " + user.getId() + " para actualizar.");
        }
    }

    @Override
    public void delete(Long id) {
        List<User> allUsers = getAll();
        List<User> updatedUsers = allUsers.stream()
                                         .filter(u -> !u.getId().equals(id))
                                         .collect(Collectors.toList());

        if (updatedUsers.size() < allUsers.size()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, false))) {
                writeCsvHeader();
                for (User u : updatedUsers) {
                    writer.write(formatUserToCsvLine(u));
                    writer.newLine();
                }
                loadUsersIntoCache(); // Actualizar caché después de eliminar
            } catch (IOException e) {
                System.err.println("Error al eliminar del archivo CSV de usuarios: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró el usuario con ID " + id + " para eliminar.");
        }
    }

    // ====================================================================================
    // Métodos Auxiliares para Mapeo CSV <-> Objeto
    // ====================================================================================

    private String formatUserToCsvLine(User user) {
        return String.join(CSV_DELIMITER,
                String.valueOf(user.getId()),
                escapeCsv(user.getUsername()),
                escapeCsv(user.getPassword()),
                user.getRole().name()
        );
    }

    private User parseCsvLineToUser(String line) {
        String[] parts = line.split(CSV_DELIMITER, -1);
        if (parts.length < 4) {
            System.err.println("Advertencia: Línea CSV de usuario incompleta o mal formada: " + line);
            return null;
        }
        try {
            User user = new User();
            user.setId(Long.parseLong(parts[0]));
            user.setUsername(unescapeCsv(parts[1]));
            user.setPassword(unescapeCsv(parts[2]));
            user.setRole(Role.valueOf(parts[3]));

            return user;
        } catch (NumberFormatException e) { // PRIMER CATCH
            System.err.println("Error (NumberFormatException) al parsear línea CSV a Usuario: " + line + " - " + e.getMessage());
            return null;
        } catch (IllegalArgumentException e) { // SEGUNDO CATCH
            System.err.println("Error (IllegalArgumentException) al parsear línea CSV a Usuario (Problema con el Role o valor no válido): " + line + " - " + e.getMessage());
            return null;
        } catch (ArrayIndexOutOfBoundsException e) { // TERCER CATCH
            System.err.println("Error (ArrayIndexOutOfBoundsException) al parsear línea CSV a Usuario (Faltan campos): " + line + " - " + e.getMessage());
            return null;
        }
    }

    private String escapeCsv(String value) {
        return value != null ? value.replace(CSV_DELIMITER, "\\" + CSV_DELIMITER) : "";
    }

    private String unescapeCsv(String value) {
        return value != null ? value.replace("\\" + CSV_DELIMITER, CSV_DELIMITER) : "";
    }
}