package com.bob.common.binding.adapter;

import android.content.res.AssetManager;
import androidx.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.Util;
import com.bob.common.base.app.BaseApplication;
import com.bob.common.command.ReplyCommand;
import com.bob.common.utils.RoundedCornersTransform;
import com.bob.common.utils.SizeUtil;

import java.io.InputStream;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class ImageViewBindingAdapters {

    @BindingAdapter(value = {"uri", "placeImageRes", "failedImageRes", "leftTop", "rightTop", "leftBottom", "rightBottom", "ivRadius", "onSuccessCommand"}, requireAll = false)
    public static void loadImage(final ImageView iv, String uri,
                                 int placeImageRes,
                                 int failedImageRes,
                                 boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom, float ivRadius,
                                 final ReplyCommand<Drawable> onSuccessCommand) {
        if (placeImageRes != 0) {
            iv.setImageResource(placeImageRes);
        }
        if (!TextUtils.isEmpty(uri) && iv.getContext() != null) {
            if (failedImageRes == 0 && placeImageRes != 0) {
                failedImageRes = placeImageRes;
            }
            int finalFailedImageRes = failedImageRes;
            if (Util.isOnMainThread()) {
                if (ivRadius > 0) {
                    RoundedCornersTransform roundedCornersTransform = new RoundedCornersTransform(iv.getContext(), SizeUtil.dp2px(iv.getContext(), ivRadius));
                    roundedCornersTransform.setNeedCorner(leftTop, rightTop, leftBottom, rightBottom);
                    RequestOptions transform = new RequestOptions().transform(roundedCornersTransform);
                    try {
                        Glide.with(iv.getContext()).load(uri).apply(transform).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                if (finalFailedImageRes != 0) {
                                    iv.setImageResource(finalFailedImageRes);
                                }
                                return true;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                if (onSuccessCommand != null) {
                                    onSuccessCommand.execute(resource);
                                }
                                return false;
                            }
                        }).into(iv);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Glide.with(iv.getContext()).load(uri).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                if (finalFailedImageRes != 0) {
                                    iv.setImageResource(finalFailedImageRes);
                                }
                                return true;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                if (onSuccessCommand != null) {
                                    onSuccessCommand.execute(resource);
                                }
                                return false;
                            }
                        }).into(iv);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    @BindingAdapter({"enable"})
    public static void isEnable(ImageView iv, boolean enable) {
        iv.setEnabled(enable);
    }

    @BindingAdapter(value = {"isRunning", "animType"}, requireAll = false)
    public static void imageAnim(final ImageView iv, boolean isRunning, String animType) {
        if (animType.equals("round") && iv.getContext() != null) {
            RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setRepeatCount(-1);
            animation.setDuration(1000);
            iv.setAnimation(animation);
            if (isRunning) {
                animation.start();
            } else {
                animation.cancel();
            }
        }
    }

    @BindingAdapter(value = {"gif", "gifUri", "gifPlaceImageRes", "gifFailedImageRes"}, requireAll = false)
    public static void setGif(final ImageView imageView, int gif, String gifUri, int gifPlaceImageRes, int gifFailedImageRes) {
        if (imageView.getContext() != null) {
            if (gif != 0) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                try {
                    Glide.with(BaseApplication.getContext()).asGif().apply(requestOptions).load(gif).into(imageView);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

            }
            if (!TextUtils.isEmpty(gifUri)) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .error(gifFailedImageRes)
                        .placeholder(gifPlaceImageRes);
                try {
                    Glide.with(BaseApplication.getContext()).asGif().load(gifUri).apply(requestOptions).into(imageView);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @BindingAdapter({"isStartAnim"})
    public static void isStartAnim(ImageView imageView, boolean isStartAnim) {
        if (isStartAnim) {
            AnimationDrawable loadingAnim = (AnimationDrawable) imageView.getBackground();
            if (!loadingAnim.isRunning()) {
                loadingAnim.start();
            }
        }
    }

    @BindingAdapter({"isStartRoundAnim"})
    public static void isStartRoundAnim(ImageView imageView, boolean isStartRoundAnim) {
        if (isStartRoundAnim) {
            RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(5000);
            rotateAnimation.setRepeatCount(-1);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            imageView.startAnimation(rotateAnimation);
        }
    }

    @BindingAdapter({"isStartAnimByXml"})
    public static void isStartAnimByXml(ImageView imageView, int anim) {
        if (imageView.getContext() == null) {
            return;
        }
        if (anim == 1) {
            imageView.clearAnimation();
            return;
        }
        if (anim != 0) {
            Animation animation = AnimationUtils.loadAnimation(imageView.getContext(), anim);
            imageView.startAnimation(animation);
        }
    }

    @BindingAdapter({"assetsImage"})
    public static void assetsImage(ImageView imageView, String assetsImage) {
        if (imageView.getContext() == null) {
            return;
        }
        AssetManager assetManager = imageView.getContext().getAssets();
        try {
            InputStream in = assetManager.open(assetsImage);
            Bitmap bmp = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bmp);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @BindingAdapter({"drawable"})
    public static void setDrawable(ImageView imageView, Drawable drawable) {
        if (drawable != null) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageDrawable(drawable);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }
}
