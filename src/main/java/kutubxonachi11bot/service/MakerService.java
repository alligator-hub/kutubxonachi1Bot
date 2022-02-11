package kutubxonachi11bot.service;

import kutubxonachi11bot.enums.Statics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import kutubxonachi11bot.entity.Book;

import java.util.List;

@Service
public class MakerService {


    public SendMessage makeSendBooksList(Page<Book> bookPages, Long chatId, int currentPage, boolean hasNextPage, String reqBook) {


        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId + "");
        sendMessage.setParseMode(ParseMode.HTML);

        if (bookPages.getTotalElements() < 1) {
            sendMessage.setText(Statics.BOOKS_NOT_FOUND_ALERT_ADMIN.getText());
            return sendMessage;
        }

        int from = bookPages.getPageable().getPageNumber() * 10 + 1;
        long to = (bookPages.toList().size() == 10) ? (bookPages.getPageable().getPageNumber() + 1) * 10L : bookPages.getTotalElements();
        String text = "<b>" + bookPages.getTotalElements() + " natija, ko'rsatilyapti " +
                from + "-" + to + "</b>\n\n";

        List<Book> booksList = bookPages.toList();

        for (int i = 0; i < booksList.size(); i++) {
            Book book = booksList.get(i);

            text += i + 1 + ")" + " <b>" + book.getName() + "</b>--" + book.getAuthor() +" <b><i>"+ book.getType().toUpperCase()+ "</i></b>\n";

        }
        sendMessage.setText(text);

        InlineKeyboardMarkup selectBoard = BoardService.selectBoard(booksList, currentPage, hasNextPage, reqBook);

        sendMessage.setReplyMarkup(selectBoard);


        return sendMessage;

    }

    public EditMessageText makeEditBooksList(Page<Book> bookPages, Long chatId, Integer messageId, int currentPage, boolean hasNextPage, String reqBook) {


        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(messageId);
        editMessageText.setChatId(chatId + "");
        editMessageText.setParseMode(ParseMode.HTML);

        if (bookPages.getTotalElements() < 1) {
            editMessageText.setText(Statics.BOOKS_NOT_FOUND_ALERT_ADMIN.getText());
            return editMessageText;
        }

        int from = bookPages.getPageable().getPageNumber() * 10 + 1;
        long to = (bookPages.toList().size() == 10) ? (bookPages.getPageable().getPageNumber() + 1) * 10L : bookPages.getTotalElements();
        String text = "<b>" + bookPages.getTotalElements() + " natija, ko'rsatilyapti " +
                from + "-" + to + "</b>\n\n";


        List<Book> booksList = bookPages.toList();

        for (int i = 0; i < booksList.size(); i++) {
            Book book = booksList.get(i);

            text += i + 1 + ")" + " <b>" + book.getName() + "</b>--" + book.getAuthor() +" <b><i>"+ book.getType().toUpperCase()+ "</i></b>\n";

        }
        editMessageText.setText(text);

        InlineKeyboardMarkup selectBoard = BoardService.selectBoard(booksList, currentPage, hasNextPage, reqBook);

        editMessageText.setReplyMarkup(selectBoard);

        return editMessageText;
    }


    public SendMessage makeSimpleMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setParseMode(ParseMode.HTML);
        sendMessage.setChatId(chatId + "");
        sendMessage.setText(text);
        return sendMessage;
    }

    public SendMessage makeReplyMessage(Long chatId, String text, Integer replyMessageId) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setReplyToMessageId(replyMessageId);
        sendMessage.setParseMode(ParseMode.HTML);
        sendMessage.setChatId(chatId + "");
        sendMessage.setText(text);
        return sendMessage;
    }

    public CopyMessage copy(Integer postId, String channelUsername, Long chatId, String description) {
        CopyMessage copyMessage = new CopyMessage();
        copyMessage.setMessageId(postId);
        copyMessage.setChatId(chatId + "");
        copyMessage.setCaption(description);
        copyMessage.setParseMode(ParseMode.HTML);
        copyMessage.setFromChatId(channelUsername);
        return copyMessage;
    }

    public DeleteMessage makeDelete(Long chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(messageId);
        deleteMessage.setChatId(chatId + "");
        return deleteMessage;
    }
}
