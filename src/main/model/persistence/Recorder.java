package model.persistence;

import java.io.*;

public class Recorder {
    private FileWriter writer;

    // EFFECTS: constructs a writer that will write data to file
    public Recorder(File file) throws IOException {
        writer = new FileWriter(file);
    }

}
