package com.moer.day.office.excel;

import com.moer.day.office.yiche.DealerSerialUploadInput;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author chenxh
 * @date 2020/8/6  10:07 上午
 * @func
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Class<Test> testClass = Test.class;
        System.out.println(testClass.getGenericInterfaces());
        System.out.println(testClass.getGenericSuperclass());
        File excelFile = new File("/Users/bita/Documents/code/a_moer/daydayup/src/main/resources/aa.xlsx");
        FileInputStream file = new FileInputStream(excelFile);
        Consumer<List<DealerSerialUploadInput>> consumer = (x) -> { System.out.println(x);};
        excelUtils(file, consumer, 30, DealerSerialUploadInput.class);
    }

    static void  excelUtils(InputStream inputStream, Consumer<List<DealerSerialUploadInput>> consumer, int cache, Class<DealerSerialUploadInput> tClass) throws Exception {
        new ExcelStreamReader().read(inputStream, new ExcelListener() {
            @Override
            public SheetListener<DealerSerialUploadInput> sheetListener(Excel.ExcelSheet sheet) {
                if (sheet.getShtIndex() == 0) {
                    //解析Sheet
                    return new SheetListener<DealerSerialUploadInput>(sheet, cache) {
                        @Override
                        protected void onList(int rowStart, int rowEnd, List<DealerSerialUploadInput> list) {
                            consumer.accept(list);
                        }
                    };
                } else {
                    return null;
                }
            }
        });
    }
}
