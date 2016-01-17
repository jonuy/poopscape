package co.poopscape.android.network.models;

/**
 * Created by jon on 12/27/15.
 */
public class User {
    String _id;
    String fname;
    String linit;
    String email;
    // @TODO array Review reviews
    // @TODO array CheckIn checkins

    private User(final UserBuilder userBuilder) {
        fname = userBuilder.fname;
        linit = userBuilder.linit;
        email = userBuilder.email;
    }

    /**
     * Builder
     */
    public static class UserBuilder {
        private String fname;
        private String linit;
        private String email;

        public UserBuilder fname(final String _fname) {
            this.fname = _fname;
            return this;
        }

        public UserBuilder linit(final String _linit) {
            this.linit = _linit;
            return this;
        }

        public UserBuilder email(final String _email) {
            this.email = _email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public String getId() {
        return _id;
    }

    public String getFirstName() {
        return fname;
    }

    public String getLastInit() {
        return linit;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "_id: " + _id + "\n" +
               "fname: " + fname + "\n" +
               "linit: " + linit + "\n" +
               "email: " + email + "\n";
    }
}
