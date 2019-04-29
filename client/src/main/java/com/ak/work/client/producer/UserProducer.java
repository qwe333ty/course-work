package com.ak.work.client.producer;

import com.ak.work.client.entity.*;
import com.ak.work.client.menu.impl.AdminMenu;
import com.ak.work.client.menu.impl.ExpertMenu;
import com.ak.work.client.menu.impl.ManagerMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.Scanner;

@Component
public class UserProducer extends Producer {

    @Value("${api.urn.user}")
    private String userPath;

    @Autowired
    private AdminMenu adminMenu;

    @Autowired
    private ManagerMenu managerMenu;

    @Autowired
    private ExpertMenu expertMenu;

    public Boolean checkIfAuthenticated(String login, String password, Scanner scanner) {
        Credentials credentials = new Credentials(login, password);
        URI uri = getUriWithPaths(null, userPath, "/authentication");

        User user = getOneObject(uri, HttpMethod.POST, credentials, User.class);
        if (user == null) {
            return Boolean.FALSE;
        }

        findInheritorAndExecuteMenu(user, scanner);
        return Boolean.TRUE;
    }

    private void findInheritorAndExecuteMenu(User user, Scanner scanner) {
        if (user instanceof Expert) {
            expertMenu.start(user, scanner);
        } else if (user instanceof Manager) {
            managerMenu.start(user, scanner);
        } else if (user instanceof Admin) {
            adminMenu.start(user, scanner);
        }
    }

    public List<Expert> findAllExperts() {
        URI uri = getClearUri(userPath, "/experts");
        return getObjectList(uri, HttpMethod.GET, null, Expert.class);
    }
}
