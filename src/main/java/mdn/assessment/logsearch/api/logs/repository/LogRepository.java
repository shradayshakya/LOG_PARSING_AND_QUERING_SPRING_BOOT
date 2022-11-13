package mdn.assessment.logsearch.api.logs.repository;

import mdn.assessment.logsearch.api.logs.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long>, CustomLogRepository {
}
