package homeoffice.PageObject.general;


import org.openqa.selenium.By;

import java.util.List;

public interface AcceptedUsernamesLoginPage {
    By LOGIN_CREDENTIALS_CONTAINER = By.cssSelector("[data-test='login-credentials-container']");
    By ACCEPTED_USERNAMES_CONTAINER = By.cssSelector("[data-test='login-credentials']");
    By PASSWORD_FOR_ALL_USERS_CONTAINER = By.cssSelector("[data-test='login-password']");
    List<String> ACCEPTED_USERNAMES = List.of("standard_user", "locked_out_user", "problem_user", "performance_glitch_user", "error_user", "visual_user");
    String PASSWORD_FOR_ALL_USERS = "secret_sauce";

    void loginDetail(String var1, String var2);

    default List<String> acceptedUsersLogin() {
        return ACCEPTED_USERNAMES;

    }
}
