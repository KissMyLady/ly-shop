package top.mylady.upload.service;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


@Service
public class UploadService {

    @Autowired
    private FastFileStorageClient storageClient;  //文件上传的最终地址

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    private static final List<String> ALLOWTYPES = Arrays.asList("image/jpeg","image/png");

    public String uploadImage(MultipartFile file) {
        try {
            //校验格式
            String contentType = file.getContentType();

            if(!ALLOWTYPES.contains(contentType)){
                //不合法的文件类型
                System.out.println("upload微服务模块, 上传文件, 不合法的文件类型, 返回null");
                return null;
            }

            //校验内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null){
                System.out.println("upload微服务模块, 上传文件, 文件内容不合法:");
                return null;
            }

            /*//准备目标路径
            File dest = new File("D:/javacode/idea/upload/", file.getOriginalFilename());
            //保存到本地
            file.transferTo(dest);*/

            //上传到FastDFS
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);

            String path = thumbImageConfig.getThumbImagePath(storePath.getPath());
            System.out.println("打印略缩图路径: "+ path);
            System.out.println("打印全路径: "+ storePath.getFullPath());

            //返回路径
            return "http://139.198.178.12:8888/" + storePath.getFullPath();

        } catch (Exception e) {
            return null;
        }
    }

}
