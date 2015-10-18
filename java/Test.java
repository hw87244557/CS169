import java.util.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.*;
import java.io.IOException;

public class Test {
	public static void main(String[] args) {
		String filePath = "recording.pcm";
		String outputFilePath = "outputfile.txt";
		int bufferSize = 2048;
		short[] buffer = new short[bufferSize/4];
		try {
			DataInputStream dataInputStream = new DataInputStream(
                    new BufferedInputStream(new FileInputStream(filePath)));
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath));
			while (dataInputStream.available() > 0) {
		        int i = 0;
		        while (dataInputStream.available() > 0 && i < buffer.length) {
		            buffer[i] = dataInputStream.readShort();
		            bw.append(Short.toString(buffer[i]));
		            bw.newLine();
		            System.out.println(buffer[i]);
		            i++;
		        }
		    }
		    dataInputStream.close();
		    bw.close();
		} catch (IOException e) {
            e.printStackTrace();	
		}
	}
}