package cn.msa.msa_museum_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.msa.msa_museum_server.service.JwtService;

@RestController
@SpringBootApplication
public class MsaMuseumServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsaMuseumServerApplication.class, args);
	}

}

