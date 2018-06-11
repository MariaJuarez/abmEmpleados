package ar.com.tecnosoftware.abmEmpleadoApp.repository;

import ar.com.tecnosoftware.abmEmpleadoApp.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository ("empleadoInterfaceRepository")
public interface EmpleadoInterfaceRepository extends JpaRepository<Empleado, Integer> {
}
