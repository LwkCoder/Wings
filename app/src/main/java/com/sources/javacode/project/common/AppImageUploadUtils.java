package com.sources.javacode.project.common;

import com.lwkandroid.lib.core.net.RxHttp;
import com.lwkandroid.lib.core.rx.scheduler.RxSchedulers;
import com.lwkandroid.lib.core.utils.common.PathUtils;
import com.lwkandroid.lib.core.utils.compress.ImageCompressor;
import com.sources.javacode.net.ApiURL;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;

/**
 * @description: 通用上传图片的工具类，需要补全上传单张图片的地址
 * @author:
 * @date: 2021/4/2 14:13
 */
public class AppImageUploadUtils
{
    private static final int SINGLE_IMAGE_WRITE_TIMEOUT = 30000;
    private static final long SINGLE_IMAGE_MAX_SIZE = 2048 * 1024;

    /**
     * 压缩单张图片
     *
     * @param localImagePath
     * @return
     */
    public static Observable<String> compress(String localImagePath)
    {
        List<String> fileList = new LinkedList<>();
        fileList.add(localImagePath);
        return compress(fileList)
                .map(strings -> strings.get(0));
    }

    /**
     * 压缩多张图片
     *
     * @param localImagePaths
     * @return
     */
    public static Observable<List<String>> compress(List<String> localImagePaths)
    {
        List<File> fileList = new LinkedList<>();
        for (String path : localImagePaths)
        {
            fileList.add(new File(path));
        }
        return ImageCompressor.advanceCompress(fileList)
                .setCacheFolderPath(PathUtils.getExAppDcimPath())
                .setMaxBytesSize(SINGLE_IMAGE_MAX_SIZE)
                .create()
                .compressByRxJava()
                .map(files -> {
                    List<String> resultList = new LinkedList<>();
                    for (File file : files)
                    {
                        resultList.add(file.getAbsolutePath());
                    }
                    return resultList;
                })
                .compose(RxSchedulers.applyNewThread2Main());
    }

    /**
     * 上传单张图片
     *
     * @param localImagePath
     * @return
     */
    public static Observable<String> upload(String localImagePath)
    {
        return RxHttp.UPLOAD(ApiURL.UPLOAD_IMAGE)
                .addFile("file", new File(localImagePath))
                .setWriteTimeOut(SINGLE_IMAGE_WRITE_TIMEOUT)
                .parseRestfulObject(String.class)
                .compose(RxSchedulers.applyIo2Main());
    }

    /**
     * 上传多张图片
     *
     * @param localImagePaths
     * @return
     */
    public static Observable<List<String>> upload(List<String> localImagePaths)
    {
        return Observable.fromIterable(localImagePaths)
                .flatMap((Function<String, ObservableSource<String>>) AppImageUploadUtils::upload)
                .collect((Supplier<List<String>>) LinkedList::new, List::add)
                .toObservable();
    }

    /**
     * 压缩后上传单张图片
     *
     * @param localImagePath
     * @return
     */
    public static Observable<String> uploadAfterCompress(String localImagePath)
    {
        return compress(localImagePath)
                .flatMap((Function<String, ObservableSource<String>>) AppImageUploadUtils::upload);
    }

    /**
     * 压缩后上传多张图片
     *
     * @param localImagePaths
     * @return
     */
    public static Observable<List<String>> uploadAfterCompress(List<String> localImagePaths)
    {
        return compress(localImagePaths)
                .flatMap((Function<List<String>, ObservableSource<List<String>>>) AppImageUploadUtils::upload);
    }
}
