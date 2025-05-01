/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementations;

import Aplicacion.model.User;
import Interfaces.UserDataAccess;
import implementations.UserDataAccessImpl;
import Interfaces.UserService;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDataAccess userDataAccess = new UserDataAccessImpl();

    @Override
    public User getUser(Long id) {
        return userDataAccess.get(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDataAccess.getByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDataAccess.getAll();
    }

    @Override
    public void addUser(User user) {
        userDataAccess.save(user);
    }

    @Override
    public void updateUser(User user) {
        userDataAccess.update(user);
    }

    @Override
    public void removeUser(Long id) {
        userDataAccess.delete(id);
    }
}