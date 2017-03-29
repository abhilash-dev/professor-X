package ai.profX.controller;

import java.io.IOException;
import java.util.*;

import ai.profX.common.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ai.profX.model.Character;
import ai.profX.model.Confidence;
import ai.profX.model.Question;
import ai.profX.service.CharacterService;
import ai.profX.service.ConfidenceService;
import ai.profX.service.QuestionService;

@CrossOrigin
@RestController
@RequestMapping("/admin/*")
public class AdminController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private ConfidenceService confidenceService;

    @RequestMapping(method = RequestMethod.GET, value = "/dq")
    public @ResponseBody
    List<Question> getQuestionsToDelete() {
        List<Question> questionList = questionService.getAllQuestions();
        return questionList;
        // Display this list along with check boxes for the admin to select the questions to be deleted
    }

    @RequestMapping(method = RequestMethod.POST, value = "/dq")
    public @ResponseBody
    void deleteQuestions(@RequestParam String questionIds) {
        JSONObject jsonObject = new JSONObject(questionIds);
        JSONArray array = jsonObject.getJSONArray("questionIds");

        List<Long> questionIdList = new ArrayList<Long>();
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                questionIdList.add(array.getLong(i));
            }
        }

        if (questionIdList != null && !questionIdList.isEmpty()) {
            Iterator<Long> questionIdIterator = questionIdList.iterator();
            Question question = null;
            while (questionIdIterator.hasNext()) {
                Long questionId = questionIdIterator.next();
                question = questionService.getQuestionById(questionId);
                if (question != null) {
                    questionService.removeQuestion(questionId);
                }
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/dc")
    public @ResponseBody
    List<Character> getCharactersToDelete() {
        List<Character> characterList = characterService.getAllCharacters();
        return characterList;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/dc")
    public @ResponseBody
    void deleteCharacters(@RequestParam String characterIds) {
        JSONObject jsonObject = new JSONObject(characterIds);
        JSONArray array = jsonObject.getJSONArray("characterIds");

        List<Long> characterIdList = new ArrayList<Long>();
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                characterIdList.add(array.getLong(i));
            }
        }
        if (characterIdList != null && !characterIdList.isEmpty()) {
            Iterator<Long> characterIdIterator = characterIdList.iterator();

            while (characterIdIterator.hasNext()) {
                Long characterId = characterIdIterator.next();
                Character character = characterService.getCharacterById(characterId);
                if (character != null) {
                    characterService.removeCharacter(characterId);
                }
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
    public @ResponseBody
    List<Character> getAllObjectsToTrain() {
        List<Character> characterList = characterService.getAllCharacters();
        return characterList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/retrain/{characterId}")
    public @ResponseBody
    Map<String, Object> getCharacterToTrain(@PathVariable("characterId") long characterId) {
        Map<String, Object> responseMap = new HashMap<>();
        Character character = characterService.getCharacterById(characterId);
        if (character != null) {
            responseMap.put("character", character);

            List<Question> questionList = questionService.getAllQuestions();
            responseMap.put("questionList", questionList);

            List<Confidence> confidenceList = confidenceService.getConfidenceByCharacterId(characterId);
            responseMap.put("confidenceList", confidenceList);
        }

        return responseMap;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/retrain/{characterId}")
    public @ResponseBody
    String postLearnInfo(@RequestParam("retrainData") String retrainData, @PathVariable("characterId") long characterId) {
        Map<Long, Integer> retrainMap = new HashMap<>();
        TypeReference<Map<Long, Integer>> typeReference = new TypeReference<Map<Long, Integer>>() {
        };
        if (retrainData != null) {
            try {
                retrainMap = new ObjectMapper().readValue(retrainData, typeReference);
                int answer = 0;
                for (long questionId : retrainMap.keySet()) {
                    answer = retrainMap.get(questionId + "");
                    if (answer == Constants.YES || answer == Constants.NO) {
                        int value = answer * Constants.RETRAIN_SCALE;
                        confidenceService.updateConfidence(characterId, questionId, value);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Success";
        } else
            return "Failure";

    }
}