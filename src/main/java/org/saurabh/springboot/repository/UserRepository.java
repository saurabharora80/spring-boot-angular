package org.saurabh.springboot.repository;

import org.saurabh.springboot.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String>{
    public List<User> findByFirstName(String firstName);
}
