package poc.minha.vez.senha;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import poc.minha.vez.usuario.UsuarioDTO;

@Setter
@Getter
public class SenhaDTO implements Serializable{
	
	private static final long serialVersionUID = 8541191132295316416L;

	private Integer id;
	
	private String numero;
	
	private boolean preferencial;
	
	private UsuarioDTO usuario;
	
	private SituacaoSenhaEnum situacaoSenha;

}
