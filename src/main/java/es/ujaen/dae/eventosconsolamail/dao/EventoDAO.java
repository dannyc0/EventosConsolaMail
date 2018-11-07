package es.ujaen.dae.eventosconsolamail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.ujaen.dae.eventosconsolamail.exception.ErrorCreacionEvento;
import es.ujaen.dae.eventosconsolamail.exception.SesionNoIniciadaException;
import es.ujaen.dae.eventosconsolamail.modelo.Evento;
import es.ujaen.dae.eventosconsolamail.modelo.Usuario;

@Repository
@Transactional(propagation=Propagation.REQUIRED)
public class EventoDAO {
	
	@PersistenceContext 
	EntityManager em;
	
	public EventoDAO() {}
	
	//Buscar evento
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public Evento buscar(int id) {
		return em.find(Evento.class, id);
	}
	
	//Buscar evento
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<Evento> buscarEventoPorTipoYDescripcion(String attr) {
		List<Evento> eventosBuscados = em.createQuery("SELECT e FROM Evento e WHERE e.tipo = :tipo OR e.descripcion LIKE :desc", Evento.class)
				.setParameter("tipo", attr)
				.setParameter("desc", "%"+attr+"%")
				.getResultList();
	
		return eventosBuscados;
	}
	
	//Obtener organizador del evento
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public Usuario obtenerOrganizadorEvento(int id) {
		//Transaccion activa
		Evento evento = em.find(Evento.class, id);
		Usuario organizador = evento.getOrganizador();
		return organizador;
	}
	
	//Crear evento
	public void insertar(Evento evento) {
		try {
			em.persist(evento);
			em.flush();
		} catch(Exception e) {
			throw new ErrorCreacionEvento();
		}
	}
	
	//Actualizar evento
	public void actualizar(Evento evento) {
		em.merge(evento);
	}
	
	//Borrar evento
	public void borrar(Evento evento) {
		em.remove(em.merge(evento));
	}
	
	//Inscribir lista invitado
	public void inscribirInvitado(Evento evento, Usuario usuario) {
		//Transaccion activa
		Evento eventoInscribir = em.find(Evento.class,evento.getId());
		Usuario usuarioInscribir = em.find(Usuario.class,usuario.getDni());
		
		eventoInscribir.getListaInvitados().add(usuarioInscribir);
	}
	
	//Cancelar de lista invitado
	public void cancelarInvitado(Evento evento, Usuario usuario) {
		//Transaccion activa
		Evento eventoInscribir = em.find(Evento.class,evento.getId());
		Usuario usuarioInscribir = em.find(Usuario.class,usuario.getDni());
		
		eventoInscribir.getListaInvitados().remove(usuarioInscribir);
	}
	
	//Quitar de lista espera
	@Transactional(noRollbackFor = Exception.class)
	public int cancelarEspera(Evento evento, Usuario usuario) {
		//Transaccion activa
		Evento eventoEspera = em.find(Evento.class,evento.getId());
		Usuario usuarioEspera = em.find(Usuario.class,usuario.getDni());
		//"SELECT e FROM Evento e JOIN e.listaEspera le WHERE ( KEY(le) = :llave and m = :valor )"
		try {
			int borrados = em.createQuery("DELETE FROM Evento e WHERE e IN (SELECT ev FROM Evento ev JOIN ev.listaEspera le WHERE ev.id = :id AND le.dni = :dni)", Evento.class)
					.setParameter("id" , eventoEspera.getId())
					.setParameter("dni" , usuarioEspera.getDni())
					.executeUpdate();
			return borrados;
		} catch (Exception e) {}
		return -1;
	}
	
	//Inscribir lista espera
	public void inscribirEspera(Evento evento, Usuario usuario) {
		//Transaccion activa
		Evento eventoInscribir = em.find(Evento.class,evento.getId());
		Usuario usuarioInscribir = em.find(Usuario.class,usuario.getDni());
		
		Calendar calendar = Calendar.getInstance();
		Timestamp fechaHoraActual = new Timestamp(calendar.getTime().getTime());
		
		eventoInscribir.getListaEspera().put(fechaHoraActual,usuarioInscribir);
	}
	
	//Validar invitado que no se repita en la lista
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public boolean validarInvitadoLista(Evento evento, Usuario usuario) {
		//Transaccion activa
		Evento eventoInscribir = em.find(Evento.class,evento.getId());
		Usuario usuarioInscribir = em.find(Usuario.class,usuario.getDni());
		List<Evento> eventoBuscado = null;
		
		try {
			eventoBuscado = em.createQuery("SELECT e FROM Evento e INNER JOIN e.listaInvitados l WHERE e.id = :id AND l.dni = :dni", Evento.class)
					.setParameter("id", eventoInscribir.getId())
					.setParameter("dni", usuarioInscribir.getDni())
					.getResultList();
		} catch (Exception e) {}
		
		if(eventoBuscado.isEmpty()) {
			return false;
		}else{
			return true;
		}
	}
	
	//Obtener tamaño actual de lista de invitados de un evento
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public long obtenerSaturacion(Evento evento) {
		Evento eventoInscribir = em.find(Evento.class,evento.getId());
		
		long saturacion = (Long) em.createQuery("SELECT COUNT(e.id) FROM Evento e INNER JOIN e.listaInvitados l WHERE e.id = :id")
				.setParameter("id", eventoInscribir.getId())
				.getSingleResult();
		
		return saturacion;
	}
	
	//Lista de eventos en espera
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<Evento> listarEventoEspera(Usuario usuario) {
		//Transaccion activa
		Usuario usuarioEspera = em.find(Usuario.class,usuario.getDni());
		List<Evento> eventoBuscado = null;
		
		try {
			eventoBuscado = em.createQuery("SELECT e FROM Evento e INNER JOIN e.listaEspera l WHERE l.dni = :dni", Evento.class)
					.setParameter("dni", usuarioEspera.getDni())
					.getResultList();
		} catch (Exception e) {}
		
		return eventoBuscado;
	}
	
	//Lista de eventos inscritos
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<Evento> listarEventoInscrito(Usuario usuario) {
		//Transaccion activa
		Usuario usuarioInscribir = em.find(Usuario.class,usuario.getDni());
		List<Evento> eventoBuscado = null;
		
		try {
			eventoBuscado = em.createQuery("SELECT e FROM Evento e INNER JOIN e.listaInvitados l WHERE l.dni = :dni", Evento.class)
					.setParameter("dni", usuarioInscribir.getDni())
					.getResultList();
		} catch (Exception e) {}
		
		return eventoBuscado;
	}
	
	//Lista de eventos organizados
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<Evento> listarEventoOrganizado(Usuario usuario) {
		//Transaccion activa
		Usuario organizador = em.find(Usuario.class,usuario.getDni());
		List<Evento> eventoBuscado = null;
		
		try {
			eventoBuscado = em.createQuery("SELECT e FROM Evento e WHERE e.organizador = :organizador", Evento.class)
					.setParameter("organizador", organizador)
					.getResultList();
		} catch (Exception e) {}
		
		return eventoBuscado;
	}
	
}