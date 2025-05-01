/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aplicacion.model;


public abstract class Device {
    private Long id;
    private boolean active;
    private String serial;
    private String model;
    private String processor; // Se mueve aquí desde Computer
    private String ram;       // Se mueve aquí desde Computer
    private String hardDrive; // Se mueve aquí desde Computer
    private User assignedUser;
    private String userIdentification;
    private String userPosition;
    private String costCenter;
    private String area;
    private String management;
    private String location;
    private String contract;

    // Constructor (ajustado)
    public Device() {}

    public Device(boolean active, String serial, String model, String processor, String ram, String hardDrive, User assignedUser, String userIdentification, String userPosition, String costCenter, String area, String management, String location, String contract) {
        this.active = active;
        this.serial = serial;
        this.model = model;
        this.processor = processor;
        this.ram = ram;
        this.hardDrive = hardDrive;
        this.assignedUser = assignedUser;
        this.userIdentification = userIdentification;
        this.userPosition = userPosition;
        this.costCenter = costCenter;
        this.area = area;
        this.management = management;
        this.location = location;
        this.contract = contract;
    }

    // Getters y setters para todos los atributos
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getHardDrive() {
        return hardDrive;
    }

    public void setHardDrive(String hardDrive) {
        this.hardDrive = hardDrive;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getUserIdentification() {
        return userIdentification;
    }

    public void setUserIdentification(String userIdentification) {
        this.userIdentification = userIdentification;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getManagement() {
        return management;
    }

    public void setManagement(String management) {
        this.management = management;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }
}
