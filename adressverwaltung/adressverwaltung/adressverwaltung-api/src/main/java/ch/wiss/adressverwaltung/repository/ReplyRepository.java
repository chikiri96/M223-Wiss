package ch.wiss.adressverwaltung.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.wiss.adressverwaltung.models.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    
}
