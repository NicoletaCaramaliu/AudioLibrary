package auditManager;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAuditRepository implements AuditRepository {
    private final List<Audit> Audit = new ArrayList<>();

    @Override
    public void addAudit(Audit audit) {
        Audit.add(audit);
    }

    @Override
    public List<Audit> getAllAudits() {
        return new ArrayList<>(Audit);
    }

    @Override
    public List<Audit> getAuditsByUser(String username) {
        List<Audit> userAudits = new ArrayList<>();
        for (Audit audit : Audit) {
            if (audit.getUsername().equals(username)) {
                userAudits.add(audit);
            }
        }
        return userAudits;
    }
}
