package com.gzzhsl.pcms.util;

import com.gzzhsl.pcms.model.UserInfo;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileUtil {
    public static String saveFile(UserInfo thisUser, List<MultipartFile> files) {
        String midPath = thisUser.getUserId()+ "/" + UUIDUtils.getUUIDString()+"/";
        for (MultipartFile file : files) {
            String oriFileName = file.getOriginalFilename();
            String suffixName = ImageUtil.getFileExtension(oriFileName);
            String destFileName = ImageUtil.getRandomFileName() + suffixName;
            File dest = new File(PathUtil.getFileBasePath(true) + midPath + destFileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return midPath;
    }
}
