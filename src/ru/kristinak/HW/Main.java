package ru.kristinak.HW;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String archiveName, List<String> filePath) {
        try (ZipOutputStream spelerArchief = new ZipOutputStream(new FileOutputStream(archiveName))) {
            int count = 0;
            for (String oneFile : filePath) {
                count++;
                FileInputStream fis = new FileInputStream(oneFile);
                ZipEntry entry = new ZipEntry("packed_GameProgress" + count + ".dat");
                spelerArchief.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                spelerArchief.write(buffer);
                spelerArchief.closeEntry();
                fis.close();
                new File(oneFile).delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        ArrayList<GameProgress> players = new ArrayList<>();
        List<String> filePath = new ArrayList<>();

        GameProgress player1 = new GameProgress(5, 2, 1, 300);
        GameProgress player2 = new GameProgress(4, 10, 5, 1000);
        GameProgress player3 = new GameProgress(2, 4, 2, 200);

        players.add(player1);
        players.add(player2);
        players.add(player3);

        int count = 0;
        for (GameProgress player : players) {
            count++;
            String fileName = "C:\\Users\\kostyuninak\\Desktop\\Учеба Java\\Games\\savegames\\gameProgress" + count + ".dat";
            filePath.add(fileName);
            saveGame(fileName, player);
        }
        zipFiles("C:\\Users\\kostyuninak\\Desktop\\Учеба Java\\Games\\savegames\\zip.zip", filePath);
    }
}



