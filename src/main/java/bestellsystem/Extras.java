package bestellsystem;

import io.javalin.http.UploadedFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import io.javalin.http.Context;

public class Extras {


        /*public static void changeProfilePic(Context context) {
            UploadedFile file = context.uploadedFile("file");
            if (file == null) {
                context.status(400).result("Bitte wählen Sie eine Datei aus.");
                return;
            }

            String uploadDir = "uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            File uploadedFile = new File(uploadDir + file.getFilename());
            try (InputStream input = file.getContent(); FileOutputStream output = new FileOutputStream(uploadedFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                context.status(200).result("Datei erfolgreich hochgeladen: " + uploadedFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                context.status(500).result("Fehler beim Hochladen der Datei.");
            }
        }*/



}
