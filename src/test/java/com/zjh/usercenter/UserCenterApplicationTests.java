package com.zjh.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class UserCenterApplicationTests {

    @Test
    void contextLoads() {
        String md5DigestAsHex = DigestUtils.md5DigestAsHex("password".getBytes());
        System.out.println(md5DigestAsHex);
    }

}
