package br.gov.pa.prodepa.pae.protocolo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

import br.gov.pa.prodepa.pae.common.domain.dto.OrgaoPaeBasicDto;
import br.gov.pa.prodepa.pae.common.domain.dto.UsuarioDto;

@EnableJms
@SpringBootApplication()
@ComponentScan(basePackages = {"br.gov.pa.prodepa.pae.common", "br.gov.pa.prodepa.pae.protocolo"})
public class PaeProtocoloProducerServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(PaeProtocoloProducerServiceApp.class, args);
	}
	
	@Bean 
	public UsuarioDto criarUSuarioLogadoMock() {
		return UsuarioDto.builder()
				.id(3199L)
				.nome("Antonio Junior")
				.cpf("66810574204")
				.orgao(new OrgaoPaeBasicDto(1L, "Prodepa", "Empresa de Tecnologia e comunicacao do Para", "05059613000118"))
				.build();
	}

}

