package com.ak.work.server;

import com.ak.work.server.entity.Admin;
import com.ak.work.server.entity.Expert;
import com.ak.work.server.entity.Manager;
import com.ak.work.server.entity.User;
import com.ak.work.server.service.ProblemService;
import com.ak.work.server.service.SolutionService;
import com.ak.work.server.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    private ProblemService problemService;

    @Autowired
    private SolutionService solutionService;

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
                "Qwerty", null, "asdfghjkl");
        assertThat(user, allOf(
                hasProperty("username", is("Qwerty")),
                hasProperty("email", is("qwerty@gmail.com")),
                hasProperty("password", is("asdfghjkl")
                )));
    }

    private Admin createMockAdmin() {
        Admin admin = new Admin();
        admin.setUsername("Qwerty");
        admin.setEmail("qwerty@gmail.com");
        admin.setPassword("asdfghjkl");
        admin.setRegistrationDate(new Date());
        admin.setAddress("qweqweqwe asd xzcxzcczx");
        admin.setBossEmail("asdasd@zxc.com");

        return admin;
    }

    private Expert createMockExpert() {
        Expert expert = new Expert();
        expert.setUsername("Aasdf");
        expert.setEmail("asd@asd.com");
        expert.setPassword("zxcdDFdvs");
        expert.setExperienceAsExpert(2);
        expert.setRegistrationDate(new Date());
        expert.setPrevProjects(5);

        return expert;
    }

    private Manager createMockManager() {
        Manager manager = new Manager();
        manager.setUsername("Zdczc");
        manager.setEmail("zxc@zxc.com");
        manager.setPassword("POIhvfd");
        manager.setCompany("Qqqqq");
        manager.setExperienceAsManager(2);
        manager.setRegistrationDate(new Date());

        return manager;
    }
}
