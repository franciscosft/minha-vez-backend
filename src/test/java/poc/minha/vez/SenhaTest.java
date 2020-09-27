package poc.minha.vez;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class SenhaTest {

	@Test
	public void validarFormatoNumero() {
		int senha = 10;
		assertEquals("P0010",String.format("P%04d", senha));
	}
	
}
