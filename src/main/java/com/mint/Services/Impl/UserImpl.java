package com.mint.Services.Impl;

import com.mint.Services.UserInterface;
import com.mint.model.User;
import com.mint.repository.UserRepository;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.command.CommandFactory;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.KieServiceResponse;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service //This tells that we will be using this as a Service
public class UserImpl implements UserInterface {
    @Autowired //This means below is a dependency
    private UserRepository userRepository;

    ////////////////////////LOCAL///////////////////////////////////////////////////////////
    KieServices kieServices = KieServices.Factory.get();
    KieContainer kContainer = kieServices.getKieClasspathContainer();

    ////////////////////////KIE SERVER///////////////////////////////////////////////////////////
    private static final String URL = "http://localhost:8180/kie-server/services/rest/server";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;

    private static KieServicesConfiguration conf;

    // KIE client for common operations
    private static KieServicesClient kieServicesClient;

    // Rules client
    private static RuleServicesClient ruleClient;

    private static final String CONTAINERID = "RestTest";
    private static final String CLASS_NAME = "User";


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String saveUser(User user){
        userRepository.save(user);
        return "success";
    }

    @Override
    public String getEmail(int userID){

        User user=userRepository.findById(userID).get();

        ////////////////////////LOCAL///////////////////////////////////////////////////////////

        KieSession kieSession = kContainer.newKieSession("rulesSession");
        kieSession.insert(user);
        kieSession.fireAllRules();
        kieSession.dispose();

        ////////////////////////KIE SERVER///////////////////////////////////////////////////////////

//        conf = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD);
//        conf.setMarshallingFormat(FORMAT);
//        kieServicesClient = KieServicesFactory.newKieServicesClient(conf);
//
//        RuleServicesClient rulesClient = kieServicesClient.getServicesClient(RuleServicesClient.class);
//        KieCommands commandsFactory = KieServices.Factory.get().getCommands();
//
//        Command<?> insert = commandsFactory.newInsert(user,CLASS_NAME);
//        Command<?> fireAllRules = commandsFactory.newFireAllRules();
//        Command<?> batchCommand = commandsFactory.newBatchExecution(Arrays.asList(insert, fireAllRules));
//
//        ServiceResponse executeResponse = rulesClient.executeCommands(CONTAINERID,batchCommand);
//
//        if(executeResponse.getType() == KieServiceResponse.ResponseType.SUCCESS) {
//            System.out.println("Commands executed with success! Response: ");
//            System.out.println(executeResponse.getResult());
//        } else {
//            System.out.println("Error executing rules. Message: ");
//            System.out.println(executeResponse.getMsg());
//        }

        return user.getEmail();
    }
}
