package com.bob.common.utils;

import androidx.fragment.app.Fragment;

/**
 * Created by Jimmy on 2016/8/26 0026.
 */
public class BeanUtils {

    /**
     * 根据配置名获取Fragment
     *
     * @param name fragment键名
     * @return
     */
    public static Fragment getFragment(Class name) {
        Fragment fragment;
        try {
            fragment = (Fragment) name.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            fragment = new Fragment();
        }
        return fragment;
    }
}
