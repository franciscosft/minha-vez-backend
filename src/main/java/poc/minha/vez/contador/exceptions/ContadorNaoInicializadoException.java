package poc.minha.vez.contador.exceptions;

public class ContadorNaoInicializadoException extends RuntimeException {

	private static final long serialVersionUID = 4714711275825885661L;

	public ContadorNaoInicializadoException() {
		super("O contador não foi inicializado ou foi zerado");
	}
	
}
