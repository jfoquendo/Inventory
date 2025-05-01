/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import Aplicacion.model.Tablet;
import java.util.List;

public interface TabletDataAccess {
    Tablet get(Long id);
    List<Tablet> getAll();
    void save(Tablet tablet);
    void update(Tablet tablet);
    void delete(Long id);
}
