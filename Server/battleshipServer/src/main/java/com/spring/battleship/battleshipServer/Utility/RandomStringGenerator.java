package com.spring.battleship.battleshipServer.Utility;

import com.spring.battleship.battleshipServer.Service.HostingGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomStringGenerator {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random random = new Random();

    public int tossACoin(){
        return random.nextInt(2);
    }

    public String generateRandomString(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            char randomChar = ALPHA_NUMERIC_STRING.charAt(index);
            builder.append(randomChar);
        }
            return builder.toString();
    }
}
