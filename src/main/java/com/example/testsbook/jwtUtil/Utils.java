package com.example.testsbook.jwtUtil;

import com.example.testsbook.DTO.PersonDTO;
import com.example.testsbook.models.Person;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Utils {
    private final ModelMapper modelMapper;
    @Autowired
    public Utils(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

}
