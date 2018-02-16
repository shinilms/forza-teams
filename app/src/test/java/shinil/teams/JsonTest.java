package shinil.teams;

import com.google.gson.Gson;

import org.junit.Test;

import shinil.teams.model.Team;

import static junit.framework.Assert.assertEquals;


/**
 * @author shinilms
 */

public class JsonTest {
    /**since we're using GSON to parse JSON, it's useless to unit test JSON parsing using GSON as it is already
     * done inside the library. so testing if fields are mapped correctly to the model class*/
    @Test
    public void test_parse_team() throws Exception {
        String json = "{\n" +
                "        \"name\": \"Kerala Blasters\",\n" +
                "        \"national\": false,\n" +
                "        \"country_name\": \"India\"\n" +
                "      }";

        Team team = new Gson().fromJson(json, Team.class);

        assertEquals(team.getName(), "Kerala Blasters");
        assertEquals(team.isNational(), false);
        assertEquals(team.getCountry_name(), "India");
    }
}