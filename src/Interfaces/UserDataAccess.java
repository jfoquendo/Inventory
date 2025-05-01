/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import Aplicacion.model.User;
import java.util.List;

public interface UserDataAccess {
    User get(Long id);
    User getByUsername(String username);
    List<User> getAll();
    void save(User user);
    void update(User user);
    void delete(Long id);
}
