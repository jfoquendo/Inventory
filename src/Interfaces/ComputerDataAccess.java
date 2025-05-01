/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import Aplicacion.model.Computer;
import java.util.List;

public interface ComputerDataAccess {
    Computer get(Long id);
    List<Computer> getAll();
    void save(Computer computer);
    void update(Computer computer);
    void delete(Long id);
}
