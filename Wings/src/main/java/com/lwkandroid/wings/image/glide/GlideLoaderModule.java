package com.lwkandroid.wings.image.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.lwkandroid.wings.image.ImageLoader;
import com.lwkandroid.wings.utils.SDCardUtils;

import java.io.InputStream;

/**
 * Created by LWK
 * TODO 自定义GlideModule
 */

@GlideModule
public final class GlideLoaderModule extends AppGlideModule
{
    //glide系统缓存基础倍率
    private static final float MEMORY_CACHE_COUNT = 1.2f;
    //磁盘缓存容量最大值
    private static final int MAX_DISK_CACHE_SIZE = 314572800;//300M

    @Override
    public void applyOptions(Context context, GlideBuilder builder)
    {
        super.applyOptions(context, builder);
        //修改内存容量和位图缓存池大小
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        int customMemoryCacheSize = (int) (MEMORY_CACHE_COUNT * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (MEMORY_CACHE_COUNT * defaultBitmapPoolSize);
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
        //设置磁盘缓存
        long availableSize = SDCardUtils.getFreeSpace();
        builder.setDiskCache(new DiskLruCacheFactory(ImageLoader.getLoader().getCachePath(),
                availableSize < MAX_DISK_CACHE_SIZE ? (int) availableSize : MAX_DISK_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry)
    {
        super.registerComponents(context, glide, registry);
        registry.replace(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(GlideOkClient.get().getClient()));
    }
}
