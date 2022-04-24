package co.com.meli.jpa;

import co.com.meli.jpa.mutant.MutantData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

public interface JPARepository extends CrudRepository<MutantData, String>, QueryByExampleExecutor<MutantData> {
    @Query(value = "SELECT md FROM MutantData md WHERE md.dna=?1")
    Optional<MutantData> findByDna(String dna);
}
