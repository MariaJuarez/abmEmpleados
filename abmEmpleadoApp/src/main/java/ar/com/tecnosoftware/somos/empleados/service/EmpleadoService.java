package ar.com.tecnosoftware.somos.empleados.service;

import ar.com.tecnosoftware.somos.empleados.entity.Empleado;
import ar.com.tecnosoftware.somos.empleados.repository.EmpleadoInterfaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class EmpleadoService implements EmpleadoServiceInterface{

    @Autowired
    private EmpleadoInterfaceRepository empleadoInterfaceRepository;

    @Override
    public List<Empleado> listEmpleados() {
        return empleadoInterfaceRepository.findAll();
    }

    @Override
    public void addEmpleado(Empleado empleado) {
        empleadoInterfaceRepository.save(empleado);
    }

    @Override
    public boolean deleteEmpleado(int empleadoId) {
        if (empleadoInterfaceRepository.findOne(empleadoId) == null) {
            return false;
        }
        empleadoInterfaceRepository.delete(empleadoId);
        return true;
    }

    @Override
    public boolean editEmpleado(int empleadoId, Empleado empleadoEdited) {
        if (empleadoInterfaceRepository.findOne(empleadoId) == null) {
            return false;
        }
        empleadoEdited.setId(empleadoId);
        empleadoInterfaceRepository.save(empleadoEdited);
        return true;
    }

    @Override
    public Empleado searchEmpleado(int empleadoId) {
        return empleadoInterfaceRepository.findOne(empleadoId);
    }

    }


