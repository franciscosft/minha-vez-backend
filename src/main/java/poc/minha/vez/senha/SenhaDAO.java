package poc.minha.vez.senha;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SenhaDAO extends JpaRepository<Senha, Integer>   {

	@Transactional(readOnly=true)
	List<Senha> findBySituacaoSenha(SituacaoSenhaEnum situacao);
	

}
