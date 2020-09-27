package poc.minha.vez.domain;

import java.io.Serializable;

import lombok.Getter;

@Getter
public enum SituacaoSenhaEnum implements Serializable{

	PENDENTE,
	ATENDENDO,
	CONCLUIDO;
	
	public static SituacaoSenhaEnum getInstance(String situacao) {
		return valueOf(situacao);
	}
	
}
