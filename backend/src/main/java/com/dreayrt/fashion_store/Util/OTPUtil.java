package com.dreayrt.fashion_store.Util;

import java.security.SecureRandom;

public class OTPUtil {
    private static final SecureRandom random = new SecureRandom();
    public static String generateOtp() {
        return String.valueOf(100000 + random.nextInt(900000)); // 6 số
    }
}
