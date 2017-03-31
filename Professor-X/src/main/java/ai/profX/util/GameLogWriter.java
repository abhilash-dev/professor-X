package ai.profX.util;

import ai.profX.model.Question;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Admin on 29-03-2017.
 */
public class GameLogWriter {

    public static void writeToFile(LinkedHashMap<Long, Integer> characterValues, HashMap<Long, Integer> askedQuestions,
                                   long questionId, int answer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("playLog.txt",true))) {
            writer.write(String.valueOf(questionId));
            writer.newLine();

            writer.write(String.valueOf(answer));
            writer.newLine();

            writer.write(new ObjectMapper().writeValueAsString(askedQuestions));
            writer.newLine();

            writer.write(new ObjectMapper().writeValueAsString(characterValues));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
