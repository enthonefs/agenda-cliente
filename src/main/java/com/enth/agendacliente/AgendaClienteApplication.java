package com.enth.agendacliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.enth.agendacliente.infrastructure.security.client")
public class AgendaClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendaClienteApplication.class, args);
	}

}
