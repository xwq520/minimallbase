package com.minimall.boilerplate.business.service;

import com.minimall.boilerplate.business.dto.CommodityDTO;
import com.minimall.boilerplate.business.dto.assembler.CommodityAssembler;
import com.minimall.boilerplate.business.entity.Commodity;
import com.minimall.boilerplate.business.entity.Dictionary;
import com.minimall.boilerplate.business.entity.User;
import com.minimall.boilerplate.business.repository.CommodityRepository;
import com.minimall.boilerplate.business.repository.DictionaryRepository;
import com.minimall.boilerplate.business.repository.UserRepository;
import com.minimall.boilerplate.business.repository.CommoditySpecification;
import com.minimall.boilerplate.common.*;
import com.minimall.boilerplate.exception.BadRequestException;
import com.minimall.boilerplate.exception.NotFoundRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.minimall.boilerplate.common.Message.E124;
import static java.util.Objects.nonNull;

@Service
public class CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private DictionaryRepository dictionaryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommodityAssembler commodityAssembler;

    // 新增
    @Transactional
    public boolean commodityAdd(CommodityDTO commodityDTO){
        Commodity commodity = new Commodity();
        // 用户
        Optional<User> user = userRepository.findByUserId(String.valueOf(commodityDTO.getUserId()));
        if(!user.isPresent()){
            return false;
        }
        commodity.setUser(user.get());
        commodity.setComNo("C"+DateHelper.LongToStringFormat(System.currentTimeMillis(),DateHelper.normalFormtNo)+""+ (int)((Math.random()*9+1)*1000));
        commodity.setInventory(nonNull(commodityDTO.getInventory())?commodityDTO.getInventory():0);
        commodity.setSalesVolume(nonNull(commodityDTO.getSalesVolume())?commodityDTO.getSalesVolume():0);
        commodity.setManufacturer(commodityDTO.getManufacturer());
        commodity.setHeadline(commodityDTO.getHeadline());
        commodity.setSubtitle(commodityDTO.getSubtitle());
        commodity.setOriginalPrice(nonNull(Double.valueOf(commodityDTO.getOriginalPrice()))?Double.valueOf(commodityDTO.getOriginalPrice()):0);
        commodity.setSellingPrice(nonNull(Double.valueOf(commodityDTO.getSellingPrice()))?Double.valueOf(commodityDTO.getSellingPrice()):0);
        // type 类型
        if(!CheckUtils.isEmpty(commodityDTO.getType())){
            Optional<Dictionary> dictionary = dictionaryRepository.findByCode(commodityDTO.getType());
            if(dictionary.isPresent()){
                commodity.setDictionary(dictionary.get());
            }
        }
       // commodity.setProducedDate(Timestamp.valueOf(commodityDTO.getProducedDate()));
       // commodity.setStartGuaPeriodDate(Timestamp.valueOf(commodityDTO.getStartGuaPeriodDate()));
       // commodity.setEndGuaPeriodDate(Timestamp.valueOf(commodityDTO.getEndGuaPeriodDate()));
        commodity.setPreviewImg(commodityDTO.getPreviewImg());
        commodity.setCommodityDetails(commodityDTO.getCommodityDetails());
        commodity.setCommodityStatus(commodityDTO.getCommodityStatus());
        commodityRepository.save(commodity);
        return true;
    }


   // 列表
   @Transactional
   public List<CommodityDTO> commodityList(CommodityDTO commodityDTO, Pageable pageable, MessageObject mo){
        // wechat search list
        if(!CheckUtils.isEmpty(commodityDTO.getCodeKey())){
            Optional<User>  optional = userRepository.findByCodeKey(commodityDTO.getCodeKey());
            if(!optional.isPresent()){
                return null;
            }
            commodityDTO.setCodeKey("");
            commodityDTO.setUserId(optional.get().getUserId());
        }
       Page<Commodity> commodities = commodityRepository.findAll(CommoditySpecification.conditionQuerySpec(commodityDTO), pageable);
       if(nonNull(commodities)){
           mo.put("total", commodities.getTotalElements());
       }
       return commodities.getContent().stream()
               .map(commodityAssembler::toDTO)
               .flatMap(dto -> dto.isPresent() ? Stream.of(dto.get()) : Stream.empty())
               .collect(Collectors.toList());
   }

   // 编辑
   @Transactional
   public boolean commodityUpdate(CommodityDTO commodityDTO){
        if(!CheckUtils.isEmpty(commodityDTO.getId())){
            Optional<Commodity> commodity = commodityRepository.findByIdAndUserUserId(commodityDTO.getId(),commodityDTO.getUserId());
            if(commodity.isPresent()){
               // if(!CheckUtils.isEmpty(commodityDTO.getCommodityStatus())){
                    commodity.get().setCommodityStatus(commodityDTO.getCommodityStatus());
               // }
               // if(!CheckUtils.isEmpty(commodityDTO.getInventory())){
                    commodity.get().setInventory(nonNull(commodityDTO.getInventory())?commodityDTO.getInventory():0);
               // }
             //   if(!CheckUtils.isEmpty(commodityDTO.getSalesVolume())){
                    commodity.get().setSalesVolume(nonNull(commodityDTO.getSalesVolume())?commodityDTO.getSalesVolume():0);
               // }
              //  if(!CheckUtils.isEmpty(commodityDTO.getManufacturer())){
                    commodity.get().setManufacturer(commodityDTO.getManufacturer());
               // }
             //   if(!CheckUtils.isEmpty(commodityDTO.getHeadline())){
                    commodity.get().setHeadline(commodityDTO.getHeadline());
              //  }
               // if(!CheckUtils.isEmpty(commodityDTO.getSubtitle())){
                    commodity.get().setSubtitle(commodityDTO.getSubtitle());
             //   }
               // if(!CheckUtils.isEmpty(commodityDTO.getOriginalPrice())){
                    commodity.get().setOriginalPrice(nonNull(Double.valueOf(commodityDTO.getOriginalPrice()))?Double.valueOf(commodityDTO.getOriginalPrice()):0);
              //  }
               // if(!CheckUtils.isEmpty(commodityDTO.getSellingPrice())){
                    commodity.get().setSellingPrice(nonNull(Double.valueOf(commodityDTO.getSellingPrice()))?Double.valueOf(commodityDTO.getSellingPrice()):0);
              //  }
                if(!CheckUtils.isEmpty(commodityDTO.getType())){
                    // type 类型
                    Optional<Dictionary> dictionary = dictionaryRepository.findByCode(commodityDTO.getType());
                    if(dictionary.isPresent()){
                        commodity.get().setDictionary(dictionary.get());
                    }
                }
              //  if(!CheckUtils.isEmpty(commodityDTO.getProducedDate())){
              //      commodity.get().setProducedDate(Timestamp.valueOf(commodityDTO.getProducedDate()));
              //  }
              //  if(!CheckUtils.isEmpty(commodityDTO.getStartGuaPeriodDate())){
              //      commodity.get().setStartGuaPeriodDate(Timestamp.valueOf(commodityDTO.getStartGuaPeriodDate()));
              //  }
              //  if(!CheckUtils.isEmpty(commodityDTO.getEndGuaPeriodDate())){
             //       commodity.get().setEndGuaPeriodDate(Timestamp.valueOf(commodityDTO.getEndGuaPeriodDate()));
             //   }

                commodity.get().setPreviewImg(commodityDTO.getPreviewImg());
                commodity.get().setCommodityDetails(commodityDTO.getCommodityDetails());
                // soldOutTime 下架时间
                if(!CheckUtils.isEmpty(commodityDTO.getCommodityStatus())
                        && commodityDTO.getCommodityStatus() == Constants.COMMODITY_STATUS_02){
                    commodity.get().setSoldOutTime(new Timestamp(System.currentTimeMillis()));
                }
                commodityRepository.save(commodity.get());
            }
        }
       return true;
   }

    // 删除
    @Transactional
    public void delete(CommodityDTO commodityDTO) {
        List<Commodity> commodityList = new ArrayList<>();
        Arrays.stream(commodityDTO.getDelIds()).forEach(id -> {
            Optional<Commodity> deleteData = Optional.ofNullable(commodityRepository.findById(Long.valueOf(id)))
                    .orElseThrow(() -> new NotFoundRequestException(Message.E109));
            deleteData.get().deletedAtNow();
            commodityList.add(deleteData.get());
        });

        if(commodityList.size()>0){
            commodityRepository.saveAll(commodityList);
        }
    }

    // 获取单条数据
   /* @Transactional
    public CommodityDTO commodityInfo(Long commodityId){
        Optional<Commodity> commodity = commodityRepository.findById(commodityId);
        if(commodity.isPresent()){
            Optional<CommodityDTO> commodityOptional = commodityAssembler.toDTO(commodity.get());
            if(commodityOptional.isPresent()){
                return commodityOptional.get();
            }
        }
        return null;
    }*/

    /**
     *  上传 视频 语音
     *
     controller 使用列子
     @RequestMapping(method = POST, value = "/asset/repairUpload",consumes = MULTIPART_FORM_DATA_VALUE, produces = JSON_UTF8)
     public ResponseEntity<MessageObject> repairUpload(HttpServletRequest request){

      * @param request
     *
     *  filePath 文件Id
     *  fileName 文件名称
     *  filePlayPath 文件播放完整地址
     *
     * @return 返回保存成功的文件名
     */
    public Map uploadMediaFiles(HttpServletRequest request){

        try{
            // 返回保存成功的文件名
            String path = "";
            Map<String,String> map = new HashMap<String,String>();
            //创建一个通用的多部分解析器
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            //判断 request 是否有文件上传,即多部分请求
            if(multipartResolver.isMultipart(request)){

                //转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;

                //取得request中的所有文件名
                Iterator<String> iter = multiRequest.getFileNames();
                while(iter.hasNext()){
                    //记录上传过程起始时的时间，用来计算上传时间
                    // int pre = (int) System.currentTimeMillis();
                    //取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if(file != null){
                        //取得当前上传文件的文件名称
                        String myFileName = file.getOriginalFilename();
                        //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                        if(!CheckUtils.isEmpty(myFileName.trim())){
                            // System.out.println(myFileName);
                            // 获取文件后缀名
                            String originalFilename = file.getOriginalFilename();
                            String fileType = originalFilename.substring(originalFilename.lastIndexOf("."),originalFilename.length());
                            //重命名上传后的文件名
                            String fileNameStr = "m_imgs_" + System.currentTimeMillis()+ fileType;
                            // 定义上传路径 localFileDisk,localFileMkdirs
                            path = File.separator + "Users"+File.separator+"mac-xwq"+File.separator+"works"+File.separator+"imgs"+File.separator+fileNameStr;
                            //path =  ""+fileName;
                            // 文件上传目录
                            File localFile = new  File(path);
                            localFile.setWritable(true, false);
                            //image/jpeg
                            //if(!localFile.exists()){
                               // localFile.mkdirs();
                            //}
                            file.transferTo(localFile);

                            map.put("filePath",""+fileNameStr);
                            map.put("fileName",fileNameStr);
                           // map.put("filePlayPath",httpRequstImgsServer+'/'+path);

                            // 存储到file表
                          //  File fileObj = new File();
                           // fileObj.setFileId(fileNameStr);
                           // fileObj.setFilePath("/"+path);
                           // fileRepository.save(fileObj);
                        }
                    }
                }
            }
            return map;
        } catch (Exception e) {
           // logger.error("读取导入文件流失败", e);
            throw new BadRequestException(E124);
        }

    }

}
