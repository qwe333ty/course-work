package com.ak.work.client.producer;

import com.ak.work.client.entity.*;
import com.ak.work.client.menu.impl.AdminMenu;
import com.ak.work.client.menu.impl.ExpertMenu;
import com.ak.work.client.menu.impl.ManagerMenu;
import com.ak.work.client.util.URIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.sql.Date;
import java.time.Instant;
import java.util.Collections;
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

    public static final String EXPERT_ROLE = "expert";

    public static final String MANAGER_ROLE = "manager";

    public Boolean checkIfAuthenticated(String login, String password, Scanner scanner) {
        Credentials credentials = new Credentials(login, password);
        URI uri = getUriWithPaths(null, userPath, "/authentication");

        User user = getOneObject(uri, HttpMethod.POST, credentials, User.class);
        if (user == null) {
            return Boolean.FALSE;
        }

        addVisit(user.getId());

        if (user.getIsBlocked()) {
            System.out.println("Ваш аккаунт был заблокирован.");
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

    public User regist(String username, String email, String password, String role, String first, int second) {
        User user;
        if (role.equals(EXPERT_ROLE)) {
            user = new Expert();
        }
        else {
            user = new Manager();
        }
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRegistrationDate(Date.from(Instant.now()));

        if (role.equals(EXPERT_ROLE)) {
            Expert expert = (Expert) user;
            expert.setExperienceAsExpert(second);
            expert.setPrevProjects(Integer.valueOf(first));
        }
        else {
            Manager manager = (Manager) user;
            manager.setCompany(first);
            manager.setExperienceAsManager(second);
        }

        URI uri = getUriWithPaths(null, userPath, "/registration");

        return getOneObject(uri, HttpMethod.POST, user, User.class);
    }

    public List<VisitHistory> getUserVisitHistory(Integer userId) {
        URI uri = getUriWithPaths(new Object[]{userId}, userPath, "{id}", "visitHistory");
        return getObjectList(uri, HttpMethod.GET, null, VisitHistory.class);
    }

    public List<User> getAllUsers() {
        URI uri = getClearUri(userPath);
        return getObjectList(uri, HttpMethod.GET, null, User.class);
    }

    private void addVisit(Integer userId) {
        URI uri = getUriWithPaths(new Object[]{userId}, userPath, "{id}", "addVisitHistory");
        getOneObject(uri, HttpMethod.GET, null, VisitHistory.class);
    }

    protected void changeUserStatus(Integer userId, Boolean status) {
        URI uri = getUriWithParamsAndPathVariables(
                new Object[]{userId},
                Collections.singletonList(
                        new URIUtils.QueryParam("isBlocked", new Object[]{status})),
                userPath, "{id}", "status");
        getOneObject(uri, HttpMethod.GET, null, Boolean.class);
    }
}
