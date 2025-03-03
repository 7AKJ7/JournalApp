package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository repository;
    @Autowired
    private UserService userService; //dependency injection

    public void saveEntry(JournalEntry entry){

        repository.save(entry);
    }
    @Transactional
    public void saveEntry(JournalEntry entry, String userName){
        try {
            User user = userService.findByUserName(userName);
            entry.setDate(LocalDateTime.now());
            JournalEntry saved = repository.save(entry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entry.", e);
        }

    }

    public List<JournalEntry> getAll(){
        return repository.findAll();
    }
    public JournalEntry getEntryById(ObjectId id){
        return repository.findById(id).get();
    }
    @Transactional
    public boolean deleteEntryById(ObjectId id, String userName){
        boolean removed = false;

        try {
            User user = userService.findByUserName(userName);
             removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                repository.deleteById(id);

        }




    }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting the entry.", e);}
        return removed;

    }




}

//controller---->service--->repository
