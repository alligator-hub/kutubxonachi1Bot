package kutubxonachi11bot;

import kutubxonachi11bot.service.FollowerService;
import kutubxonachi11bot.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import kutubxonachi11bot.model.CustomUpdate;

@Component
public class Kutubxonachi1Bot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Autowired
    @Lazy
    MainService mainService;

    @Autowired
    FollowerService followerService;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
    

    @Override
    public void onUpdateReceived(Update telegramUpdate) {


        CustomUpdate update = getCustomUpdate(telegramUpdate);

        if (update.isValid()) {
            followerService.receiveAction(update);

            if (update.isTextMessage()) {
                mainService.textMessage(update);
            } else if (update.isCallBackQuery()) {
                mainService.inlineMessage(update);
            }
        }

    }

    private CustomUpdate getCustomUpdate(Update telegramUpdate) {
        CustomUpdate update = new CustomUpdate();

        if (telegramUpdate.hasMessage()) {
            if (telegramUpdate.getMessage().hasText()) {
                update.setValid(true);
                update.setTextMessage(true);
                update.setChatId(telegramUpdate.getMessage().getChatId());
                update.setFirstName(telegramUpdate.getMessage().getFrom().getFirstName());
                update.setLastName(telegramUpdate.getMessage().getFrom().getLastName());
                update.setUsername(telegramUpdate.getMessage().getFrom().getUserName());
                update.setReqText(telegramUpdate.getMessage().getText());
                update.setMessageId(telegramUpdate.getMessage().getMessageId());
            }
        } else if (telegramUpdate.hasCallbackQuery()) {
            update.setValid(true);
            update.setCallBackQuery(true);
            update.setChatId(telegramUpdate.getCallbackQuery().getMessage().getChatId());
            update.setFirstName(telegramUpdate.getCallbackQuery().getFrom().getFirstName());
            update.setLastName(telegramUpdate.getCallbackQuery().getFrom().getLastName());
            update.setUsername(telegramUpdate.getCallbackQuery().getFrom().getUserName());
            update.setQueryData(telegramUpdate.getCallbackQuery().getData());
            update.setMessageId(telegramUpdate.getCallbackQuery().getMessage().getMessageId());
        }
        return update;
    }
}
