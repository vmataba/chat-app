package com.tabaapps.chat.commands;

import com.tabaapps.chat.models.User;
import com.tabaapps.chat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CommandRunner implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
       // this.addUsers();
       // this.addMoreUsers();
    }

    private void addUsers() throws Exception{
        String adminUserName = "victor@gmail.com";
        User admin = this.userRepository.findByEmail(adminUserName).orElse(new User());
        admin.setFirstName("Victor");
        admin.setLastName("Mataba");
        admin.setEmail(adminUserName);
        admin.setPassword("victor");
        this.userRepository.save(admin);

        String userUserName = "frank@gmail.com";
        User frank = this.userRepository.findByEmail(userUserName).orElse(new User());
        frank.setFirstName("Frank");
        frank.setLastName("Natalis");
        frank.setEmail(userUserName);
        frank.setPassword("frank");
        this.userRepository.save(frank);

        String veroUserName = "vero@gmail.com";
        User vero = this.userRepository.findByEmail(veroUserName).orElse(new User());
        vero.setFirstName("Veronica");
        vero.setLastName("Natalis");
        vero.setEmail(veroUserName);
        vero.setPassword("vero");
        this.userRepository.save(vero);

        String estherUserName = "esther@gmail.com";
        User esther = this.userRepository.findByEmail(estherUserName).orElse(new User());
        esther.setFirstName("Esther");
        esther.setLastName("Mbaga");
        esther.setEmail(estherUserName);
        esther.setPassword("esther");
        this.userRepository.save(esther);
    }

    private void addMoreUsers() throws Exception{
        List<User> extraUsers = new ArrayList<>();
        for (int count = 1; count <= 10000; count++){
            System.out.println("Adding user -- "+count+"\n");
            //Optional<User> optionalUser = this.userRepository.findByEmail("user-"+count+"@gmail.com");
            //if (optionalUser.isPresent()){
            //    continue;
            // }
            User user = new User();
            user.setEmail("user-"+count+"@gmail.com");
            user.setFirstName("First Name "+count);
            user.setMiddleName("Middle Name "+count);
            user.setLastName("Last Name "+count);
            user.setPassword("user"+count);
            //this.userRepository.save(user);
            extraUsers.add(user);
        }
        System.out.println("Created Batch Successfully ---  Batch Size: "+extraUsers.size()+"\n");
        this.userRepository.saveAll(extraUsers);
    }
}
