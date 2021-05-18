package com.han.commom.jd.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Base64;

/**
 * @author liuwenhe
 * @date 2020/8/22
 */
public class ImageUtil {
    public static String imageBase64(String path,boolean prefix) {
        try {
            String imageType = path.substring(path.lastIndexOf(".")+1);
            ClassPathResource resource = new ClassPathResource(path);
            byte[] fileContent = FileUtils.readFileToByteArray(resource.getFile());
            String base64Str = Base64.getEncoder().encodeToString(fileContent);
            return prefix?"data:image/"+imageType+";base64,"+base64Str:base64Str;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
