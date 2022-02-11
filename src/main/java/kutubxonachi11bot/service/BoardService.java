package kutubxonachi11bot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import kutubxonachi11bot.entity.Book;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoardService {


    public static InlineKeyboardMarkup selectBoard(List<Book> books, int currentPage, boolean hasNextPage, String reqBook) {
        InlineKeyboardMarkup loadBookKeyboard = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> row1 = new ArrayList();
        List<InlineKeyboardButton> row2 = new ArrayList();
        List<InlineKeyboardButton> row3 = new ArrayList();

        for (int i = 0; i < books.size(); i++) {
            InlineKeyboardButton idBtn = new InlineKeyboardButton();
            idBtn.setText(i + 1 + "");
            idBtn.setCallbackData(books.get(i).getId() + "");

            if (row1.size() < 5) {
                row1.add(idBtn);
            } else if (row2.size() < 5) {
                row2.add(idBtn);
            } else {
                row3.add(idBtn);
            }

        }

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(row1);
        rowList.add(row2);
        rowList.add(row3);
        rowList.add(footerRow(currentPage,hasNextPage,reqBook));

        loadBookKeyboard.setKeyboard(rowList);
        return loadBookKeyboard;
    }

    private static List<InlineKeyboardButton> footerRow(int currentPage, boolean hasNextPage, String reqBook) {

        List<InlineKeyboardButton> footer = new ArrayList();

        InlineKeyboardButton footerPreviewBtn = new InlineKeyboardButton();
        footerPreviewBtn.setText("⬅️");
        footerPreviewBtn.setCallbackData(reqBook + "--" + (currentPage - 1));

        InlineKeyboardButton footerNextBtn = new InlineKeyboardButton();
        footerNextBtn.setText("➡️");
        footerNextBtn.setCallbackData(reqBook + "--" + (currentPage + 1));

        InlineKeyboardButton footerDeleteBtn = new InlineKeyboardButton();
        footerDeleteBtn.setText("\uD83D\uDEAB");
        footerDeleteBtn.setCallbackData("delete");

        if (currentPage != 0) {
            footer.add(footerPreviewBtn);
        }
        footer.add(footerDeleteBtn);
        if (hasNextPage) {
            footer.add(footerNextBtn);
        }

        return footer;
    }

}
