package it.unipi.dii.dmml.soundrecognition.soundrecognition_application.classification;

import java.io.*;
import java.net.Socket;

public class Classifier {
    private DataOutputStream dataOutputStream;
    private DataInputStream bufferedReader;
    private final int FILE_PACKET_SIZE = 1000 * 1024;
    private final String classifierServerIp;
    private final int classifierServerPort;

    public Classifier() {
        this.classifierServerIp = "localhost";
        this.classifierServerPort = 5002;
    }


    /**
     * Function that returns audio class
     * @param audio Plot of to the film
     * @return audio class
     */
    public String getAudioClass(File audio)
    {
        try(Socket socket = new Socket(classifierServerIp,classifierServerPort)) {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            bufferedReader = new DataInputStream(socket.getInputStream());

            sendPlot();

            String audioclass = bufferedReader.readUTF();

            dataOutputStream.close();
            bufferedReader.close();

            return audioclass;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Function that sends a file to the server, dividing it into packet
     * @throws Exception if something goes wrong
     */
    private void sendPlot() throws Exception{
        int bytes;

        File file = new File("src/main/resources/it/unipi/dii/dmml/soundrecognition/soundrecognition_application/test.wav");
        FileInputStream fileInputStream = new FileInputStream(file);

        // Send the commands
        dataOutputStream.writeUTF("SendSound");
        dataOutputStream.flush();

        // send file size
        dataOutputStream.writeUTF(String.valueOf(file.length()));
        dataOutputStream.flush();

        // break file into chunks
        byte[] buffer = new byte[FILE_PACKET_SIZE];
        while ((bytes=fileInputStream.read(buffer))!=-1){
            dataOutputStream.write(buffer,0, bytes);
            dataOutputStream.flush();
        }
        fileInputStream.close();
        file.delete();
    }
}