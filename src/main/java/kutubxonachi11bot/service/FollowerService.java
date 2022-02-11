package kutubxonachi11bot.service;

import kutubxonachi11bot.repository.FollowerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kutubxonachi11bot.entity.Follower;
import kutubxonachi11bot.model.CustomUpdate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class FollowerService {

    @Autowired
    FollowerRepo followerRepo;

    public void receiveAction(CustomUpdate update) {

        Optional<Follower> followerOptional = followerRepo.findByChatId(update.getChatId());

        if (followerOptional.isPresent()) {
            Follower follower = followerOptional.get();
            follower.setFirstName(update.getFirstName());
            follower.setLastName(update.getLastName());
            follower.setUsername(update.getUsername());
            follower.setLastConnectionDate((LocalDateTime.now().toInstant(ZoneOffset.UTC)).getEpochSecond());
            followerRepo.save(follower);
        } else {
            addNewFollower(update);
        }
    }

    public Follower addNewFollower(CustomUpdate update) {

        Long startedDate = (LocalDateTime.now().toInstant(ZoneOffset.UTC)).getEpochSecond();

        Follower follower = new Follower();
        follower.setFirstName(update.getFirstName());
        follower.setLastName(update.getLastName());
        follower.setUsername(update.getUsername());
        follower.setChatId(update.getChatId());
        follower.setStartedDate(startedDate);
        follower.setLastConnectionDate(startedDate);
        return followerRepo.save(follower);
    }
}
