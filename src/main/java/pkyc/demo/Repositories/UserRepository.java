package pkyc.demo.Repositories;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import pkyc.demo.Model.User;
import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {
    @Aggregation(
            pipeline = {
                    "{'$match' : {'creation_date' : { $gte : ?0 } "
                            + "'last_updated':{$lte:?1}}} ",
                    "{'$limit': ?2}"
            })
    List<User> findByDateRange(LocalDateTime creation_date, LocalDateTime last_updated,int value);
}
