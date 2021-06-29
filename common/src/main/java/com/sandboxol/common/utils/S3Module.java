package com.sandboxol.common.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.module.GlideModule;
import com.sandboxol.common.entity.S3Image;

import java.io.InputStream;
//@GlideModule
public class S3Module extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder glideBuilder) {

    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        registry.append(S3Image.class, InputStream.class, new S3ImageImageLoader.Factory());
    }

}
