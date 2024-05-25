package auditManager;

import java.util.List;

public interface AuditRepository {
    List<Audit> getAllAudits();

    List<Audit> getAuditsByUser(String username);

    void addAudit(Audit audit);
}
