package ar.com.tecnosoftware.abmEmpleadoApp.service;

import ar.com.tecnosoftware.abmEmpleadoApp.model.Empleado;

import java.util.List;

public interface EmpleadoServiceInterface {
    public List<Empleado> listEmpleados();
    public void addEmpleado(Empleado empleado);
    public boolean deleteEmpleado(int empleadoId);
    public boolean editEmpleado(int empleadoId, Empleado empleadoEdited);
    public Empleado searchEmpleado(int empleadoId);
}
