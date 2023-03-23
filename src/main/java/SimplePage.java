import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;

@Getter
@AllArgsConstructor
public class SimplePage {

    private String filePath;
    private BufferedImage imp;
    private String page;
    private int number;

    public static SimplePage empty() {
        var imp1 = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = imp1.createGraphics();
        graphics.setPaint(new Color(255, 255, 255));
        graphics.fillRect(0, 0, imp1.getWidth(), imp1.getHeight());
        return new SimplePage("", imp1, "", 0);
    }
}
