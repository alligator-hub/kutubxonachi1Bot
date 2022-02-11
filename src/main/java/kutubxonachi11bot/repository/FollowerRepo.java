package kutubxonachi11bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kutubxonachi11bot.entity.Follower;

import java.util.Optional;

@Repository
public interface FollowerRepo extends JpaRepository<Follower,Long> {

    Optional<Follower> findByChatId(Long chatId);
}
