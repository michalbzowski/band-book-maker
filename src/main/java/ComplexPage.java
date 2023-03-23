import lombok.Builder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.awt.Image.SCALE_AREA_AVERAGING;
import static java.awt.Image.SCALE_DEFAULT;

@Builder
public class ComplexPage {
    SimplePage _1;
    SimplePage _2;
    SimplePage _3;
    SimplePage _4;
    SimplePage _5;
    SimplePage _6;
    SimplePage _7;
    SimplePage _8;

    public TwoPages createTwoSidedPdf() {
        //1cm = 0,3937 cali
        //2,154 cm = 1 cal
        //21 cm = 8,268 cal
        //29,7 = 11,693 cal
        double dpi = 300.0;
        double a4Width = 8.268;
        double a4WidthPixels = a4Width * dpi;

        double a4Height = 11.693;
        double a4HeightPixels = a4Height * dpi;
        var bufferedImageA = new BufferedImage((int) a4WidthPixels, (int) a4HeightPixels, _1.getImp().getType());
        var bufferedImageB = new BufferedImage((int) a4WidthPixels, (int) a4HeightPixels, _1.getImp().getType());

        var graphicsA = bufferedImageA.getGraphics();
        var graphicsB = bufferedImageB.getGraphics();

        int scaleType = SCALE_AREA_AVERAGING;
        int margin = 60;
        var scaled1 = _1.getImp().getScaledInstance((int) a4WidthPixels / 2 - margin, (int) a4HeightPixels / 2, scaleType);
        var scaled2 = _2.getImp().getScaledInstance((int) a4WidthPixels / 2 - margin, (int) a4HeightPixels / 2, scaleType);
        var scaled3 = _3.getImp().getScaledInstance((int) a4WidthPixels / 2 - margin, (int) a4HeightPixels / 2, scaleType);
        var scaled4 = _4.getImp().getScaledInstance((int) a4WidthPixels / 2 - margin, (int) a4HeightPixels / 2, scaleType);

        var scaled5 = _5.getImp().getScaledInstance((int) a4WidthPixels / 2 - margin, (int) a4HeightPixels / 2, scaleType);
        var scaled6 = _6.getImp().getScaledInstance((int) a4WidthPixels / 2 - margin, (int) a4HeightPixels / 2, scaleType);
        var scaled7 = _7.getImp().getScaledInstance((int) a4WidthPixels / 2 - margin, (int) a4HeightPixels / 2, scaleType);
        var scaled8 = _8.getImp().getScaledInstance((int) a4WidthPixels / 2 - margin, (int) a4HeightPixels / 2, scaleType);

        ImageObserver imageObserver = (img, infoflags, x, y, width, height) -> true;
        graphicsA.drawImage(scaled1, margin, 0, imageObserver);
        graphicsB.drawImage(scaled2, (int) a4WidthPixels / 2, 0, imageObserver);
        graphicsA.drawImage(scaled4, (int) a4WidthPixels / 2, 0, imageObserver);
        graphicsB.drawImage(scaled3, margin, 0, imageObserver);

        graphicsA.drawImage(scaled5, margin, (int) a4HeightPixels / 2, imageObserver);
        graphicsB.drawImage(scaled6, (int) a4WidthPixels / 2, (int) a4HeightPixels / 2, imageObserver);
        graphicsA.drawImage(scaled8, (int) a4WidthPixels / 2, (int) a4HeightPixels / 2, imageObserver);
        graphicsB.drawImage(scaled7, margin, (int) a4HeightPixels / 2, imageObserver);

        var collect = Arrays.stream(_1.getFilePath().split("/")).collect(Collectors.toList());
        var title = collect.get(collect.size() - 1).replace("05.jpg", "");
        graphicsA.setColor(Color.BLACK);
        graphicsA.drawString(title, margin, margin);
        graphicsB.drawString(title, margin, margin);
        graphicsA.drawString(title, margin + (int) a4WidthPixels / 2, margin);
        graphicsB.drawString(title, margin + (int) a4WidthPixels / 2, margin);

        graphicsA.drawString(title, margin, margin + (int) a4HeightPixels / 2);
        graphicsB.drawString(title, margin, margin + (int) a4HeightPixels / 2);
        graphicsA.drawString(title, margin + (int) a4WidthPixels / 2, margin + (int) a4HeightPixels / 2);
        graphicsB.drawString(title, margin + (int) a4WidthPixels / 2, margin + (int) a4HeightPixels / 2);



        graphicsA.setColor(Color.BLACK);
        graphicsA.drawLine((int) a4WidthPixels / 2, 0, (int) a4WidthPixels / 2, (int) a4HeightPixels);
        graphicsA.drawLine(0, (int) a4HeightPixels / 2, (int) a4WidthPixels, (int) a4HeightPixels / 2);


        graphicsB.setColor(Color.BLACK);
        graphicsB.drawLine((int) a4WidthPixels / 2, 0, (int) a4WidthPixels / 2, (int) a4HeightPixels);
        graphicsB.drawLine(0, (int) a4HeightPixels / 2, (int) a4WidthPixels, (int) a4HeightPixels / 2);


        graphicsA.setColor(Color.WHITE);
        graphicsA.fillRect(0, 0, margin, (int) a4HeightPixels);
        graphicsA.fillRect((int) a4WidthPixels - margin - 1, 0, (int) a4WidthPixels, (int) a4HeightPixels);
        graphicsB.setColor(Color.WHITE);
        graphicsB.fillRect(0, 0, margin, (int) a4HeightPixels);
        graphicsB.fillRect((int) a4WidthPixels - margin - 1, 0, (int) a4WidthPixels, (int) a4HeightPixels);


        return new TwoPages(title, bufferedImageA, bufferedImageB);

    }
}
