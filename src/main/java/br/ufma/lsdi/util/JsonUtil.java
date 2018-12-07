package br.ufma.lsdi.util;

import br.ufma.lsdi.model.Matcher;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class JsonUtil {

   public static String getjsonMatches(List<Matcher> matches, List<String> capabilities){

       JSONObject jsonObject = new JSONObject();
       JSONArray jsonArrayCapability = new JSONArray();
       for (String capability : capabilities) {
           jsonArrayCapability.put(capability);
       }
       jsonObject.put("capabilities",jsonArrayCapability);

       JSONObject jsonObjectMatchers = new JSONObject();
       for (Matcher mat : matches) {
           jsonObjectMatchers.put(mat.getMatche(),mat.getValue());
       }
       jsonObject.put("matchers", jsonObjectMatchers);

    return String.valueOf(jsonObject);
   }





}
