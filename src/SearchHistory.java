import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchHistory {
    private ObservableList<String> searchs = FXCollections.observableArrayList();
    private File searchRecorder;
    private PrintStream ps;

    SearchHistory(File file){
        this.searchRecorder = file;
        try {
            ps = new PrintStream(file);
        }catch(IOException e){
            System.err.println("Output stream failed while initializing");
        }
    }

    public void trackSearchHistory(String s) {
        searchs.add(s);
        ps.println(s);
    }

    public ObservableList<String> getSearchs() {
        return searchs;
    }
}
