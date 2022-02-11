package kutubxonachi11bot.enums;

import com.fasterxml.jackson.core.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Statics {
    BOOKS_NOT_FOUND_ALERT_ADMIN("Bizning bazada hozircha bunday kitob topilmadi, " +
            "yoki siz kitob nomini noto'g'ri kiritdingiz. Kitobni topish uchun" +
            " @kitobxonlarguruhi1 ga qo'shiling va adminlar sizga kitobni topib berishadi"),
    BOOK_NOT_FOUND("Ushbu kitob mavjud emas"),
    START_TXT("\uD83D\uDCDAKitoblar olamiga xush kelibsiz\uD83D\uDCDA"),
    PLACEHOLDER_SEARCH("Menga kitob nomi yoki muallif ismini yuboring"),
    NOT_PERMISSION("Ruxsat yo`q!!!"),
    COPY_MSG_CAPTION("Yuklab olingan bot \uD83D\uDC49 @kutubxonachi1Bot"),
    SHORT_LENGTH("Kitob yoki muallif nomi juda qisqa ❗️")


    ;
    private final String text;
}
