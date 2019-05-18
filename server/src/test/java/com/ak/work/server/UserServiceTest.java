package com.ak.work.server;

import com.ak.work.server.entity.Admin;
import com.ak.work.server.entity.Expert;
import com.ak.work.server.entity.Manager;
import com.ak.work.server.entity.User;
import com.ak.work.server.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.StreamSupport;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test1() {
        Iterable<User> users = userService.saveAll(Arrays.asList(
                createMockAdmin(),
                createMockExpert(),
                createMockManager()
        ));
        long count = StreamSupport.stream(users.spliterator(), false).count();
        assertThat(count, is(3L));
    }

    @Test
    public void test2() {
        User user = userService.findUserByUsernameOrEmailAndPassword(
                "Admin", null, "admin");
        assertThat(user, allOf(
                hasProperty("username", is("Admin")),
                hasProperty("email", is("admin@admin.com"))));
    }

    private Admin createMockAdmin() {
        Admin admin = new Admin();
        admin.setUsername("Admin");
        admin.setEmail("admin@admin.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRegistrationDate(new Date());
        admin.setAddress("Belarus, Minsk, Kozlova str., 45-1");
        admin.setBossEmail("bossAdmin@boss.com");

        return admin;
    }

    private Expert createMockExpert() {
        Expert expert = new Expert();
        expert.setUsername("Expert");
        expert.setEmail("expert@expert.com");
        expert.setPassword(passwordEncoder.encode("expert"));
        expert.setExperienceAsExpert(2);
        expert.setRegistrationDate(new Date());
        expert.setPrevProjects(5);

        return expert;
    }

    private Manager createMockManager() {
        Manager manager = new Manager();
        manager.setUsername("Manager");
        manager.setEmail("manager@manager.com");
        manager.setPassword(passwordEncoder.encode("manager"));
        manager.setCompany("Google");
        manager.setExperienceAsManager(2);
        manager.setRegistrationDate(new Date());

        return manager;
    }
}
