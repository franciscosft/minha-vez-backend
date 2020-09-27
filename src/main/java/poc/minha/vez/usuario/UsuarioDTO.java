package poc.minha.vez.usuario;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO implements Serializable{

	private static final long serialVersionUID = 500618274550647929L;
	
	private Integer id;
	
	private TipoUsuarioEnum tipoUsuario;

}
