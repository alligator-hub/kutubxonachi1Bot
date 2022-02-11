package kutubxonachi11bot.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    @Value("${admin.username}")
    private String adminUsername;

    public static boolean isBookId(String queryData) {
        for (int i = 0; i < queryData.length(); i++) {
            boolean digit = Character.isDigit(queryData.charAt(i));
            if (!digit) {
                return false;
            }
        }
        return true;
    }

    public boolean isStart(String txt) {
        return "/start".equals(txt);
    }

    public boolean isStatics(String txt) {
        return "/statics".equals(txt);
    }

    public boolean isAdmin(String txt) {
        return adminUsername.equals(txt);
    }
}
