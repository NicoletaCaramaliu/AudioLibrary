package auditManager;

public class Audit {
    private int auditId;
    private final String username;
    private final String action;
    private final Boolean success;

    public Audit(int auditId, String username, String action, Boolean success) {
        this.auditId = auditId;
        this.username = username;
        this.action = action;
        this.success = success;
    }
}
