package br.com.zup.forum.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AutenticacaoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void deveriaDevolver400CasoDadosDeAutenticacaoEstejaIncorretos() {
		try {
			URI uri = new URI("/auth");
			String json = "{\"email\":\"invalido@emial.com\",\"senha\":\"123456\"}";
			
			this.mockMvc.perform(MockMvcRequestBuilders
						.post(uri)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(400));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}