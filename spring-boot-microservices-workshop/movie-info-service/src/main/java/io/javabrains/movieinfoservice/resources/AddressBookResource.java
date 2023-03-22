package io.javabrains.movieinfoservice.resources;


import io.javabrains.movieinfoservice.models.Contact;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/contacts")
public class AddressBookResource {
    private static final ConcurrentMap<String, Contact> CONTACTS = new ConcurrentHashMap<>();

    @GetMapping("/contact/{id}")
    public Contact getContact(@PathVariable String id) {
        return CONTACTS.get(id);
    }

    @GetMapping()
    public List<Contact> getAllContacts() {
        return new ArrayList<>(CONTACTS.values());
    }

    @PostMapping()
    public Contact addContact(@RequestBody Contact contact) {
        CONTACTS.put(contact.getId(), contact);
        return contact;
    }
}
