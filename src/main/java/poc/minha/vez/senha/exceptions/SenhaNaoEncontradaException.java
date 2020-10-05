package poc.minha.vez.senha.exceptions;

public class SenhaNaoEncontradaException extends Exception {

	private static final long serialVersionUID = -6011421447635425353L;
	
	public SenhaNaoEncontradaException() {
		super("Não foi possível encontrar nenhuma senha");
	}
}
