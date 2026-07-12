package com.fauzan.backrooms;

import java.net.URI;

public class UrlNormalizer {
    public static String normalize(String rawUrl) {
        if (rawUrl == null || rawUrl.isBlank()) {
            return rawUrl;
        }
        try {
            String cleanedUrl = rawUrl.trim();
            if (cleanedUrl.toLowerCase().startsWith("http://")) {
                cleanedUrl = "https://" + cleanedUrl.substring(7);
            }
            URI uri = new URI(cleanedUrl);
            String scheme = uri.getScheme();
            String host = uri.getHost();
            String path = uri.getPath();
            String query = uri.getQuery();

            if (path.length() > 1 && !path.endsWith("/")) {
                path = path + "/";
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(scheme).append("://").append(host).append(path);
            if (query != null) {
                stringBuilder.append(query);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return rawUrl.trim().toLowerCase();
        }

    }
}
