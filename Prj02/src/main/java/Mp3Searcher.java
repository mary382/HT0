import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Mp3Searcher {
    public static int numOfFiles;

    public static int findMP3(File[] listOfFiles, String fileExt, ArrayList<Author> authors) {
        Path path;
        System.setProperty("log4j.configurationFile", "C:\\app\\Prj02\\log4j2.xml");
        Logger logger = LogManager.getLogManager().getLogger("logger");

        for (File item : listOfFiles) {
            path = Paths.get(item.getAbsolutePath());
            if (item.isDirectory()) {
                File[] list = item.listFiles();
                if (list.length > 0) {
                    findMP3(list, fileExt, authors);
                }
            }
            if (item.isFile() && getFileExtension(item).equals(fileExt)) {
                boolean songWasAdded = false;
                int numOfArtists;
                int numOfAlbums;
                numOfFiles++;
                Mp3File mp3File = null;
                try {
                    mp3File = new Mp3File(String.valueOf(path));
                } catch (IOException e) {
                    System.out.println("File reading error! "+item.getAbsolutePath()+" will be missed.");
                    continue;
                } catch (InvalidDataException e) {
                    System.out.println("Invalid data error! "+item.getAbsolutePath()+" will be missed.");
                    continue;
                } catch (UnsupportedTagException e) {
                    System.out.println("Can't identify the tags! "+item.getAbsolutePath()+" will be missed.");
                    continue;
                }
                if (mp3File.hasId3v2Tag()) {
                    ID3v2 id3v2Tag = mp3File.getId3v2Tag();
                    String artist = id3v2Tag.getArtist();
                    String album = id3v2Tag.getAlbum();
                    String song = id3v2Tag.getTitle();
                    if (artist.equals("")) {
                        artist = "Unknown artist";
                    }
                    if (authors.size() < 1) {
                        authors.add(new Author(artist));
                    }
                    numOfArtists = authors.size();
                    for (Author author : authors) {
                        if (author.getName().equals(artist)) {
                            if (author.getAlbums().size() < 1) {
                                author.getAlbums().add(new Album(album));
                            }
                            numOfAlbums = author.getAlbums().size();
                            for (Album album1 : author.getAlbums()) {
                                if (album1.getAlbumTitle().equals(album)) {
                                    if (album1.getSongs().size() < 1) {
                                        album1.getSongs().add(new Song(song, item.getAbsolutePath()));
                                        break;
                                    }

                                    for (Song song1 : album1.getSongs()) {
                                        if (song1.getTitle().equals(song)) {
                                            songWasAdded = true;

                                      //     logger.log(Level.INFO,song1.getTitle()+" in "+song1.getPath()+
                                      //              " has the same tags as "+song+" in "+item.getAbsolutePath());
                                            System.out.println("Song \"" + song1.getTitle() + "\" in (" + song1.getPath() +
                                                    ") has the same tags as \"" + song + "\" in (" + item.getAbsolutePath() + ").");
                                            break;
                                        }
                                    }
                                    if (!songWasAdded) {
                                        album1.getSongs().add(new Song(song, item.getAbsolutePath()));
                                    }
                                } else if (numOfAlbums == 1) {
                                    Song song2 = new Song(song, item.getAbsolutePath());
                                    Album album2 = new Album(album);
                                    album2.getSongs().add(song2);
                                    author.getAlbums().add(album2);
                                    break;
                                } else {
                                    numOfAlbums--;
                                    continue;
                                }
                            }
                        } else if (numOfArtists == 1) {
                            Song song2 = new Song(song, item.getAbsolutePath());
                            Album album2 = new Album(album);
                            album2.getSongs().add(song2);
                            Author author2 = new Author(artist);
                            author2.getAlbums().add(album2);
                            authors.add(author2);
                            break;
                        } else {
                            numOfArtists--;
                            continue;
                        }
                    }
                } else {
                    System.out.println(item.getName() + " doesn't have Id3v2Tags.");
                }
            }
        }
        return numOfFiles;
    }

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            String extension = fileName.substring(i + 1);
            return extension;
        } else return "Error in extension identification.";
    }
}
