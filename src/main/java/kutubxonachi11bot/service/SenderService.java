package kutubxonachi11bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import kutubxonachi11bot.Kutubxonachi1Bot;

import java.io.Serializable;

@Service
public class SenderService {

    @Autowired
    @Lazy
    Kutubxonachi1Bot bot;

    public Message send(SendMessage sendMessage) {
        try {
            return bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Serializable edit(EditMessageText editMessageText) {
        try {
            return bot.execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Serializable send(CopyMessage copyMessage) {
        try {
            return bot.execute(copyMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Serializable delete(DeleteMessage deleteMessage) {
        try {
            return bot.execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}
