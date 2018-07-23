package ar.com.tecnosoftware.abmEmpleadoApp.controller;
import ar.com.tecnosoftware.abmEmpleadoApp.exception.EmpleadoNotFoundException;
import ar.com.tecnosoftware.abmEmpleadoApp.model.Empleado;
import ar.com.tecnosoftware.abmEmpleadoApp.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Agregamos el @RestController "de esta forma Spring sabrá que debe ofrecer esta clase como Web Service Restful"
@RestController
@CrossOrigin
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    //Metodo para crear empleado
    //RequestMapping le digo el valor del mapping y en este caso  su metodo va a agregar un dato, es por ello el POST
    @RequestMapping(value = "/crear", method = RequestMethod.POST)
    /*
     *Con el @Valid verifico si el dato que entra al parámetro sea correcto
     * y el @RequestBody indico que el parámetro de método debe estar vinculado al cuerpo de la solicitud web
     */
    public void addEmpleado(@Valid @RequestBody Empleado empleado) {
        empleadoService.addEmpleado(empleado);
    }

    /*        Metodo para editar empleado
    * En el @RequestMapping le enviamos un RequestMethod.PUT para editar
    * Con @RequestBody indico que el parámetro de método debe estar vinculado al cuerpo de la solicitud web
     Con ResponseEntity<> le valido y entrego el estatus http del objeto enviado*/
    @RequestMapping(value = "/editar", method = RequestMethod.PUT)
    public ResponseEntity<Empleado> editEmpleado(@Valid @RequestBody Empleado empleadoEdited) throws EmpleadoNotFoundException {
        if (empleadoService.editEmpleado(empleadoEdited.getId(), empleadoEdited)) {
            return ResponseEntity.ok(empleadoEdited);
        }
        throw new EmpleadoNotFoundException("No se encontro el empleado con el id: " + empleadoEdited.getId());
    }


/*    Metodo para eliminar empleado con el parametro ID
        * Con @PathVariable le indico que el valor que va a entrar viene en la URL */

    @RequestMapping(value = "/eliminar/{empleadoId}", method = RequestMethod.DELETE)
    public ResponseEntity<Empleado> deleteEmpleado(@PathVariable ("empleadoId") int empleadoId) throws EmpleadoNotFoundException {
        Empleado empleado=empleadoService.searchEmpleado(empleadoId);
        if(empleadoService.deleteEmpleado(empleadoId)){
            return ResponseEntity.ok(empleado);
        }
        throw new EmpleadoNotFoundException("No se encontro el empleado con el id: " + empleadoId);
    }

    @RequestMapping("/list")
    public List<Empleado> listEmpleados() {
        return empleadoService.listEmpleados();
    }

    @RequestMapping("/search/{empleadoId}")
    public ResponseEntity<Empleado> searchEmpleado(@PathVariable ("empleadoId") int empleadoId) throws  EmpleadoNotFoundException{
        Empleado empleado = empleadoService.searchEmpleado(empleadoId);
        if (empleado == null) {
            throw new EmpleadoNotFoundException("No se encontro el empleado con el id: " + empleadoId);
        }
        return ResponseEntity.ok(empleado);
    }

    //
    @ExceptionHandler(EmpleadoNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, String> onException(EmpleadoNotFoundException e) {
        return Collections.singletonMap("mensaje", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, Map<String, String>> validationError(MethodArgumentNotValidException ex) {
        Map<String, String> map = new HashMap<>();
        Map<String, Map<String, String>> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        errors.put("errores", map);
        return errors;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, Map<String, String>> validationError(BindException ex) {
        Map<String, String> map = new HashMap<>();
        Map<String, Map<String, String>> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        errors.put("errores", map);
        return errors;
    }


}