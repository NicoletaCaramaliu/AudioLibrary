package auditManager;

import authentication.Command;
import authentication.SessionManager;
import java.util.List;
import java.util.Scanner;
import pagination.Paginator;
import sessionVerification.BaseCommand;

public class AuditCommand extends BaseCommand implements Command {
    private AuditRepository audits;
    private SessionManager sessionManager;
    private final int itemsPerPage;

    public AuditCommand(AuditRepository audits, SessionManager sessionManager, int itemsPerPage) {
        super(sessionManager);
        this.audits = audits;
        this.sessionManager = sessionManager;
        this.itemsPerPage = itemsPerPage;
    }

    @Override
    public void execute() {
        requireLoggedIn();
        requireAdmin();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.nextLine().trim();
        // get audits for a user
        List<Audit> userAudits = audits.getAuditsByUser(username);

        Paginator<Audit> paginator = new Paginator<>(userAudits, itemsPerPage);
        paginator.paginate();
    }
}
