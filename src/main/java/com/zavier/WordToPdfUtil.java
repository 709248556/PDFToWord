package com.zavier;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * word文档转pdf
 *
 * @author JHY
 * @date 2023/03/08
 */
public class WordToPdfUtil {

    /**
     * 获取aspose证书
     */
    private static boolean getLicense() {
        boolean result = false;
        InputStream is = null;
        try {
            Resource resource = new ClassPathResource("license.xml");
            is = resource.getInputStream();
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * word转pdf静态方法
     *
     * @param wordPath word文件全路径含文件名
     * @param pdfPath  pdf输出全路径含文件名
     * @return boolean 成功/失败
     */
    public static boolean docToPdf(String wordPath, String pdfPath) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicense()) {
            return false;
        }
        FileOutputStream os = null;
        try {
            long old = System.currentTimeMillis();
            // 新建一个空白pdf文档
            File file = new File(pdfPath);
            os = new FileOutputStream(file);
            // inPath是将要被转化的word文档
            Document doc = new Document(wordPath);
            // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.PDF);
            long now = System.currentTimeMillis();
            // 转化用时
            System.out.println("word转换pdf成功，共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            System.err.println("word转pdf失败:" + e.getMessage());
            return false;
        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

}
