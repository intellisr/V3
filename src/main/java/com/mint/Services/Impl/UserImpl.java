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
import org.kie.server.client.*;
import org.kie.server.client.credentials.EnteredCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service //This tells that we will be using this as a Service
public class UserImpl implements UserInterface {

    public User userUpdated;

    @Autowired //This means below is a dependency
    private UserRepository userRepository;

    ////////////////////////LOCAL///////////////////////////////////////////////////////////
    // KieServices kieServices = KieServices.Factory.get();
    // KieContainer kContainer = kieServices.getKieClasspathContainer();

    ////////////////////////KIE SERVER///////////////////////////////////////////////////////////
    private static final String URL = "http://localhost:8180/kie-server/services/rest/server";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;
    private static final String CONTAINERID = "com.mint:model:1.0.0-SNAPSHOT";
    private static final String CLASS_NAME = "User";


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public String saveUser(User user) {
        userRepository.save(user);
        return "success";
    }


    /// Kie Server Integration.
    @Override
    public String getEmail(int userID) {

        User user = userRepository.findById(userID).get();



        ////////////////////////LOCAL///////////////////////////////////////////////////////////

//       KieSession kieSession = kContainer.newKieSession("rulesSession");
//       kieSession.insert(user);
//       kieSession.fireAllRules();
//       kieSession.dispose();





        ////////////////////////KIE SERVER////////////////////////////////////////////////////////

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD, 120000);
        config.setMarshallingFormat(FORMAT);

        RuleServicesClient client = KieServicesFactory.newKieServicesClient(config).getServicesClient(RuleServicesClient.class);
        List<Command<?>> cmds = new ArrayList<Command<?>>();
        KieCommands commands = KieServices.Factory.get().getCommands();
        cmds.add(commands.newInsert(user, CLASS_NAME));
        cmds.add(commands.newFireAllRules());
        BatchExecutionCommand myCommands = CommandFactory.newBatchExecution(cmds);
        ServiceResponse<ExecutionResults> response = client.executeCommandsWithResults(CONTAINERID, myCommands);

        if (response.getType() == KieServiceResponse.ResponseType.SUCCESS) {
            System.out.println("Success");
            userUpdated = (User) response.getResult().getValue(CLASS_NAME);

        } else {
            System.out.println("Error");
            System.out.println(response.getMsg());
        }

        return userUpdated.getEmail();
    }


}
