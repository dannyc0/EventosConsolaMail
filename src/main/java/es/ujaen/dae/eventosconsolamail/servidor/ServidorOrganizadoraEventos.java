package es.ujaen.dae.eventosconsolamail.servidor;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import es.ujaen.dae.eventosconsolamail.cliente.ClienteOrganizadoraEventos;
import es.ujaen.dae.eventosconsolamail.dao.EventoDAO;
import es.ujaen.dae.eventosconsolamail.modelo.Evento;
import es.ujaen.dae.eventosconsolamail.modelo.Usuario;

@EnableAutoConfiguration
@EnableConfigurationProperties
@ComponentScan("es.ujaen.dae.eventosconsolamail.bean")
@EntityScan(basePackages= {"es.ujaen.dae.eventosconsolamail.bean.modelo"})
@SpringBootApplication
public class ServidorOrganizadoraEventos {
	
	public static void main(String[] args) throws IOException {
		SpringApplication servidor = new SpringApplication(ServidorOrganizadoraEventos.class);
		ApplicationContext ctx = servidor.run(args);
		
		EventoDAO evento = new EventoDAO(); 
		
		evento.insertar(new Evento("evento","aa","aa","aa","aa",1,new Usuario()));
		
		ClienteOrganizadoraEventos cliente = new ClienteOrganizadoraEventos(ctx);
		cliente.run();	
	}
}