package es.ujaen.dae.eventosconsolamail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.ujaen.dae.eventosconsolamail.modelo.Evento;

@Repository
//@Transactional
public class EventoDAO {
	
	@PersistenceContext 
	EntityManager em;
	
	public void insertar(Evento evento) {
		System.out.println(evento.toString());
		em.persist(evento);
	}
	
}