package com.mafei.laboratory.system.controller;

import com.mafei.laboratory.commons.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wutangsheng
 * @create 2021-02-18 16:03
 * @info
 */
@SpringBootTest
class AuthControllerTest {

    @Test
    void login() {
        Map<String, Object> map = JwtUtils.parseToken("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxMGEzZjA2Mi1iOTliLTQyNjgtYmYyMS0yM2IwZGVjMTk1NmMiLCJpYXQiOjE2MTM2MzU1NzcsImV4cCI6MTYxMzY0OTk3Nywicm9sZU5hbWUiOiLotoXnuqfnrqHnkIblkZgiLCJ1c2VyIjoi6Iul5L6dIiwiYXZhdGFyIjoiaHR0cHM6Ly9jdWJlLmVsZW1lY2RuLmNvbS8wLzg4LzAzYjBkMzk1ODNmNDgyMDY3NjhhNzUzNGU1NWJjcG5nLnBuZyIsInN1YiI6IuiLpeS-nSJ9.KA04Wq4h2q0RKm7-jNc9YSPDZGG726tZ2JgrscIEd_RI0sg4hLc_UzYyxqMtN0LXpADWshQkXe_DELdNa6xmKw");
        System.out.println(map);
    }
}