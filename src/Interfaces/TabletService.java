/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import Aplicacion.model.Tablet;
import java.util.List;

public interface TabletService {
    Tablet getDevice(Long id);
    List<Tablet> getAllDevices();
    void addDevice(Tablet tablet);
    void updateDevice(Tablet tablet);
    void removeDevice(Long id);
}