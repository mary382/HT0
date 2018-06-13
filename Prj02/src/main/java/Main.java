import Exceptions.NoArgsException;
import Exceptions.NoExistingDirectoriesException;
import Exceptions.NotDirectoryException;
import Exceptions.PathNotExistException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    public static int numberOfMP3;
    public static String fileExt = "mp3";
    public static ArrayList<Author> authors = new ArrayList<>();

    public static void main(String[] args) {
        if (args.length < 1) {
            try {
                throw new NoArgsException("There must at least 1 parameter be entered: directory path.");
            } catch (NoArgsException e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        HashSet<String> uniquePaths = new HashSet<>();
        for (int i = 0; i < args.length; i++) {
            uniquePaths.add(args[i]);
        }

        ArrayList<String> paths = new ArrayList<>(uniquePaths);
        Path path = null;

        for (int i = 0; i < paths.size(); ) {
            path = Paths.get(paths.get(i));
            //каталог для поиска указан верно?
            if (Files.notExists(path)) {
                try {
                    throw new PathNotExistException("Path " + paths.get(i) + " doesn't exist. It will not be added to the search list.");
                } catch (PathNotExistException e) {
                    System.out.println(e.getMessage());
                }
                paths.remove(i);
            } else if (Files.isDirectory(path)) {
                i++;
                continue;
            } else {

                try {
                    throw new NotDirectoryException("Only directories parameters must be specified. " + paths.get(i) + "is not a directory");
                } catch (NotDirectoryException e) {
                    System.out.println(e.getMessage());
                }
                paths.remove(i);
            }
        }

        if (paths.size() < 1) {
            try {
                throw new NoExistingDirectoriesException("There are no existing directories with such paths to find in. Check path's spelling and try again.");
            } catch (NoExistingDirectoriesException e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        for (String a_path : paths) {
            File folder = new File(a_path);
            //Массив файлов/папок каталога
            File[] list = folder.listFiles();
            if (list.length > 0) {
                numberOfMP3 = Mp3Searcher.findMP3(list, fileExt, authors);
            }
        }

        if (numberOfMP3 == 0) {
            System.out.println("There are no mp3 files in specified directory(ies).");
            return;
        }

        if (authors.size() > 0) {
            HTMLCreator.getHTML(authors);
        }
    }
}
