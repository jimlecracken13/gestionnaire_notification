package utils;

public class Utils {
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static boolean estEmailValide(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }
}
