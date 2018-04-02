import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchHistory {

    /* This class keeps track of the users search and watched video history adn place data into a txt file*/
    private ObservableList<String> searchs = FXCollections.observableArrayList();
    private File searchRecorder;

    SearchHistory(File file){
        this.searchRecorder = file;
    }

    // This function keeps track of the latest searches and watched videos
    // and appends to an observable array list
    public void trackSearchHistory(String s) {

        try {
            FileWriter fw = new FileWriter(searchRecorder,true);
            BufferedWriter bos = new BufferedWriter(fw);
            PrintWriter ps = new PrintWriter(bos);

            searchs.add(s);
            ps.println(s);
            ps.close();
        }catch(IOException e){
            System.err.println("Output stream failed while initializing");
        }

    }

    public ObservableList<String> getSearchs() {
        return searchs;
    }

    // Continuously adds the searches to the list
    public void setSearchs() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(searchRecorder));
            String s = br.readLine();
            while(null != s){
                searchs.add(s);
                s = br.readLine();
            }
            br.close();
        }catch(IOException e){
            System.err.println("Error while opening file stream");
        }
    }
}

