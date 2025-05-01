/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementations;


import Interfaces.UserDataAccess;
import Aplicacion.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class UserDataAccessImpl implements UserDataAccess {
    private final Map<Long, User> users = new HashMap<>();
    private final Map<String, User> usersByUsername = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public User get(Long id) {
        return users.get(id);
    }

    @Override
    public User getByUsername(String username) {
        return usersByUsername.get(username);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void save(User user) {
        long id = nextId.getAndIncrement();
        user.setId(id);
        users.put(id, user);
        usersByUsername.put(user.getUsername(), user);
    }

    @Override
    public void update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            usersByUsername.put(user.getUsername(), user);
        }
    }

    @Override
    public void delete(Long id) {
        User userToDelete = users.get(id);
        if (userToDelete != null) {
            users.remove(id);
            usersByUsername.remove(userToDelete.getUsername());
        }
    }
}
