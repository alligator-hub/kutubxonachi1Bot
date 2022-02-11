package kutubxonachi11bot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomUpdate {

    private boolean valid=false;

    private Long chatId;

    private Integer messageId;

    private String username;

    private String firstName;

    private String lastName;

    private String reqText=null;

    private String queryData=null;

    private boolean callBackQuery=false;

    private boolean textMessage=false;
}
