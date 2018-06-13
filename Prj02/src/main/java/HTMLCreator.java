import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HTMLCreator {
    public static void getHTML(ArrayList<Author> authors) {

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>Found mp3 files</title>\n" +
                "  </head>\n" +
                "  <body>");

        for (Author author : authors) {
            html.append("<p>Artist - " + author.getName() + "</p>");
            for (Album album : author.getAlbums()) {
                html.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;Album - " + album.getAlbumTitle() + "</p>");
                for (Song song : album.getSongs()) {
                    html.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Song - " + song.getTitle() +
                            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + song.getPath() + "</p>");
                }
            }
        }

        html.append("</body>\n" +
                "</html>");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("index.html"))) {
            bw.write(html.toString());
        } catch (IOException ex) {
            System.out.println("HTML file reading error!");
        }

    }
}
