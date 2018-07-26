package ar.com.tecnosoftware.somos.empleados.controller;
import ar.com.tecnosoftware.somos.empleados.exception.EmpleadoNotFoundException;
import ar.com.tecnosoftware.somos.empleados.entity.Empleado;
import ar.com.tecnosoftware.somos.empleados.service.EmpleadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

 * Clase controladora la cual recibe peticiones desde la WEB y da entrada a los metodos de ABM para los empleados

 * TODO @author: Maria J. Juarez P.

 * @version: 25/07/2018/A

 */


/** Agregamos el @RestController y de esta forma Spring sabrá que debe ofrecer esta clase como Web Service Restfull
 * combina las anotaciones {@link Controller @Controller} y {@link ResponseBody @ResponseBody} , que elimina la necesidad
 * de anotar cada método de manejo de solicitudes de la clase de controlador con la anotación @ResponseBody.
 * @see más en <a href = "http://www.baeldung.com/spring-controller-vs-restcontroller" /> baeldung.com – RestController en Spring </a>
 *
 * Agregamos el @CrossOrigin que habilita la comunicación entre dominios.
 * @see más en <a href = "http://www.baeldung.com/spring-cors" /> baeldung.com – CROS en Spring </a>
 *
 * Agregamos el @RequestMapping que asigna las solicitudes web a los métodos del Controller.
 * @see más en <a href = "http://www.baeldung.com/spring-requestmapping" /> baeldung.com – RequestMapping en Spring </a>
 * en el que al colocar por ejemplo "http://localhost8080/empleado" ya que estaría en contact con el controlador.
 **/
@RestController
@CrossOrigin
@RequestMapping("/empleado")
public class EmpleadoController {


    @Autowired
    private EmpleadoService empleadoService;

    /** Metodo para crear empleado
     *  {@link PostMapping @PostMapping} Es parte del @RequestMapping para recibir una peticion que se mappea hasta este método
     *  y se usa POST al ser un metodo que agrega un nuevo dato.
     *  tambien puede usarse @RequestMapping(value = "/crear", method = RequestMethod.POST)
     *
     *
     *  Con el @Valid verifico si el dato que entra al parámetro sea correcto
     *  y el @RequestBody indico que el parámetro de método debe estar vinculado al cuerpo de la solicitud web
     **/
    @PostMapping (value = "/crear")
    public void addEmpleado(@Valid @RequestBody Empleado empleado) {
        empleadoService.addEmpleado(empleado);
    }


    /** Metodo para editar empleado
     * {@link PutMapping @PutMapping} le enviamos un RequestMethod.PUT para editar.
     * tambien puede usarse @RequestMapping(value = "/editar", method = RequestMethod.PUT)
     *
     * @param empleadoEdited se recibe un emplado en JSON
     * Con ResponseEntity<> se valido y entrega el estatus http del objeto enviado.
     * @return con el ResponseEntity entrega un estado de tipo HTTP para enviarle
     *
     **/
    @PutMapping(value = "/editar")
    public ResponseEntity<Empleado> editEmpleado(@Valid @RequestBody Empleado empleadoEdited) throws EmpleadoNotFoundException {
        if (empleadoService.editEmpleado(empleadoEdited.getId(), empleadoEdited)) {
            return ResponseEntity.ok(empleadoEdited);
        }
        throw new EmpleadoNotFoundException("No se encontro el empleado con el id: " + empleadoEdited.getId());
    }

/*    Metodo para eliminar empleado con el parametro ID
        * Con @PathVariable le indico que el valor que va a entrar viene en la URL */

    @DeleteMapping(value = "/eliminar/{empleadoId}")
    public ResponseEntity<Empleado> deleteEmpleado(@PathVariable ("empleadoId") int empleadoId) throws EmpleadoNotFoundException {
        Empleado empleado=empleadoService.searchEmpleado(empleadoId);
        if(empleadoService.deleteEmpleado(empleadoId)){
            return ResponseEntity.ok(empleado);
        }
        throw new EmpleadoNotFoundException("No se encontro el empleado con el id: " + empleadoId);
    }

    @GetMapping("/list")
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