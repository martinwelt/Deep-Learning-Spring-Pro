package com.xrervip.super_ai_service.common;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;

/**
 * 模型URL的工具类
 */
@UtilityClass
@Slf4j
public class ModelUrlUtils {
    /**
     * 获取模型url，如果是http或file开头，直接返回
     *
     * @param name 模型名称
     * @return url
     */
    @SneakyThrows
    public static String getRealUrl(String name) {
        if (name.startsWith("http") || name.startsWith("file:")) {
            log.debug("model url is {}", name);
            return name;
        }

        URI uri = new ClassPathResource("classpath:").getURI();
        if (uri.toString().startsWith("jar:")) {
            return "jar://" + name;
        }

        return uri.toString();
    }

    @SneakyThrows
    public static String saveTmpModel(String modelName,byte[] modelBlob) {
        File file  = File.createTempFile(modelName,".zip");
        file.deleteOnExit();
        log.info("Model :{} is saved to filePath {} ",modelName,file.getAbsolutePath());

        String path = "file:"+file.getPath();
        if(System.getProperty("os.name").toLowerCase().contains("win")){
            path = path.replace("\\","//");
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(modelBlob,0,modelBlob.length);
        fos.flush();
        fos.close();
        return path;
    }
}
