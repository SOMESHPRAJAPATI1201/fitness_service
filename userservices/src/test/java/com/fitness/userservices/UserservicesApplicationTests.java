package com.fitness.userservices;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.fitness.userservices.UserservicesApplication.main;

@SpringBootTest
class UserservicesApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void mainMethodRuns() {
        UserservicesApplication.main(new String[]{});
    }

}
