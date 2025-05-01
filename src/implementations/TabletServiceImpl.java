/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementations;

import Aplicacion.model.Tablet;
import Interfaces.TabletDataAccess;
import implementations.TabletDataAccessImpl;
import Interfaces.TabletService;
import java.util.List;

public class TabletServiceImpl implements TabletService {

    private final TabletDataAccess tabletDataAccess = new TabletDataAccessImpl();

    @Override
    public Tablet getDevice(Long id) {
        return tabletDataAccess.get(id);
    }

    @Override
    public List<Tablet> getAllDevices() {
        return tabletDataAccess.getAll();
    }

    @Override
    public void addDevice(Tablet tablet) {
        tabletDataAccess.save(tablet);
    }

    @Override
    public void updateDevice(Tablet tablet) {
        tabletDataAccess.update(tablet);
    }

    @Override
    public void removeDevice(Long id) {
        tabletDataAccess.delete(id);
    }
}
