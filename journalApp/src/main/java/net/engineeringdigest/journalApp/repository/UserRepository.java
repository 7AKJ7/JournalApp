package net.engineeringdigest.journalApp.repository;


import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.Authentication;

public interface UserRepository extends MongoRepository<User,ObjectId>{
    User findByUserName(String userName);

    void deleteByUserName(Authentication authentication);
}
