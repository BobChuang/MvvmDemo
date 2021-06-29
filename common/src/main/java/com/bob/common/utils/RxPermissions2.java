package com.bob.common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.tbruyelle.rxpermissions2.Permission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;

public class RxPermissions2 {

    static final String TAG = RxPermissions2.class.getSimpleName();
    static final Object TRIGGER = new Object();
    @VisibleForTesting
    RxPermissions2.Lazy<RxPermissionsFragment2> mRxPermissionsFragment;

    public RxPermissions2(@NonNull FragmentActivity activity) {
        this.mRxPermissionsFragment = this.getLazySingleton(activity.getSupportFragmentManager());
    }

    public RxPermissions2(@NonNull Fragment fragment) {
        this.mRxPermissionsFragment = this.getLazySingleton(fragment.getChildFragmentManager());
    }

    @NonNull
    private RxPermissions2.Lazy<RxPermissionsFragment2> getLazySingleton(@NonNull final FragmentManager fragmentManager) {
        return new RxPermissions2.Lazy<RxPermissionsFragment2>() {
            private RxPermissionsFragment2 rxPermissionsFragment;

            public synchronized RxPermissionsFragment2 get() {
                if (this.rxPermissionsFragment == null) {
                    this.rxPermissionsFragment = RxPermissions2.this.getRxPermissionsFragment(fragmentManager);
                }

                return this.rxPermissionsFragment;
            }
        };
    }

    private RxPermissionsFragment2 getRxPermissionsFragment(@NonNull FragmentManager fragmentManager) {
        RxPermissionsFragment2 rxPermissionsFragment = this.findRxPermissionsFragment(fragmentManager);
        boolean isNewInstance = rxPermissionsFragment == null;
        if (isNewInstance) {
            rxPermissionsFragment = new RxPermissionsFragment2();
            fragmentManager.beginTransaction().add(rxPermissionsFragment, TAG).commitNowAllowingStateLoss();
        }

        return rxPermissionsFragment;
    }

    private RxPermissionsFragment2 findRxPermissionsFragment(@NonNull FragmentManager fragmentManager) {
        return (RxPermissionsFragment2)fragmentManager.findFragmentByTag(TAG);
    }

    public void setLogging(boolean logging) {
        ((RxPermissionsFragment2)this.mRxPermissionsFragment.get()).setLogging(logging);
    }

    public <T> ObservableTransformer<T, Boolean> ensure(final String... permissions) {
        return new ObservableTransformer<T, Boolean>() {
            public ObservableSource<Boolean> apply(Observable<T> o) {
                return RxPermissions2.this.request(o, permissions).buffer(permissions.length).flatMap(new Function<List<Permission>, ObservableSource<Boolean>>() {
                    public ObservableSource<Boolean> apply(List<Permission> permissionsx) {
                        if (permissionsx.isEmpty()) {
                            return Observable.empty();
                        } else {
                            Iterator var2 = permissionsx.iterator();

                            Permission p;
                            do {
                                if (!var2.hasNext()) {
                                    return Observable.just(true);
                                }

                                p = (Permission)var2.next();
                            } while(p.granted);

                            return Observable.just(false);
                        }
                    }
                });
            }
        };
    }

    public <T> ObservableTransformer<T, Permission> ensureEach(final String... permissions) {
        return new ObservableTransformer<T, Permission>() {
            public ObservableSource<Permission> apply(Observable<T> o) {
                return RxPermissions2.this.request(o, permissions);
            }
        };
    }

    public <T> ObservableTransformer<T, Permission> ensureEachCombined(final String... permissions) {
        return new ObservableTransformer<T, Permission>() {
            public ObservableSource<Permission> apply(Observable<T> o) {
                return RxPermissions2.this.request(o, permissions).buffer(permissions.length).flatMap(new Function<List<Permission>, ObservableSource<Permission>>() {
                    public ObservableSource<Permission> apply(List<Permission> permissionsx) {
                        return permissionsx.isEmpty() ? Observable.empty() : Observable.just(new Permission(permissionsx));
                    }
                });
            }
        };
    }

    public Observable<Boolean> request(String... permissions) {
        return Observable.just(TRIGGER).compose(this.ensure(permissions));
    }

    public Observable<Permission> requestEach(String... permissions) {
        return Observable.just(TRIGGER).compose(this.ensureEach(permissions));
    }

