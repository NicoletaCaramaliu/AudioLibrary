package authentication;

public class LogoutCommand implements Command{
    private final Authentication authentication;

    public LogoutCommand(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public void execute() {
        String result = authentication.logout();
        System.out.println(result);
    }
}
