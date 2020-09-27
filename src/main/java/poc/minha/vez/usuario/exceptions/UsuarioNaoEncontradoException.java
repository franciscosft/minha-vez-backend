package poc.minha.vez.usuario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = -8724970299914541071L;
	
	public UsuarioNaoEncontradoException(int idUsuario) {
		super("Não foi possível encontrar o usuário com ID: " + idUsuario);
	}

}
