package com.iwaneez.stuffer.core;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {

    public static void main(String[] args) {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String lol = bc.encode("admin");
        System.out.println(lol);
    }


}
