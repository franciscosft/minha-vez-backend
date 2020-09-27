package poc.minha.vez.contador;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContadorDAO extends JpaRepository<Contador, Integer> {

}
