package sv.restservices;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import sv.domain.Card;
import sv.persistence.CardService;
import sv.persistence.CardServiceProvider;

@Path("/deck")
public class CardRestService {
	private CardService service=CardServiceProvider.getCardService();
	
	@GET
	@Path("/getanalysis")
	@Produces(MediaType.TEXT_HTML)
	public String getDeckAnalysis (@QueryParam("deckcode") String code, @QueryParam("queries") String queries) {
		ArrayList<Card>deck=getDeckFromCode(code);
		try {
		File htmlTemplateFile = new File(getClass().getClassLoader().getResource(("template.html")).getFile());
		String htmlString = FileUtils.readFileToString(htmlTemplateFile);
		String title = "New Page";
		String body = "<h2>DeckCode: "+code+"</h2><br><br>";
		for(String s : queries.split(",")) {
			body+="<div id="+s+"div>";
			body+="<h3>"+s+"</h3><br>";
			body+=generateDiv(deck,s);
			body+="</div><br>";
		}
		htmlString = htmlString.replace("$title", title);
		htmlString = htmlString.replace("$body", body);
		return htmlString;
		}catch(IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private ArrayList<Card> analysisSelector(ArrayList<Card> deck,String s){
		if (s.equals("EarthSigil")) {
			return service.getEarthSigilCardsFromDeck(deck);
		}
		if (s.equals("Burn")) {
			return service.getBurnCardsFromDeck(deck);
		}
		else {
			return null;
		}
	}
	
	private String generateDiv(ArrayList<Card>deck,String category) {
		String body="";
		for (Card c:analysisSelector(deck,category)) {
			body+=c.getCard_name()+"<br>";
		}
		return body;
	}
	
	@GET
	@Path("/spells")
	@Produces("application/json")
	public String getSpellCards (@QueryParam("code") String code) {
		ArrayList<Card>deck=getDeckFromCode(code);
		
		JsonArrayBuilder jab=getJsonFromDeck(service.getSpellCardsFromDeck(deck));
		return jab.build().toString();
		
	}
	
	@GET
	@Path("/amulets")
	@Produces("application/json")
	public String getAmuletCards (@QueryParam("code") String code) {
		ArrayList<Card>deck=getDeckFromCode(code);
		
		JsonArrayBuilder jab=getJsonFromDeck(service.getAmuletCardsFromDeck(deck));
		return jab.build().toString();
		
	}
	
	@GET
	@Path("/followers")
	@Produces("application/json")
	public String getFollowerCards (@QueryParam("code") String code) {
		ArrayList<Card>deck=getDeckFromCode(code);
		
		JsonArrayBuilder jab=getJsonFromDeck(service.getFollowerCardsFromDeck(deck));
		return jab.build().toString();
		
	}
	
	@GET
	@Path("/removal")
	@Produces("application/json")
	public String getRemovalCards (@QueryParam("code") String code) {
		ArrayList<Card>deck=getDeckFromCode(code);
		
		JsonArrayBuilder jab=getJsonFromDeck(service.getRemovalCardsFromDeck(deck));
		return jab.build().toString();
		
	}
	
	@GET
	@Path("/aoeremoval")
	@Produces("application/json")
	public String getAoERemovalCards (@QueryParam("code") String code) {
		ArrayList<Card>deck=getDeckFromCode(code);
		
		JsonArrayBuilder jab=getJsonFromDeck(service.getAoERemovalCardsFromDeck(deck));
		return jab.build().toString();
		
	}
	
	@GET
	@Path("/burn")
	@Produces("application/json")
	public String getBurnRemovalCards (@QueryParam("code") String code) {
		ArrayList<Card>deck=getDeckFromCode(code);
		
		JsonArrayBuilder jab=getJsonFromDeck(service.getBurnCardsFromDeck(deck));
		return jab.build().toString();
		
	}
	
	@GET
	@Path("/early")
	@Produces("application/json")
	public String getEarlyCards (@QueryParam("code") String code) {
		ArrayList<Card>deck=getDeckFromCode(code);
		
		JsonArrayBuilder jab=getJsonFromDeck(service.getEarlyCardsFromDeck(deck));
		return jab.build().toString();
		
	}
	
	@GET
	@Path("/mid")
	@Produces("application/json")
	public String getMidCards (@QueryParam("code") String code) {
		ArrayList<Card>deck=getDeckFromCode(code);
		
		JsonArrayBuilder jab=getJsonFromDeck(service.getMidCardsFromDeck(deck));
		return jab.build().toString();
		
	}
	
	@GET
	@Path("/late")
	@Produces("application/json")
	public String getLateCards (@QueryParam("code") String code) {
		ArrayList<Card>deck=getDeckFromCode(code);
		
		JsonArrayBuilder jab=getJsonFromDeck(service.getLateCardsFromDeck(deck));
		return jab.build().toString();
		
	}
	
	private ArrayList<Card> getDeckFromCode(String code) {
		JSONObject data=readJsonFromUrl("https://shadowverse-portal.com/api/v1/deck/import?format=json&deck_code="+code).getJSONObject("data");
		String deckhash=data.getString("hash");
		
		JSONObject deckdata=readJsonFromUrl("https://shadowverse-portal.com/api/v1/deck?format=json&lang=EN&hash="+deckhash).getJSONObject("data").getJSONObject("deck");
		JSONArray cards=deckdata.getJSONArray("cards");

		ArrayList<Card>deck=new ArrayList<Card>();
		for (int i = 0; i < cards.length(); ++i) {
	        JSONObject obj = cards.getJSONObject(i);
	        deck.add(service.getCardByID(obj.getInt("card_id")));
		}
		return deck;
	}

	private JSONObject readJsonFromUrl(String url) {
		try {
		InputStream is = new URL(url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			is.close();
			return json;
		}catch(Exception e){
			e.printStackTrace();
		}return null;
	}

	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	private JsonArrayBuilder getJsonFromDeck(ArrayList<Card>deck) {
		JsonArrayBuilder results=Json.createArrayBuilder();
		for (Card c:deck) {
			JsonObjectBuilder job=Json.createObjectBuilder();
			 job.add("card_id",c.getCard_id());
	         job.add("foil_card_id",c.getFoil_card_id());
	         job.add("card_set_id",c.getCard_set_id());
	         job.add("card_name",c.getCard_name());
	         job.add("is_foil",c.getIs_foil());
	         job.add("char_type",c.getChar_type());
	         job.add("clan",c.getClan()); 
	         job.add("tribe_name",c.getTribe_name());
	         job.add("skill_disc",c.getSkill_disc());
	         job.add("evo_skill_disc",c.getEvo_skill_disc()); 
	         job.add("cost",c.getCost());
	         job.add("atk",c.getAtk()); 
	         job.add("life",c.getLife()); 
	         job.add("evo_atk",c.getEvo_atk()); 
	         job.add("evo_life",c.getEvo_life()); 
	         job.add("rarity",c.getRarity()); 
	         job.add("get_red_ether",c.getGet_red_ether()); 
	         job.add("use_red_ether",c.getUse_red_ether()); 
	         job.add("description",c.getDescription()); 
	         job.add("evo_description",c.getEvo_description()); 
	         job.add("cv","-");
	         job.add("base_card_id",c.getBase_card_id());
	         if (c.getTokens()==null) {
	        	 job.add("tokens", "-");
	         }else {
	         job.add("tokens",c.getTokens());
	         }
	         job.add("normal_card_id",c.getNormal_card_id()); 
	         job.add("format_type",c.getFormat_type()); 
	         job.add("restricted_count",c.getRestricted_count());
	         results.add(job);
		}
		return results;
	}

}
