package endpoints;

public enum UserEndpoints {

    LIST_USERS("/users"),
    SINGLE_USER("/users/{id}"),
    CREATE_USER("/users"),
    REGISTER_USER("/register");

    private final String endpoint;

    UserEndpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
