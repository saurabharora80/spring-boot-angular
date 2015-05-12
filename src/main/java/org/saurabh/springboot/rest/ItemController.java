package org.saurabh.springboot.rest;

import org.saurabh.springboot.domain.Item;
import org.saurabh.springboot.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemRepository repo;

    @RequestMapping(method = RequestMethod.GET)
    public List findItems() {
        return repo.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Item addItem(@RequestBody Item item) {
        return repo.save(item);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Item updateItem(@RequestBody Item updatedItem, @PathVariable String id) {
        updatedItem.setId(id);
        return repo.save(updatedItem);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteItem(@PathVariable String id) {
        repo.delete(id);
    }
}