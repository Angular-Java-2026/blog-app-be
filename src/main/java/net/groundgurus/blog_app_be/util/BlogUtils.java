package net.groundgurus.blog_app_be.util;

import org.apache.commons.lang3.StringUtils;

public class BlogUtils {
    public static String createBlogUrl(String title) {
        if (StringUtils.isBlank(title)) {
            return StringUtils.EMPTY;
        }

        return title
                .toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "")   // remove special characters
                .replaceAll("\\s+", "-")           // spaces -> dashes
                .replaceAll("-+", "-");            // collapse multiple dashes
    }
}