    public Observable<Permission> requestEachCombined(String... permissions) {
        return Observable.just(TRIGGER).compose(this.ensureEachCombined(permissions));
    }

    private Observable<Permission> request(Observable<?> trigger, final String... permissions) {
        if (permissions != null && permissions.length != 0) {
            return this.oneOf(trigger, this.pending(permissions)).flatMap(new Function<Object, Observable<Permission>>() {
                public Observable<Permission> apply(Object o) {
                    return RxPermissions2.this.requestImplementation(permissions);
                }
            });
        } else {
            throw new IllegalArgumentException("RxPermissions.request/requestEach requires at least one input permission");
        }
    }

    private Observable<?> pending(String... permissions) {
        String[] var2 = permissions;
        int var3 = permissions.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String p = var2[var4];
            if (!((RxPermissionsFragment2)this.mRxPermissionsFragment.get()).containsByPermission(p)) {
                return Observable.empty();
            }
        }

        return Observable.just(TRIGGER);
    }

    private Observable<?> oneOf(Observable<?> trigger, Observable<?> pending) {
        return trigger == null ? Observable.just(TRIGGER) : Observable.merge(trigger, pending);
    }

    @TargetApi(23)
    private Observable<Permission> requestImplementation(String... permissions) {
        List<Observable<Permission>> list = new ArrayList(permissions.length);
        List<String> unrequestedPermissions = new ArrayList();
        String[] unrequestedPermissionsArray = permissions;
        int var5 = permissions.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String permission = unrequestedPermissionsArray[var6];
            ((RxPermissionsFragment2)this.mRxPermissionsFragment.get()).log("Requesting permission " + permission);
            if (this.isGranted(permission)) {
                list.add(Observable.just(new Permission(permission, true, false)));
            } else if (this.isRevoked(permission)) {
                list.add(Observable.just(new Permission(permission, false, false)));
            } else {
                PublishSubject<Permission> subject = ((RxPermissionsFragment2)this.mRxPermissionsFragment.get()).getSubjectByPermission(permission);
                if (subject == null) {
                    unrequestedPermissions.add(permission);
                    subject = PublishSubject.create();
                    ((RxPermissionsFragment2)this.mRxPermissionsFragment.get()).setSubjectForPermission(permission, subject);
                }

                list.add(subject);
            }
        }

        if (!unrequestedPermissions.isEmpty()) {
            unrequestedPermissionsArray = (String[])unrequestedPermissions.toArray(new String[unrequestedPermissions.size()]);
            this.requestPermissionsFromFragment(unrequestedPermissionsArray);
        }

        return Observable.concat(Observable.fromIterable(list));
    }

    public Observable<Boolean> shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        return !this.isMarshmallow() ? Observable.just(false) : Observable.just(this.shouldShowRequestPermissionRationaleImplementation(activity, permissions));
    }

    @TargetApi(23)
    private boolean shouldShowRequestPermissionRationaleImplementation(Activity activity, String... permissions) {
        String[] var3 = permissions;
        int var4 = permissions.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String p = var3[var5];
            if (!this.isGranted(p) && !activity.shouldShowRequestPermissionRationale(p)) {
                return false;
            }
        }

        return true;
    }

    @TargetApi(23)
    void requestPermissionsFromFragment(String[] permissions) {
        ((RxPermissionsFragment2)this.mRxPermissionsFragment.get()).log("requestPermissionsFromFragment " + TextUtils.join(", ", permissions));
        ((RxPermissionsFragment2)this.mRxPermissionsFragment.get()).requestPermissions(permissions);
    }

    public boolean isGranted(String permission) {
        return !this.isMarshmallow() || ((RxPermissionsFragment2)this.mRxPermissionsFragment.get()).isGranted(permission);
    }

    public boolean isRevoked(String permission) {
        return this.isMarshmallow() && ((RxPermissionsFragment2)this.mRxPermissionsFragment.get()).isRevoked(permission);
    }

    boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= 23;
    }

    void onRequestPermissionsResult(String[] permissions, int[] grantResults) {
        ((RxPermissionsFragment2)this.mRxPermissionsFragment.get()).onRequestPermissionsResult(permissions, grantResults, new boolean[permissions.length]);
    }

    @FunctionalInterface
    public interface Lazy<V> {
        V get();
    }

}
