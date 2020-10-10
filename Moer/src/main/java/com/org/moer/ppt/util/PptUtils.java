package com.org.moer.ppt.util;


import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.util.List;

/**
 * @author chenxh
 * @date 2020/4/3 4:20 下午
 */

@Slf4j
public class PptUtils {

    public static void outputPpt(XMLSlideShow ppt, String outputPath) {
        log.info("输出ppt中..... path为:{}", outputPath);
        File file = new File(outputPath);
        if(!file.exists()){
            //先得到文件的上级目录，并创建上级目录，在创建文件
            file.getParentFile().mkdir();
            try {
                //创建文件
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            ppt.write(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ppt.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("输出ppt完成");

    }

    /**
     * @param inputPath
     * @return org.apache.poi.xslf.usermodel.XMLSlideShow
     * @Description: <br>
     * 〈获取PPT对象，如果不存在创建一PPT对象〉
     * @date 2020/3/30 chenxh
     */
    public static XMLSlideShow inputPpt(String inputPath) {
        File file = new File(inputPath);
        XMLSlideShow ppt = null;
        try {
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ppt = new XMLSlideShow(fileInputStream);
            } else {
//                ppt = new XMLSlideShow();
                log.error("file do not exist");

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ppt;
    }


    private static ResourceLoader resourceLoader;

    @Autowired
    @Qualifier("ResourceLoader")
    public void setResourceLoader(ResourceLoader resourceLoader) {
        PptUtils.resourceLoader = resourceLoader;
    }


    /**
     * 获取PPT对象 读取资源的方式
     *
     * @param type
     * @return
     */
    public static XMLSlideShow inputPpt(Integer type) {
        XMLSlideShow ppt = null;
        try {
            log.info("读取ppt模板:" + type);
            Resource resource = resourceLoader.getResource("classpath:tmpl/" + type + "_tmpl.pptx");
            InputStream inputStream = resource.getInputStream();
            ppt = new XMLSlideShow(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ppt;
    }

    /**
     * @param ppt              ppt实体
     * @param pictrueFile      图片文件
     * @param pictureType      替换图片类型
     * @param layoutName       模板的名字
     * @param relationLocation 需要替换的文本的位置
     * @param removeShape      由于文本和图片是同时存在，需要删除一个
     * @return void
     * @description: <br>
     * 〈设置PPT母版的logo的图片〉
     * @date 2020/5/06 chenxh
     */
    public static void addPptMasterLogoForPicture(XMLSlideShow ppt, File pictrueFile, PictureData.PictureType pictureType, String layoutName, int relationLocation, int removeShape) {
        //  默认幻灯片母版是在幻灯片主数据的第0位置
        XSLFSlideMaster slideMaster = ppt.getSlideMasters().get(0);
        //获取自定义的板式
        XSLFSlideLayout layout = slideMaster.getLayout(layoutName);
        List<POIXMLDocumentPart> relations = layout.getRelations();
        //替换图片 删除文本
        List<XSLFShape> shapes = layout.getShapes();
        XSLFShape xslfShapeText = shapes.get(removeShape);
        if (pictrueFile.exists()) {
            POIXMLDocumentPart packagePart = relations.get(relationLocation);
            if (packagePart instanceof XSLFPictureData) {
                try {
                    XSLFPictureData xslfPictureData = null;
                    xslfPictureData = ppt.addPicture(pictrueFile, pictureType);
                    ((XSLFPictureData) packagePart).setData(xslfPictureData.getData());
                    log.info("picture width " + xslfPictureData.getImageDimension().width);
                    log.info("picture height " + xslfPictureData.getImageDimension().height);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //删除文本logo
                layout.removeShape(xslfShapeText);
                log.info("delete  xslfShapeText");
            }
        }
    }

    /**
     * @param ppt              ppt实体
     * @param text             logo 名字
     * @param layoutName       模板的名字
     * @param relationLocation 需要替换的文本的位置
     * @param removeShape      由于文本和图片是同时存在，需要删除一个
     * @return void
     * @description: <br>
     * 〈设置PPT母版的logo文字〉
     * @date 2020/5/06 chenxh
     */
    public static void addPptMasterLogoForText(XMLSlideShow ppt, String text, String layoutName, int relationLocation, int removeShape) {
        //  默认幻灯片母版是在幻灯片主数据的第0位置
        XSLFSlideMaster slideMaster = ppt.getSlideMasters().get(0);
        //获取自定义的板式
        XSLFSlideLayout layout = slideMaster.getLayout(layoutName);
        List<XSLFShape> shapes = layout.getShapes();
        // 替换文本，删除图片
        XSLFShape xslfShapePicture = shapes.get(removeShape);

        XSLFShape xslfShape = shapes.get(relationLocation);

        if (xslfShape instanceof XSLFTextShape) {
            for (XSLFTextParagraph textParagraph : ((XSLFTextShape) xslfShape).getTextParagraphs()) {
                List<XSLFTextRun> textRuns = textParagraph.getTextRuns();
                for (XSLFTextRun textRun : textRuns) {
                    //清空原来的数据
                    textRun.setText("");
                }
                if (textRuns.size() > 0) {
                    //设置对应文本的内容
                    textRuns.get(0).setText(text);
                }
            }
            //删除图片logo
            if (StringUtils.isNotEmpty(text)) {
                layout.removeShape(xslfShapePicture);
                log.info("delete  xslfShapePicture");
            }
        }
    }

    /**
     * @param ppt
     * @param pictrueFile
     * @param pictureType
     * @return void
     * @Description: <br>
     * 〈设置PPT母版的背景图片〉
     * @date 2020/3/30 chenxh
     */
    public static void addPptMasterBackGround(XMLSlideShow ppt, File pictrueFile, PictureData.PictureType pictureType) {
        log.info("ppt width " + ppt.getPageSize().width);
        log.info("ppt height " + ppt.getPageSize().height);
//        Dimension size = new Dimension(660, 649);//设置ppt的模板的大小
//        ppt.setPageSize(size); //设置ppt的大小
        //幻灯片母版 添加图片

        //  默认幻灯片母版是在幻灯片主数据的第0位置
        XSLFSlideMaster slideMaster = ppt.getSlideMasters().get(0);
        XSLFPictureData xslfPictureData = null;
        try {
            xslfPictureData = ppt.addPicture(pictrueFile, pictureType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (xslfPictureData != null) {
            slideMaster.createPicture(xslfPictureData);
        } else {
            log.error("xslfPictureData is null ");
        }
        log.info("picture width " + xslfPictureData.getImageDimension().width);
        log.info("picture height " + xslfPictureData.getImageDimension().height);
    }

    public static void addPicture(XMLSlideShow ppt, File pictrueFile, PictureData.PictureType pictureType, int slidePageNum) throws IOException {
        log.info("ppt width " + ppt.getPageSize().width);
        log.info("ppt height " + ppt.getPageSize().height);
        XSLFSlide xslfSlide = ppt.getSlides().get(slidePageNum);
        List<POIXMLDocumentPart> relations = xslfSlide.getRelations();
        POIXMLDocumentPart poixmlDocumentPart = relations.get(1);

        String picturePath = System.getProperty("user.dir") + File.separator + "/src/main/resources/data/logo.png";
        File pictureFile = new File(picturePath);
        if (pictureFile.exists()) {
            XSLFPictureData pictureData = ppt.addPicture(pictureFile, PictureData.PictureType.PNG);
            if (poixmlDocumentPart instanceof XSLFPictureData) {
                XSLFPictureData xslfPictureData = (XSLFPictureData) poixmlDocumentPart;
                xslfPictureData.setData(pictureData.getData());
                log.info("picture width " + xslfPictureData.getImageDimension().width);
                log.info("picture height " + xslfPictureData.getImageDimension().height);
            }
        }


//        Dimension size = new Dimension(660, 649);//设置ppt的模板的大小
//        ppt.setPageSize(size); //设置ppt的大小
        //幻灯片母版 添加图片

        //  默认幻灯片母版是在幻灯片主数据的第0位置
        XSLFSlideMaster slideMaster = ppt.getSlideMasters().get(0);
        XSLFPictureData xslfPictureData = null;
        try {
            xslfPictureData = ppt.addPicture(pictrueFile, pictureType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (xslfPictureData != null) {
            slideMaster.createPicture(xslfPictureData);
        } else {
            log.error("xslfPictureData is null ");
        }

    }

    /**
     * @param path
     * @return byte[]
     * @Description: <br>
     * 〈通过图片地址 获取到对应 byte数组  使用 Thumbnails 压缩〉
     * @date 2020/4/10 chenxh
     */
    public static byte[] imageTobytes(String path) {


        byte[] data = null;
        try {
            //图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
            File fromPic = new File(path);
            if (fromPic.exists()) {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                Thumbnails.Builder<File> fileBuilder = Thumbnails.of(fromPic).scale(1f).outputFormat("jpg").outputQuality(0.4f);
                fileBuilder.toOutputStream(output);
                data = output.toByteArray();
                output.close();
            }
        } catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

}
