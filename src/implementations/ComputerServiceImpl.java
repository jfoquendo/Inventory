/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementations;

import implementations.ComputerDataAccessImplCsv;
import Aplicacion.model.Computer;
import Interfaces.ComputerDataAccess;
import implementations.ComputerDataAccessImpl;
import Interfaces.ComputerService;
import Interfaces.UserService;
import java.util.List;

public class ComputerServiceImpl implements ComputerService {

    
    private final ComputerDataAccess computerDataAccess;
    private final UserService userService;

    public ComputerServiceImpl(UserService userService) {
        this.userService = userService;
        this.computerDataAccess = new ComputerDataAccessImplCsv(userService);
    }
    
    
    @Override
    public Computer getDevice(Long id) {
        return computerDataAccess.get(id);
    }

    @Override
    public List<Computer> getAllDevices() {
        return computerDataAccess.getAll();
    }

    @Override
    public void addDevice(Computer computer) {
        computerDataAccess.save(computer);
    }

    @Override
    public void updateDevice(Computer computer) {
        computerDataAccess.update(computer);
    }

    @Override
    public void removeDevice(Long id) {
        computerDataAccess.delete(id);
    }
}
