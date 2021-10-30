package com.mainproject.useraccount.services;

import com.mainproject.useraccount.entity.EmailData;
import com.mainproject.useraccount.repository.EmailDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmailDataService {

    @Autowired
    private EmailDataRepository emailDataRepository;


    public List<EmailData> getAll() {
        return this.emailDataRepository.findAll();
    }

    public Optional<EmailData> getOne(int emailDataId) {
        return this.emailDataRepository.findById(emailDataId);
    }
}
