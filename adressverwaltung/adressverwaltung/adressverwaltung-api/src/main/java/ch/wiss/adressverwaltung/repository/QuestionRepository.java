package ch.wiss.adressverwaltung.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.wiss.adressverwaltung.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
