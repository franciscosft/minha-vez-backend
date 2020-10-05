package poc.minha.vez.exceptions;

public class OperacaoNegadaException extends Exception {

	private static final long serialVersionUID = -5978103903411958177L;

		public OperacaoNegadaException() {
			super("Ops, você não pode executar essa operação");
		}
}
