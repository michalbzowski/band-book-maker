import com.itextpdf.text.DocumentException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException, DocumentException {
        var path = "/Users/up75ir/Downloads/NUTY_KOSCIELNE/";

        File file = new File(path);
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        for (String directory : directories) {
            System.out.println("Directory: " + directory);
            var strings = listFilesUsingFilesList(path + directory);
            extracted(path, directory, strings);
        }

        System.out.println();

    }

    private static void extracted(String path, String directory, List<String> strings) throws IOException, DocumentException {
        String[] page = {"A", "B"};
        int i = 0;
        SimplePage[] simplePages = new SimplePage[strings.size()];
        for (String s : strings) {

            var pathToImage = path + "/" + directory + "/" + s;
            BufferedImage myPicture = ImageIO.read(new File(pathToImage));
            System.out.println("- path to image: " + pathToImage);
            simplePages[i] = new SimplePage(pathToImage, myPicture, page[i % 2], i + 1);
            i++;

        }
        ComplexPage complexPage = null;
        List<TwoPages> twoPagesList = new ArrayList<>();
        for (int n = 0; n < strings.size(); n = n + 8) {
            System.out.println("- page: " + n);
            complexPage = ComplexPage.builder()
                    ._1(getSimpleImage(simplePages, 1 + n))
                    ._2(getSimpleImage(simplePages, 2 + n))
                    ._3(getSimpleImage(simplePages, 3 + n))
                    ._4(getSimpleImage(simplePages, 4 + n))
                    ._5(getSimpleImage(simplePages, 5 + n))
                    ._6(getSimpleImage(simplePages, 6 + n))
                    ._7(getSimpleImage(simplePages, 7 + n))
                    ._8(getSimpleImage(simplePages, 8 + n))
                    .build();
            TwoPages twoPages = complexPage.createTwoSidedPdf();
            twoPagesList.add(twoPages);
        }
        new Pdf(twoPagesList).saveTo(path, directory);
    }

    private static SimplePage getSimpleImage(SimplePage[] simplePages, int i) {
        if (i > simplePages.length) {
            return SimplePage.empty();
        }
        return simplePages[i - 1];
    }

    static List<String> listFilesUsingFilesList(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .filter(fileName -> fileName.toString().contains("jpg"))
                    .map(Path::toString)
                    .sorted()
                    .collect(Collectors.toList());
        }
    }
}
