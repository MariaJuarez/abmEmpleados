package ar.com.tecnosoftware.abmEmpleadoApp.controller;
import ar.com.tecnosoftware.abmEmpleadoApp.exception.EmpleadoNotFoundException;
import ar.com.tecnosoftware.abmEmpleadoApp.model.Empleado;
import ar.com.tecnosoftware.abmEmpleadoApp.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

// Agregamos el @RestController "de esta forma Spring sabrá que debe ofrecer esta clase como Web Service Restful"
@RestController
public class EmpleadoController {

    @Autowired
    @Qualifier("empleadoService")
    private EmpleadoService empleadoService;

    //Metodo para crear empleado
    //RequestMapping le digo el valor del mapping y en este caso  su metodo va a agregar un dato, es por ello el POST
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    /*
     *Con el @Valid verifico si el dato que entra al parámetro sea correcto
     * y el @RequestBody indico que el parámetro de método debe estar vinculado al cuerpo de la solicitud web
     */
    public void addEmpleado(@Valid @RequestBody Empleado empleado) {
        empleadoService.addEmpleado(empleado);
    }

    /*        Metodo para editar empleado
    * En el @RequestMapping le enviamos un RequestMethod.PUT para editar
    * Con @PathVariable le indico el el valor que va a entrar viene en la URL
    * Con @RequestBody indico que el parámetro de método debe estar vinculado al cuerpo de la solicitud web
     Con ResponseEntity<> le valido y entrego el estatus http del objeto enviado*/
    @RequestMapping(value = "/edit/{empleadoId}", method = RequestMethod.PUT)
    public ResponseEntity<Empleado> editEmpleado(@PathVariable("empleadoId") Integer empleadoId, @Valid @RequestBody Empleado empleadoEdited) throws EmpleadoNotFoundException {
        if (empleadoService.editEmpleado(empleadoId, empleadoEdited)) {
            return ResponseEntity.ok(empleadoEdited);
        }
        throw new EmpleadoNotFoundException("El ID de empleado ingresado (" + empleadoId + ") no existe.");
    }

    //Metodo para eliminar empleado con el parametro ID
    @RequestMapping(value = "/eliminar/{empleadoId}", method = RequestMethod.DELETE)
    public ResponseEntity<Empleado> eliminarEmpleado(@PathVariable ("empleadoId") Integer empleadoId) throws EmpleadoNotFoundException {
        Empleado empleado=empleadoService.searchEmpleado(empleadoId);
        if(empleadoService.deleteEmpleado(empleadoId)){
            return ResponseEntity.ok(empleado);
        }
        throw new EmpleadoNotFoundException("No se encontro el empleado con el id: " + empleadoId);
    }

}