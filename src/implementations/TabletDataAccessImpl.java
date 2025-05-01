/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementations;

import Interfaces.TabletDataAccess;
import Aplicacion.model.Tablet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class TabletDataAccessImpl implements TabletDataAccess {
    private final Map<Long, Tablet> tablets = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public Tablet get(Long id) {
        return tablets.get(id);
    }

    @Override
    public List<Tablet> getAll() {
        return new ArrayList<>(tablets.values());
    }

    @Override
    public void save(Tablet tablet) {
        long id = nextId.getAndIncrement();
        tablet.setId(id);
        tablets.put(id, tablet);
    }

    @Override
    public void update(Tablet tablet) {
        if (tablets.containsKey(tablet.getId())) {
            tablets.put(tablet.getId(), tablet);
        }
    }

    @Override
    public void delete(Long id) {
        tablets.remove(id);
    }
}