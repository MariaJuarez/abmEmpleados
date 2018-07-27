package ar.com.tecnosoftware.somos.proyecto.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

@Entity
@Table(name="Proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int idProyecto;

    @NotBlank
    @NotEmpty
    @Column(name = "nombre")
    private String nombreProyecto;

    @NotNull
    @Column(name="duracion")
    private double duracionProyecto;

    public Proyecto(String nombreProyecto, double duracionProyecto) {
        this.nombreProyecto = nombreProyecto;
        this.duracionProyecto = duracionProyecto;
    }

    public Proyecto() {
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public double getDuracionProyecto() {
        return duracionProyecto;
    }

    public void setDuracionProyecto(double duracionProyecto) {
        this.duracionProyecto = duracionProyecto;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "idProyecto=" + idProyecto +
                ", nombreProyecto='" + nombreProyecto + '\'' +
                ", duracionProyecto=" + duracionProyecto +
                '}';
    }
}
