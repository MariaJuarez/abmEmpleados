package ar.com.tecnosoftware.abmEmpleadoApp.service;

import ar.com.tecnosoftware.abmEmpleadoApp.model.Empleado;

import java.util.List;

public interface EmpleadoServiceInterface {
    public abstract List<Empleado> listEmpleados();
    public abstract Empleado addEmpleado(Empleado empleado);
    public abstract boolean deleteEmpleado(int empleadoId);
    public abstract boolean editEmpleado(int empleadoId, Empleado empleadoEdited);
    public abstract Empleado searchEmpleado(int empleadoId);
}
