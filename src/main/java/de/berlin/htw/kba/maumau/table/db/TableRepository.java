package de.berlin.htw.kba.maumau.table.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface TableRepository extends JpaRepository<GameTable, String> {

}
