/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aplicacion.model;


public class Tablet extends Device {
    private String screenSize;
    private String operatingSystem;
    private String storage;

    public Tablet() {
        super();
    }

    public Tablet(boolean active, String serial, String model, String processor, String ram, String hardDrive, User assignedUser, String userIdentification, String userPosition, String costCenter, String area, String management, String location, String contract, String screenSize, String operatingSystem, String storage) {
        super(active, serial, model, processor, ram, hardDrive, assignedUser, userIdentification, userPosition, costCenter, area, management, location, contract);
        this.screenSize = screenSize;
        this.operatingSystem = operatingSystem;
        this.storage = storage;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }
}
