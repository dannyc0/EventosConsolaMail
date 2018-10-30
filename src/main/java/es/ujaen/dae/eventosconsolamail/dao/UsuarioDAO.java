package es.ujaen.dae.eventosconsolamail.dao;

import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

//Bean o POJO
public class UsuarioDAO {
	String dni;
	String nombre;
	String correo;
	String telefono;
	String password;
	
	public Map<Integer,EventoDAO> eventosOrganizados;
	public Map<Integer,EventoDAO> eventosEspera;
	public Map<Integer, EventoDAO> eventosInvitado;
	
	public UsuarioDAO() {
		eventosOrganizados=new TreeMap<>();
		eventosEspera=new TreeMap<>();
		eventosInvitado=new TreeMap<>();
	}

	public UsuarioDAO(String dni, String nombre, String correo, String telefono, String password) {
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
