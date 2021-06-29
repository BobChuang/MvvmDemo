package com.sandboxol.common.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.HttpUrlFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.sandboxol.common.entity.S3Image;

import java.io.InputStream;

public class S3ImageImageLoader implements ModelLoader<S3Image,InputStream> {

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(S3Image s3Image, int i, int i1, Options options) {
        return null;
    }

    @Override
    public boolean handles(S3Image s3Image) {
        return true;
    }

    public static class Factory implements ModelLoaderFactory<S3Image, InputStream> {

        @Override
        public ModelLoader<S3Image, InputStream> build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return new S3ImageImageLoader();
        }

        @Override
        public void teardown() {

        }
    }
}
