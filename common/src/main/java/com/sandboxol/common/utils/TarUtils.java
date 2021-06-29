package com.sandboxol.common.utils;

import android.util.Log;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/12/28 0028.
 */

public class TarUtils {

    private static final String TAG = TarUtils.class.getSimpleName();

    public static void unCompressArchiveGz(String archive, String toPath) throws Exception {
        File file = new File(archive);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        String finalName = file.getParent() + File.separator + fileName;
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(finalName));
        GzipCompressorInputStream gcis = new GzipCompressorInputStream(bis);
        byte[] buffer = new byte[1024];
        int read = -1;
        while ((read = gcis.read(buffer)) != -1) {
            bos.write(buffer, 0, read);
        }
        gcis.close();
        bos.close();
        unCompressTar(finalName, toPath);
    }

    private static void unCompressTar(String finalName, String toPath) throws Exception {
        File file = new File(finalName);
        try {
            File toFile = new File(toPath);
            if (!toFile.isDirectory()) {
                toFile.mkdirs();
            }
            TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(file));
            TarArchiveEntry tarArchiveEntry = null;
            while ((tarArchiveEntry = tais.getNextTarEntry()) != null) {
                String name = tarArchiveEntry.getName();
                File tarFile = new File(toPath, name);
                Log.e(TAG, tarFile.getPath());
                if (tarArchiveEntry.isDirectory()) {
                    if (!tarFile.isDirectory()) {
                        tarFile.mkdirs();
                    }
                } else {
                    if (!tarFile.getParentFile().exists()) {
                        tarFile.getParentFile().mkdirs();
                    }
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tarFile));
                    int read = -1;
                    byte[] buffer = new byte[1024];
                    while ((read = tais.read(buffer)) != -1) {
                        bos.write(buffer, 0, read);
                    }
                    bos.close();
                }
            }
            tais.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (file.isFile()) {
                file.delete();
            }
        }
    }
}
