package com.company;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    /*Az alabbi harom marad 'public' elerhetoseggel, mert igy konnyebb lesz majd kezelni. Mivel
    leglabb ket hazra van internetes elres, ezert listat alkalmazunk, ahova legalabb ketto hazra
    elhelyezhetoek az adatok. Megfelelo sorrendben kell oket elhelyezni a listakban.*/

    List<Monitor> monitors = new ArrayList<>();
    List<Driver> drivers = new ArrayList<>();
    List<Loader> loaders = new ArrayList<>();

    //constructor mindharom adattaggal

    public Controller(List<Monitor> monitors, List<Driver> drivers, List<Loader> loaders) {
        this.monitors = monitors;
        this.drivers = drivers;
        this.loaders = loaders;
    }

    //settere es getterek kovetkeznek, ha kesobb kellenenek
    public List<Monitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<Monitor> monitors) {
        this.monitors = monitors;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public List<Loader> getLoaders() {
        return loaders;
    }

    public void setLoaders(List<Loader> loaders) {
        this.loaders = loaders;
    }

    public void addMonitor(Monitor m) {
        monitors.add(m);
    }

    public void addDriver(Driver d) {
        drivers.add(d);
    }

    public void addLoader(Loader l) {
        loaders.add(l);
    }

}
