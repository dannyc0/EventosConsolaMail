package es.ujaen.dae.eventosconsolamail.servidor;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import es.ujaen.dae.eventosconsolamail.cliente.ClienteOrganizadoraEventos;

//@ImportResource("classpath:context.xml")
@ComponentScan("es.ujaen.dae.eventosconsolamail.bean")
@SpringBootApplication
public class ServidorOrganizadoraEventos {
	
//	@Bean
//	DataSource dataSource() {
//		DataSource dataSource = new SingleConnectionDataSource();
//		dataSource.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
//		dataSource.setUrl("jdbc:derby://localhost:1527/dae");
//		dataSource.setUsername(“dae”);
//		dataSource.setPassword(“dae”);
//		return conexionBD;
//	}
	
	
	public static void main(String[] args) throws IOException {
		SpringApplication servidor = new SpringApplication(ServidorOrganizadoraEventos.class);
		ApplicationContext ctx = servidor.run(args);
		
		ClienteOrganizadoraEventos cliente = new ClienteOrganizadoraEventos(ctx);
		cliente.run();
		
		
	}
}