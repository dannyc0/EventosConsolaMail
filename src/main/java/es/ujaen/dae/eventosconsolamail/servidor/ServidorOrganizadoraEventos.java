package es.ujaen.dae.eventosconsolamail.servidor;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import es.ujaen.dae.eventosconsolamail.cliente.ClienteOrganizadoraEventos;

//@ImportResource("classpath:context.xml")
@ComponentScan("es.ujaen.dae.eventosconsolamail.bean")
@SpringBootApplication
public class ServidorOrganizadoraEventos {
	public static void main(String[] args) throws IOException {
		SpringApplication servidor = new SpringApplication(ServidorOrganizadoraEventos.class);
		ApplicationContext ctx = servidor.run(args);
		
		ClienteOrganizadoraEventos cliente = new ClienteOrganizadoraEventos(ctx);
		cliente.run();
	}
}