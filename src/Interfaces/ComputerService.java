/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;


import Aplicacion.model.Computer;
import java.util.List;

public interface ComputerService {
    Computer getDevice(Long id);
    List<Computer> getAllDevices();
    void addDevice(Computer computer);
    void updateDevice(Computer computer);
    void removeDevice(Long id);
}
