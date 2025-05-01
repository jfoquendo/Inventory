/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aplicacion.model;


public class Computer extends Device {
    private String operatingSystem;

    public Computer() {
        super();
    }

    public Computer(boolean active, String serial, String model, String processor, String ram, String hardDrive, User assignedUser, String userIdentification, String userPosition, String costCenter, String area, String management, String location, String contract, String operatingSystem) {
        super(active, serial, model, processor, ram, hardDrive, assignedUser, userIdentification, userPosition, costCenter, area, management, location, contract);
        this.operatingSystem = operatingSystem;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }
}