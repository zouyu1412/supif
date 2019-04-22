//package com.tem;
//
//import com.sohu.auto.content.common.pojo.ResultVO;
//import com.sohu.auto.content.common.utils.DateUtils;
//import com.sohu.auto.content.common.utils.ExcelUtils;
//import com.sohu.auto.content.common.utils.SohuScsUtils;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.*;
//import java.util.*;
//import java.util.concurrent.Callable;
//import java.util.concurrent.FutureTask;
//import java.util.concurrent.TimeUnit;
//
///**
// * 评论注水
// * @Author:zouyu
// * @Date:2019/3/12 17:00
// */
//@Service
//public class WateringService {
//
//    private static final Logger logger = LoggerFactory.getLogger(WateringService.class);
//
//    /** 随机数生成器 */
//    private static final Random RANDOM_NUMBER_GENERATOR = new Random();
//
//    @Value("${spring.servlet.multipart.max-file-size}")
//    private Integer mxFileSize;
//
//    @Autowired
//    CommentService commentService;
//
//    /**
//     * 随机产生 [from ,to] 范围内随机数
//     * @param from
//     * @param to
//     * @return
//     */
//    private static int getRandomInt(int from, int to) {
//        return from + RANDOM_NUMBER_GENERATOR.nextInt(to - from + 1);
//    }
//
//    public ResultVO commentWatering(String excelFilePath){
//        try {
//            File file = new File(excelFilePath);
//            List<List<String>> lists = ExcelUtils.readExcel(file);
//            commentService.bumpComment("","","","","","","","",System.currentTimeMillis());
//            return ResultVO.ok();
//        }catch (Exception e){
//            logger.error("error occur while watering",e);
//            return ResultVO.ERROR;
//        }
//    }
//
//    public List<String> filesUpload(String filePath) {
//        // 文件夹地址  可自行更改
////        String path = "D:\\imagesTest";
//        List<String> re = new ArrayList<>();
//        try {
//            LinkedHashMap<String, File> map = getFiles(filePath);
//            for (Map.Entry<String, File> entry : map.entrySet()) {
//                String key = entry.getKey();
//                File value = entry.getValue();
//                ResultVO resultVO = upImg(key, value);
//                if (resultVO.getCode() == 200) {
//                    re.add((String)resultVO.getData());
//                }
//            }
//            return re;
//        }catch (Exception e){
//            logger.error("file Upload error",e);
//            return null;
//        }
//    }
//
//    /**
//     * 读取某个目录下所有文件、文件夹
//     * @param path
//     * @return LinkedHashMap<String,String>
//     */
//    public static LinkedHashMap<String,File> getFiles(String path) {
//        LinkedHashMap<String,File> files = new LinkedHashMap<>();
//        File file = new File(path);
//        File[] tempList = file.listFiles();
//
//        for (int i = 0; i < tempList.length; i++) {
//            files.put(tempList[i].getName(),tempList[i]);
//        }
//        return files;
//    }
//
//    /**
//     * 上传图片
//     * @param file
//     * @return
//     */
//    private ResultVO upImg(String fileName, File file) {
//        try {
//            byte[] bytes = getBytes(file);
//            String originalFilename = fileName;
//
//            // 1.4 查看bytes大小是否存在
//            if (bytes.length > mxFileSize) {
//                return ResultVO.build(-1, "图片最大为20M大小");
//            }
//            // 2. 生成图片名称 上传到云图，存储路径为wap/home/20181109/+ 图片md5 + jpg
//            String imgName = new StringBuilder("pecker").append(DateUtils.getMini(new Date())).append("/").append(DigestUtils.md5Hex(bytes)).append(getSuffix(originalFilename)).toString();
//            // 3. 进行上传图片 使用FutureTask异步线程
//            boolean isSuccess;
//            FutureTask<Boolean> futureTask = new FutureTask(new TaskThread(imgName, bytes, false, true));
//            // 线程提交运行
//            futureTask.run();
//            // 超时5秒
//            isSuccess = futureTask.get(10, TimeUnit.SECONDS);
//            logger.debug("PicUploadController uploadImg isSuccess[{}], filename[{}], originalFilename[{}]", isSuccess, imgName, originalFilename);
//            // 4. 成功，返回图片地址
//            if (isSuccess) {
//                // http://m3.auto.itc.cn/auto/content/20181130/1543564126900.jpg
//                String url = new StringBuilder("//m").append(getRandomInt(1, 4)).append(".auto.itc.cn/").append(SohuScsUtils.UPLOAD_ROOT).append(imgName).toString();
//                return ResultVO.ok(url);
//            }
//        } catch (Exception e) {
//            logger.error("PicUploadController uploadImg error", e);
//        }
//        return ResultVO.build(-1, "出现错误，请重新上传");
//    }
//
//    /**
//     * 异步线程上传图片
//     */
//    class TaskThread implements Callable<Boolean> {
//        private String uploadKey;
//        private byte[] content;
//        private boolean needWaterMark;
//        private boolean ensureSuccess;
//
//        public TaskThread(String uploadKey, byte[] content, boolean needWaterMark, boolean ensureSuccess) {
//            this.uploadKey = uploadKey;
//            this.content = content;
//            this.needWaterMark = needWaterMark;
//            this.ensureSuccess = ensureSuccess;
//        }
//
//        @Override
//        public Boolean call() {
//            boolean isSuccess = false;
//            try {
//                isSuccess = SohuScsUtils.uploadObject(uploadKey, content, needWaterMark, ensureSuccess);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return isSuccess;
//        }
//    }
//
//    /**
//     * 文件转byte数组
//     * @param file
//     * @return
//     */
//    private byte[] getBytes(File file){
//        byte[] buffer = null;
//        try {
//            FileInputStream fis = new FileInputStream(file);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
//            byte[] b = new byte[1000];
//            int n;
//            while ((n = fis.read(b)) != -1) {
//                bos.write(b, 0, n);
//            }
//            fis.close();
//            bos.close();
//            buffer = bos.toByteArray();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return buffer;
//    }
//
//    private String getSuffix(String originalFilename) {
//        if (StringUtils.isBlank(originalFilename)) {
//            return "";
//        }
//        return StringUtils.substring(originalFilename, originalFilename.lastIndexOf('.')).trim();
//    }
//}
