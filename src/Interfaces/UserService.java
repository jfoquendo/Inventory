/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import Aplicacion.model.User;
import java.util.List;

public interface UserService {
    User getUser(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    void addUser(User user);
    void updateUser(User user);
    void removeUser(Long id);
}