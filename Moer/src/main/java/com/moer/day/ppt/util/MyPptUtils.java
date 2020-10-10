package com.moer.day.ppt.util;

import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;
import org.apache.xmlbeans.XmlException;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author : hepw
 * @date : 2020/4/3 9:30
 * @func : PPT 生成测试
 */
public class MyPptUtils {

    private static final String FILE_PATH = "E:\\bdgit\\test\\addingimage.pptx";
    private static final String YICHE_LOGO = "E:\\image\\bb.png";
    private static final String BRAND_LOGO = "E:\\image\\m_8_100.png";

    public void test() {

        XSLFSlideShow slideShow;
        String resultString = null;
        try {
            slideShow = new XSLFSlideShow(FILE_PATH);
            XMLSlideShow xmlSlideShow = new XMLSlideShow(slideShow.getPackage());
            List<XSLFSlide> slides = xmlSlideShow.getSlides();
            StringBuilder sb = new StringBuilder();
            int idx = 0;
            for (XSLFSlide slide : slides) {
                idx++;
                if (idx == 1) {
                    XSLFTextBox textBox = ((XSLFTextBox) slide.getShapes().get(6));
                    textBox.getTextParagraphs().get(0).getTextRuns().get(0).setText("2020-03-30");
                    ((XSLFAutoShape) slide.getShapes().get(5)).getTextParagraphs().get(0).getTextRuns().get(0).setText("朗逸营销报告");
                    //((org.apache.poi.xslf.usermodel.XSLFAutoShape)slide.getShapes()[5]).getTextParagraphs().get(0).getTextRuns().get(1).setText("");
                } else if (idx == 4) {
                    File image = new File(BRAND_LOGO);
                    byte[] picture = IOUtils.toByteArray(new FileInputStream(image));
                    //slide.getShapes()[2]
                    //((XSLFPictureShape)slide.getShapes()[4]).getPictureData().getPackagePart().;
                    XSLFPictureShape pic = (XSLFPictureShape) slide.getShapes().get(4);
                    pic.getPictureData().setData(picture);
                    XSLFTable table = (XSLFTable) slide.getShapes().get(1);
                    List<XSLFTableRow> rows = table.getRows();
                    rows.get(2).getCells().get(0).getTextParagraphs().get(0).getTextRuns().get(0).setText("朗逸");
                    rows.get(2).getCells().get(1).getTextParagraphs().get(0).addNewTextRun().setText("123");
                    rows.get(3).getCells().get(0).getTextParagraphs().get(0).getTextRuns().get(0).setText("卡罗拉");
                    rows.get(3).getCells().get(1).getTextParagraphs().get(0).addNewTextRun().setText("456");
                    rows.get(4).getCells().get(0).getTextParagraphs().get(0).getTextRuns().get(0).setText("思域");
                    rows.get(4).getCells().get(1).getTextParagraphs().get(0).addNewTextRun().setText("789");
                } else if (idx == 7) {
                    XSLFGraphicFrame fg = (XSLFGraphicFrame) slide.getShapes().get(4);
                    List<POIXMLDocumentPart.RelationPart> parts = slide.getRelationParts();
                    XSLFChart documentPart = parts.get(2).getDocumentPart();
                    System.out.println(slide.getShapes().get(0).getClass());
                }
                sb.append(slide.getTitle());
            }
            File file = new File("1.pptx");
            FileOutputStream out = new FileOutputStream(file);
            //slideShow.write(out);
            xmlSlideShow.write(out);
            resultString = sb.toString();

        } catch (OpenXML4JException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(resultString);
    }

    public void ttt() {

        try {
            //creating a presentation
            XMLSlideShow ppt = new XMLSlideShow();

            //creating a slide in it
            XSLFSlide slide = ppt.createSlide();

            //reading an image
            File image = new File("E:\\bb.png");

            //converting it into a byte array
            byte[] picture = IOUtils.toByteArray(new FileInputStream(image));

            //adding the image to the presentation
            XSLFPictureData idx = ppt.addPicture(picture, PictureData.PictureType.PNG);

            //creating a slide with given picture on it
            XSLFPictureShape pic = slide.createPicture(idx);

            //creating a file object
            File file = new File("addingimage.pptx");
            FileOutputStream out = new FileOutputStream(file);

            //saving the changes to a file
            ppt.write(out);
            System.out.println("image added successfully");
            out.close();

        } catch (Exception ex) {
            return;
        }
    }

    /**
     * @return
     * @Author
     * @Description //TODO 集合分割
     * @Date 2019/1/24 16:48
     * @Param
     */
    private List<List<Map>> getSubListMap(List list, int len) {
        List<List<Map>> listGroup = new ArrayList<>();
        if (list.size() < len) {
            listGroup.add(list);
            return listGroup;
        }

        int listSize = list.size();
        //子集合的长度
        int toIndex = len;
        for (int i = 0; i < list.size(); i += len) {
            if (i + len > listSize) {
                toIndex = listSize - i;
            }
            List<Map> newList = list.subList(i, i + toIndex);
            listGroup.add(newList);
        }
        return listGroup;
    }

    /**
     * @return
     * @Author
     * @Description //TODO 复制ppt中的幻灯片 ,并设置幻灯片在ppt中的位置
     * @Date 2019/1/24 11:16
     * @Param slide：被复制的幻灯片，ppt：ppt对象， index：复制的ppt插入到第几页
     */
    public static XSLFSlide copyPage(XSLFSlide slide, XMLSlideShow ppt, int index) throws IOException {
        try {
            //List<XSLFShape> shapes = new ArrayList<XSLFShape>( Arrays.asList(slide.getShapes()));
            XSLFSlide slide2 = ppt.createSlide();

            slide2.importContent(slide);
            //排序（在PPT中的第几页）
            ppt.setSlideOrder(slide2, index);
            return slide2;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * @return
     * @Author
     * @Description //TODO 六福珠宝--往PPT中表格填充数据
     * @Date 2019/4/21 16:41
     * @Param
     */
    public void insertExcelDataToPpt(XSLFSlide slide, XMLSlideShow ppt, List<Map> dataTable) throws IOException {
        List<List<Map>> subListMap = getSubListMap(dataTable, 10);
        int k = 1;
        int p = 1;
        for (int a = 0; a < subListMap.size(); a++) {
            int h = 1;
            XSLFSlide slide1 = copyPage(slide, ppt, p);
            List<XSLFShape> shapes = slide.getShapes();
            for (XSLFShape shape : shapes) {
                Rectangle2D rcn = shape.getAnchor();
                //ppt页中是否含有表格判断
                if (shape instanceof XSLFTable) {
                    XSLFTable table = (XSLFTable) shape;
                    table.setAnchor(rcn);
                    for (int d = 0; d < subListMap.get(a).size(); d++) {
                        XSLFTableRow tr = table.getRows().get(h);
                        int cellSize = tr.getCells().size();
                        for (int j = 0; j < cellSize; j++) {
                            if (j == 0) {
                                tr.getCells().get(j).setText(String.valueOf(k));
                            } else if (j == 1) {
                                String projectName = String.valueOf(subListMap.get(a).get(d).get("projectName"));
                                tr.getCells().get(j).setText(projectName);
                            } else if (j == 2) {
                                String projectAdds = String.valueOf(subListMap.get(a).get(d).get("projectAdds"));
                                tr.getCells().get(j).setText(projectAdds);
                            } else if (j == 3) {
                                String ddNum = String.valueOf(subListMap.get(a).get(d).get("ddNum"));
                                tr.getCells().get(j).setText(ddNum);
                            } else if (j == 4) {
                                String sjNum = String.valueOf(subListMap.get(a).get(d).get("sjNum"));
                                tr.getCells().get(j).setText(sjNum);
                            }
                        }
                        k += 1;
                        h += 1;
                    }
                }
            }
            p += 1;
        }
        ppt.removeSlide(0);
    }


}
