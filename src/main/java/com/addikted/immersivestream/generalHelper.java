package com.addikted.immersivestream;

import org.bukkit.OfflinePlayer;

import java.util.Random;

import static org.bukkit.Bukkit.getWhitelistedPlayers;

public class generalHelper {
    static Random random;

    public static boolean isWhitelisted(OfflinePlayer player) {
        return getWhitelistedPlayers().contains(player);
    }

    static String generateOTP() {
        return generateOTP(6);
    }
    static String generateOTP(int len) {
        if (random == null) random = new Random();

        StringBuilder otp_builder = new StringBuilder(random.ints(len, 0, 10).mapToObj(Integer::toString)
                .reduce((a, b) -> a + b).get());

        int halfway = Math.floorDiv(len, 2);
        otp_builder.insert(halfway, " "); // inset a space in the middle of the OTP
        return otp_builder.toString();
    }
}
