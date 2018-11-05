package es.ujaen.dae.eventosconsolamail.modelo;

import java.io.Serializable;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

//Bean o POJO
@Entity
public class Usuario implements Serializable {
    
        @Id
	String dni;
	String nombre;
	String correo;
	String telefono;
	String password;
	@OneToMany
	public Map<Integer,Evento> eventosOrganizados;
        
        @ManyToMany
        public Map<Integer,Evento> eventosEspera;
        
        @ManyToMany
	public Map<Integer, Evento> eventosInvitado;
	
	public Usuario() {
		eventosOrganizados=new TreeMap<>();
		eventosEspera=new TreeMap<>();
		eventosInvitado=new TreeMap<>();
	}

	public Usuario(String dni, String nombre, String correo, String telefono, String password) {
		this.dni = dni;
		this.nombre = nombre;
		this.correo = correo;
		this.telefono = telefono;
		this.password = password;
		eventosOrganizados=new TreeMap<>();
		eventosEspera=new TreeMap<>();
		eventosInvitado=new TreeMap<>();
		
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Usuario [dni=" + dni + ", nombre=" + nombre + ", correo=" + correo + ", telefono=" + telefono
				+ ", password=" + password + ", eventosOrganizados=" + eventosOrganizados + ", eventosEspera="
				+ eventosEspera + ", eventosInvitado=" + eventosInvitado + "]";
	}
	
	
}
