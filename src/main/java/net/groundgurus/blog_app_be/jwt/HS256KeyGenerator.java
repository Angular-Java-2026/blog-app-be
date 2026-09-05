package net.groundgurus.blog_app_be.jwt;

import java.security.SecureRandom;
import java.util.Base64;

public class HS256KeyGenerator {

  static void main() {
    SecureRandom secureRandom = new SecureRandom();
    byte[] keyBytes = new byte[32];          // 256 bits
    secureRandom.nextBytes(keyBytes);

    // Preferred form for storage / sharing
    String base64Key = Base64.getEncoder().encodeToString(keyBytes);
    IO.println("Base64 key: " + base64Key);

    // Alternative (hex)
//        StringBuilder hex = new StringBuilder();
//        for (byte b : keyBytes) {
//            hex.append(String.format("%02x", b));
//        }
//        IO.println("Hex key:    " + hex);
  }
}
