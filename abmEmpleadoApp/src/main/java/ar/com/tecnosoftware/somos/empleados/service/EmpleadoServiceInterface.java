package ar.com.tecnosoftware.somos.empleados.service;

import ar.com.tecnosoftware.somos.empleados.entity.Empleado;

import java.util.List;

public interface EmpleadoServiceInterface {
    public List<Empleado> listEmpleados();
    public void addEmpleado(Empleado empleado);
    public boolean deleteEmpleado(int empleadoId);
    public boolean editEmpleado(int empleadoId, Empleado empleadoEdited);
    public Empleado searchEmpleado(int empleadoId);
}
