package org.saurabh.springboot.repository;

import org.saurabh.springboot.domain.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {
}
