package com.sola.contentcenter.controller.util;

import java.util.Random;

public class RandomUtil {

    public static int nextInt(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }
}
