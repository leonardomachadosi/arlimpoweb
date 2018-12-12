package br.ufma.lsdi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    InputStream inputStream;

    public CsvReader(InputStream is){
        this.inputStream = is;
    }



    public List<String[]> read(){
        List<String[]> resultList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String cvsLine;
            cvsLine = reader.readLine();
            while ((cvsLine = reader.readLine()) != null){
                String[] row = cvsLine.split(";");
                resultList.add(row);
            }

        }catch (IOException ex){
            throw new RuntimeException("Erro in reading CVS file:" + ex);
        } finally {
            try {
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error while closing inputsteam:  "+ e);
            }
        }
        return resultList;
    }

}
