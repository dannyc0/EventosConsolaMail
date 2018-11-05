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
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import es.ujaen.dae.eventosconsolamail.cliente.ClienteOrganizadoraEventos;
import es.ujaen.dae.eventosconsolamail.dao.EventoDAO;
import es.ujaen.dae.eventosconsolamail.modelo.Evento;
import es.ujaen.dae.eventosconsolamail.modelo.Usuario;

@EnableAutoConfiguration
@EnableConfigurationProperties
@ComponentScan({"es.ujaen.dae.eventosconsolamail.bean","es.ujaen.dae.eventosconsolamail.dao"})
@EntityScan(basePackages= {"es.ujaen.dae.eventosconsolamail.modelo"})
@SpringBootApplication
public class ServidorOrganizadoraEventos {
	
//	@Bean
//	TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
//		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
//		transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
//		this.transactionTemplate(transactionManager).setTimeout(30);
//		return transactionTemplate;
//	}
	
	public static void main(String[] args) throws IOException {
		SpringApplication servidor = new SpringApplication(ServidorOrganizadoraEventos.class);
		ApplicationContext ctx = servidor.run(args);
		
		ClienteOrganizadoraEventos cliente = new ClienteOrganizadoraEventos(ctx);
		cliente.run();	
	}
}