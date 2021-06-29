package com.sandboxol.common.entity;

public class S3Image {

    private final String imageUrl;

    public S3Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageId() {
        if (imageUrl.contains("?")) {
            return imageUrl.substring(0, imageUrl.lastIndexOf("?"));
        } else {
            return imageUrl;
        }
    }
}
