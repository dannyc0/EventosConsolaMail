package es.ujaen.dae.eventosconsolamail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

@Repository
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class EventoDAO {
	
	@PersistenceContext 
	EntityManager em;
	
	public EventoDAO() {
	}
	
	//Buscar evento
	public Evento buscar(int id) {
		return em.find(Evento.class, id);
	}
	
	//Crear evento
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=ErrorCreacionEvento.class, readOnly=false)
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
	
}