package ch.sheremet.katarina.cocktailspro.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ch.sheremet.katarina.cocktailspro.model.BeverageDetails;
import ch.sheremet.katarina.cocktailspro.model.Ingredients;


/**
 * Deserialize Beverage Details json.
 * Json has uncomfortable structure, that's why deserializer was implemented:
 * "strIngredient1":"Tequila",
 * "strIngredient2":"Triple sec" - till 15 and
 * <p>
 * "strMeasure1":"1 1\/2 oz ",
 * "strMeasure2":"1\/2 oz ",
 * "strMeasure3":"1 oz ",
 * "strMeasure4":"",
 * "strMeasure5":"",
 * <p>
 * Use non empty strings.
 * <p>
 * Resources:
 * https://stackoverflow.com/questions/37231894/
 * https://stackoverflow.com/questions/26099133/manually-parse-part-of-a-response-when-using-retrofit
 */
public class BeverageDetailsDeserializer implements JsonDeserializer<BeverageDetails> {
    @Override
    public BeverageDetails deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        BeverageDetails beverageDetails = new BeverageDetails();

        List<Ingredients> ingredientsList = new ArrayList<>();
        beverageDetails.setIngredients(ingredientsList);

        JsonObject jsonObject = json.getAsJsonArray().get(0).getAsJsonObject();

        if (jsonObject.has("idDrink")) {
            beverageDetails.setId(jsonObject.get("idDrink").getAsString());
        }

        if (jsonObject.has("strDrink")) {
            beverageDetails.setName(jsonObject.get("strDrink").getAsString());
        }

        if (jsonObject.has("strInstructions")) {
            beverageDetails.setInstructions(jsonObject.get("strInstructions").getAsString());
        }

        if (jsonObject.has("strInstructions")) {
            beverageDetails.setInstructions(jsonObject.get("strInstructions").getAsString());
        }

        if (jsonObject.has("strGlass")) {
            beverageDetails.setGlassType(jsonObject.get("strGlass").getAsString());
        }

        if (jsonObject.has("strDrinkThumb")) {
            beverageDetails.setThumbnailUrl(jsonObject.get("strDrinkThumb").getAsString());
        }

        if (jsonObject.has("strCategory")) {
            beverageDetails.setCategory(jsonObject.get("strCategory").getAsString());
        }

        if (jsonObject.has("strIBA") && !jsonObject.get("strIBA").isJsonNull()) {
            beverageDetails.setIBA(jsonObject.get("strIBA").getAsString());
        } else {
            beverageDetails.setIBA("-");
        }

        for (int i = 1; i <= 15; i++) {
            if (jsonObject.has("strIngredient" + i) && jsonObject.has("strMeasure" + i)) {
                String ingredient = jsonObject.get("strIngredient" + i).getAsString().trim();
                String measure = jsonObject.get("strMeasure" + i).getAsString().trim();
                if (!ingredient.isEmpty() || !measure.isEmpty()) {
                    ingredientsList.add(new Ingredients(ingredient, measure, beverageDetails.getId()));
                }
            }
        }

        return beverageDetails;
    }
}
