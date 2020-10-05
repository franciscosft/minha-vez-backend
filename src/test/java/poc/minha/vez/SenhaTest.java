package poc.minha.vez;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SenhaTest {

	@Test
	public void validarFormatoNumero() {
		int senha = 10;
		assertEquals("P0010",String.format("P%04d", senha));
	}
	
	@Test
	public void exemploHeader() throws JsonProcessingException {
		Map<String, String> body = new HashMap<>();
		body.put("message", "ok");
		String writeValueAsString = new ObjectMapper().writeValueAsString(body);
		System.out.println(writeValueAsString);
	}
	
}
