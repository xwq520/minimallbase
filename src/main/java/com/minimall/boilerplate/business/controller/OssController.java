package com.minimall.boilerplate.business.controller;


import com.minimall.boilerplate.business.service.FileService;
import com.minimall.boilerplate.common.CheckUtils;
import com.minimall.boilerplate.common.MessageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


import static com.minimall.boilerplate.common.Constants.JSON_UTF8;
import static com.minimall.boilerplate.common.Message.I102;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("api/oss")
public class OssController {
@Autowired
FileService fileService;

/**
 * Created by Administrator on 2017/7/28.
 * 多文件上传
 */
@RequestMapping(method = POST, value = "/putObject", consumes = MULTIPART_FORM_DATA_VALUE, produces = JSON_UTF8)
public ResponseEntity<MessageObject> putObject(@RequestPart("file") MultipartFile file) throws Exception {
  MessageObject mo = MessageObject.of(I102);
  String filePath  = fileService.uploadFilesOss(file);
  Map<String,String> map=new HashMap<>();
  map.put("filePath",filePath);
  mo.put("file", map);
  if (file==null) return new ResponseEntity<>(NO_CONTENT);
  return new ResponseEntity<>(mo, OK);
}

  /**
   * Created by Administrator on 2017/7/28.
   * 文件删除
   */
  @RequestMapping(method = GET, value = "/deleteObject",  produces = JSON_UTF8)
  public ResponseEntity<MessageObject> deleteObject(@RequestParam(value="fileUrl") String fileUrl) throws Exception {
    MessageObject mo = MessageObject.of(I102);
    boolean file  = fileService.deleteFilesOss(fileUrl);
    mo.put("file", file);
    if (CheckUtils.isEmpty(fileUrl)) return new ResponseEntity<>(NO_CONTENT);
    return new ResponseEntity<>(mo, OK);
  }

  /**
   * Created by Administrator on 2017/7/28.
   * 视频文件上传
   */
  @RequestMapping(method = POST, value = "/putMedia", consumes = MULTIPART_FORM_DATA_VALUE, produces = JSON_UTF8)
  public ResponseEntity<MessageObject> putObject(@RequestParam(value="fileUrl") String fileUrl, @RequestPart("file") MultipartFile file) throws Exception {
    MessageObject mo = MessageObject.of(I102);
    if(!CheckUtils.isEmpty(fileUrl)){
      fileService.deleteFilesOss(fileUrl);
    }
    String filePath  = fileService.uploadFilesOss(file);
    Map<String,String> map=new HashMap<>();
    map.put("filePath",filePath);
    mo.put("file", map);
    if (CheckUtils.isEmpty(fileUrl)&&file==null) return new ResponseEntity<>(NO_CONTENT);
    return new ResponseEntity<>(mo, OK);
  }
}