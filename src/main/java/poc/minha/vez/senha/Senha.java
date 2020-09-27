package poc.minha.vez.senha;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import poc.minha.vez.usuario.Usuario;

@Getter
@Setter
@Entity
public class Senha {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	private String numero;
	
	private boolean preferencial;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@Enumerated(EnumType.STRING)
	private SituacaoSenhaEnum situacaoSenha;

}
