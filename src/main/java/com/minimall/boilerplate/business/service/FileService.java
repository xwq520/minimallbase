package com.minimall.boilerplate.business.service;


import com.minimall.boilerplate.common.oss.OSSUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Title: 文件上传服务
 * <p>Description: 文件上传服务 </p>
 *
 * @author xiangwq
 */
@Service
public class FileService {

  /**
   * 上传文件oss
   *
   * @param
   * @return
   * @throws Exception
   */
  @Transactional
  public String uploadFilesOss(MultipartFile file) throws Exception {
    //存储到oss服务器上，返回地址
    String filePath = OSSUploadUtil.uploadFileMu(file, file.getContentType());
    //返回给数据库保存的文件路劲，除磁盘外
    return filePath;
  }

  /**
   * 用数据流 上传文件oss
   *
   * @param
   * @return
   * @throws Exception
   */
  @Transactional
  public String uploadFilesOss(byte[] byts) throws Exception {
    //存储到oss服务器上，返回地址
    String filePath = OSSUploadUtil.uploadFileMu(byts);
    //返回给数据库保存的文件路劲，除磁盘外
    return filePath;
  }

  /**
   * 上传文件oss,多个文件
   *
   * @param
   * @return
   * @throws Exception
   */
  @Transactional
  public String[] uploadFilesOss(MultipartFile[] files) throws Exception {
    String[] filePaths = new String[files.length];
    //存储到oss服务器上，返回地址
    for (int i = 0; i < files.length; i++) {
      MultipartFile file = files[i];
      String filePath = OSSUploadUtil.uploadFileMu(file, file.getContentType());
      filePaths[i] = filePath;
    }
    //返回给数据库保存的文件路劲，除磁盘外
    return filePaths;
  }

  /**
   * 删除文件
   *
   * @param
   * @return
   * @throws Exception
   */
  @Transactional
  public boolean deleteFilesOss(String fileUrl) throws Exception {
    //删除存储到oss服务器上的文件
    Boolean isDelete = OSSUploadUtil.deleteFile(fileUrl);
    //如果服务器上删除成功，删除文件表中的文件
    if (isDelete) {
      return true;
    /*  Optional<File> optional = fileRepository.findByFilePathLike(fileUrl);
      if (optional.isPresent()) {
        File deleteFile = optional.get();
        deleteFile.deletedAtNow();
      //  fileRepository.save(deleteFile);
        return deleteFile;
      } else {
        return null;
      }*/
    } else {
      return false;
    }

  }

}
