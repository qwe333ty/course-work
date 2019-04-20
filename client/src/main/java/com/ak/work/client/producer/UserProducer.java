package com.ak.work.client.producer;

import com.ak.work.client.entity.*;
import com.ak.work.client.exception.CallToExternalServiceException;
import com.ak.work.client.menu.impl.AdminMenu;
import com.ak.work.client.menu.impl.ExpertMenu;
import com.ak.work.client.menu.impl.ManagerMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.net.URI;

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

    public Boolean checkIfAuthenticated(String login, String password) {
        Credentials credentials = new Credentials(login, password);
        URI uri = getUriWithPaths(userPath, "/authentication");

        try {
            ResponseEntity<User> response = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    new HttpEntity<>(credentials),
                    User.class);

            findInheritor(response.getBody());
        } catch (RestClientException e) {
            throw new CallToExternalServiceException(HttpMethod.POST, uri);
        } catch (NullPointerException e) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private void findInheritor(User user) {
        if (user instanceof Expert) {
            expertMenu.start();
        } else if (user instanceof Manager) {
            managerMenu.start();
        } else if (user instanceof Admin) {
            adminMenu.start();
        }
    }
}
