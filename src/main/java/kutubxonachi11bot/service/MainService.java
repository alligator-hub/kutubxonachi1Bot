package kutubxonachi11bot.service;

import kutubxonachi11bot.enums.Statics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import kutubxonachi11bot.common.Utils;
import kutubxonachi11bot.entity.Book;
import kutubxonachi11bot.model.CustomUpdate;
import kutubxonachi11bot.repository.BookRepository;
import kutubxonachi11bot.repository.FollowerRepo;

import java.util.Optional;

@Service
public class MainService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    @Lazy
    SenderService sender;

    @Autowired
    @Lazy
    MakerService maker;

    @Autowired
    private Utils utils;

    @Autowired
    FollowerRepo followerRepo;


    public void textMessage(CustomUpdate update) {

        if (utils.isStart(update.getReqText())) {
            getStart(update);
        } else if (utils.isStatics(update.getReqText())) {
            if (utils.isAdmin(update.getUsername())) {
                getStatics(update);
            } else {
                SendMessage sendMessage = maker.makeSimpleMessage(update.getChatId(), Statics.NOT_PERMISSION.getText());
                sender.send(sendMessage);
            }
        } else {

            if (update.getReqText().length() < 4) {
                SendMessage shortTextMsg = maker.makeReplyMessage(update.getChatId(), Statics.SHORT_LENGTH.getText(), update.getMessageId());
                sender.send(shortTextMsg);
                return;
            }


            update.setReqText("%" + update.getReqText() + "%");
            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("name")));
            Page<Book> bookPage = bookRepository.findBooksToPage(update.getReqText(), pageable);

            Pageable pageable1 = PageRequest.of(1, 10, Sort.by(Sort.Order.asc("name")));
            Page<Book> bookPage1 = bookRepository.findBooksToPage(update.getReqText(), pageable1);

            SendMessage sendMessage = maker.makeSendBooksList(
                    bookPage,
                    update.getChatId(),
                    0,
                    bookPage1.getContent().size() > 0,
                    update.getReqText());

            sender.send(sendMessage);

        }

    }


    public void inlineMessage(CustomUpdate update) {

        if (update.getQueryData().contains("--")) {
            String reqBook = update.getQueryData().substring(0, update.getQueryData().indexOf("--"));
            int pageNextOrPreView = Integer.parseInt(update.getQueryData().substring(update.getQueryData().indexOf("--") + 2));

            Pageable pageable = PageRequest.of(pageNextOrPreView, 10, Sort.by(Sort.Order.asc("name")));
            Page<Book> bookPage = bookRepository.findBooksToPage(reqBook, pageable);

            Pageable pageable1 = PageRequest.of(pageNextOrPreView + 1, 10, Sort.by(Sort.Order.asc("name")));
            Page<Book> bookPage1 = bookRepository.findBooksToPage(reqBook, pageable1);

            EditMessageText editMessageText = maker.makeEditBooksList(bookPage,
                    update.getChatId(),
                    update.getMessageId(),
                    pageNextOrPreView,
                    bookPage1.getContent().size() > 0,
                    reqBook);
            sender.edit(editMessageText);
        } else if (Utils.isBookId(update.getQueryData())) {
            long bookId = Long.parseLong(update.getQueryData());
            Optional<Book> bookOptional = bookRepository.findById(bookId);

            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();

                CopyMessage copyMessage = maker.copy(book.getPostId(), book.getChannelUsername(), update.getChatId(), Statics.COPY_MSG_CAPTION.getText());

                sender.send(copyMessage);
            } else {
                SendMessage sendMessage = maker.makeSimpleMessage(update.getChatId(), Statics.BOOK_NOT_FOUND.getText());
                sender.send(sendMessage);

            }

        } else if (update.getQueryData().equals("delete")) {
            DeleteMessage deleteMessage = maker.makeDelete(update.getChatId(), update.getMessageId());
            sender.delete(deleteMessage);
        }

    }


    private void getStart(CustomUpdate update) {

        SendMessage sendMessage = maker.makeSimpleMessage(update.getChatId(), Statics.START_TXT.getText() + "\n" + Statics.PLACEHOLDER_SEARCH.getText());
        sender.send(sendMessage);
    }

    private void getStatics(CustomUpdate update) {
        String staticsTemplate = "<pre>---------------------------------</pre>\n" + "<b>Followers count:</b> " + followerRepo.count() + "\n" + "<pre>---------------------------------</pre>";
        SendMessage message = maker.makeSimpleMessage(update.getChatId(), staticsTemplate);
        sender.send(message);
    }
}
