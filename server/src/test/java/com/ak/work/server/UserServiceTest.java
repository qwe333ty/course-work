package com.ak.work.server;

import com.ak.work.server.entity.*;
import com.ak.work.server.service.ProblemService;
import com.ak.work.server.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
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

    @Test
    public void test3() {
        User userManager = userService.findUserByUsernameOrEmailAndPassword(
                null, "zxc@zxc.com", "POIhvfd");
        User userExpert = userService.findUserByUsernameOrEmailAndPassword(
                null, "asd@asd.com", "zxcdDFdvs");
        if (userManager instanceof Manager && userExpert instanceof Expert) {
            Manager manager = (Manager) userManager;
            Expert expert = (Expert) userExpert;

            Problem problem = new Problem();
            problem.setManager(manager);

            Solution solution = new Solution();
            solution.setRating(9.86);
            solution.setExpert(expert);
            solution.setProblem(problem);

            problem.setSolutions(Collections.singletonList(solution));
            problem = problemService.save(problem);
            assertThat(problem, hasProperty("id", notNullValue()));
        } else {
            throw new AssertionError("Can't cast user to Manager class.");
        }
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
