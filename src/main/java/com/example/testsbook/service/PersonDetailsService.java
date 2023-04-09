package com.example.testsbook.service;

import com.example.testsbook.models.Person;
import com.example.testsbook.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleService peopleService;
    @Autowired
    public PersonDetailsService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Person person = peopleService.findByName(name);

        if (person == null || person.isRemoved() == true)
            throw new UsernameNotFoundException("User not found!");

        return new PersonDetails(person);
    }
}
