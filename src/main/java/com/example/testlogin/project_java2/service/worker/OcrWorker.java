package com.example.testlogin.project_java2.service.worker;


import com.example.testlogin.project_java2.service.OcrService;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class OcrWorker  implements OcrService {

    private final Tesseract tesseract;

    @Autowired
    public OcrWorker(Tesseract tesseract) {
        this.tesseract = tesseract;
    }


    @Override
    public String getImageString(File file) throws TesseractException {

        try {
            BufferedImage image = ImageIO.read(file);
            if (image == null) {
                throw new IOException("Failed to read image from file: " + file.getAbsolutePath());
            }
            BufferedImage brightenedImage = preprocessImage(image);
            // Lưu ảnh đã tiền xử lý để kiểm tra
            File preprocessedFile = new File(System.getProperty("java.io.tmpdir") + "/preprocessed_" + file.getName());
            ImageIO.write(brightenedImage, "png", preprocessedFile);

            return tesseract.doOCR(preprocessedFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error while processing file";
        }
    }

    // Tiền xử lý ảnh
    @Override
    public BufferedImage preprocessImage(BufferedImage image) {
        BufferedImage brightenedImage = brightenImage(image);
        return convertToGrayscale(brightenedImage);
    }

    // Làm sáng ảnh
    @Override
    public BufferedImage brightenImage(BufferedImage image) {
        BufferedImage brightenedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = brightenedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return brightenedImage;
    }

    // Đảo ảnh sang ảnh đen trắng
    @Override
    public BufferedImage convertToGrayscale(BufferedImage image) {
        BufferedImage grayscaleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = grayscaleImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return grayscaleImage;
    }
}
