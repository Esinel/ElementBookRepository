package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity;

/**
 * Created by alligator on 17.1.17..
 */
public enum UserRole {
    GUEST("guest"),
    SUBSCRIBER("subscriber"),
    ADMIN("administrator");

    private String userRoleString;

    UserRole(String userRoleString) {
        this.userRoleString = userRoleString;
    }

    public String userRoleString() {
        return userRoleString;
    }
}
