package poc.minha.vez.domain;

import java.io.Serializable;

import lombok.Getter;

@Getter
public enum TipoUsuarioEnum implements Serializable{
	
	GERENTE,
	CLIENTE;
	
	public static TipoUsuarioEnum getInstance(String situacao) {
		return valueOf(situacao);
	}

}
