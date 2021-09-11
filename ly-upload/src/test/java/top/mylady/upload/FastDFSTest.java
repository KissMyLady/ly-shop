package top.mylady.upload;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FastDFSTest {

    @Autowired
    private FastFileStorageClient storageClient;

    private final static String cloudServer = "http://139.198.178.12:8888/";

    /**
     * 文件上传, 获取资源图片路径
     */
    @Test
    public void testUpload() throws Exception{
        //要上传的文件
        File file = new File("H:\\我的文档\\cut_up\\2021-09-10_090737.png");
        StorePath storePath = this.storageClient.uploadFile(
                new FileInputStream(file),
                file.length(), "png", null
        );

        //带分组的路径
        System.out.println("带分组的路径: "+ storePath.getFullPath());
        //group1/M00/00/00/CoIFVmDywuiAJ-73AAB5r6bSAng697.png

        //不带分组的路径
        System.out.println("不带分组的路径: "+ storePath.getPath());
        //M00/00/00/CoIFVmDywuiAJ-73AAB5r6bSAng697.png
    }

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Test
    public void testUploadAndCreteThumb() throws Exception{
        //要上传的文件
        File file = new File("H:\\我的文档\\cut_up\\2021-09-10_090737.png");
        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
                new FileInputStream(file), file.length(), "jpg", null
        );

        //带分组路径
        System.out.println("带分组的路径: "+ cloudServer+ storePath.getFullPath());
        //group1/M00/00/00/CoIFVmDywuiAJ-73AAB5r6bSAng697.png

        //略缩图路径
        String path = thumbImageConfig.getThumbImagePath(storePath.getFullPath());
        System.out.println("打印略缩图路径: "+ cloudServer+ path);

        //带分组的路径: http://139.198.178.12:8888/group1/M00/00/00/CoIFVmE8usyAEEHPAAJlMzkrUcA658.jpg
        //打印略缩图路径: http://139.198.178.12:8888/group1/M00/00/00/CoIFVmE8usyAEEHPAAJlMzkrUcA658_600x600.jpg
    }

}
