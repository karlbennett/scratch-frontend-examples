package cucumber.scratch.frontend.examples.page;

public interface HomePage {

    void visit();

    void clickRegister();

    void clickSignIn();

    void clickSignOut();

    boolean isCurrentPage();

    String getUsername();
}
