/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementations;

import Interfaces.ComputerDataAccess;
import Aplicacion.model.Computer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ComputerDataAccessImpl implements ComputerDataAccess {
    private final Map<Long, Computer> computers = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public Computer get(Long id) {
        return computers.get(id);
    }

    @Override
    public List<Computer> getAll() {
        return new ArrayList<>(computers.values());
    }

    @Override
    public void save(Computer computer) {
        long id = nextId.getAndIncrement();
        computer.setId(id);
        computers.put(id, computer);
    }

    @Override
    public void update(Computer computer) {
        if (computers.containsKey(computer.getId())) {
            computers.put(computer.getId(), computer);
        }
    }

    @Override
    public void delete(Long id) {
        computers.remove(id);
    }
}