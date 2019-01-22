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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	public String getDeckAnalysis (@QueryParam("deckcode") String code, @QueryParam("queries") String queries) throws IOException {
		try {
		ArrayList<Card>deck=getDeckFromCode(code);
		Map<String,Map<Card,Integer>>categorymap=new LinkedHashMap<String,Map<Card,Integer>>();
		int boardBuildingCount=0;
		Map<Card,Integer>boardBuilding=service.getBoardBuildingCardsFromDeck(deck);
		for (int i : boardBuilding.values()) {
			boardBuildingCount+=i;
		}
		categorymap.put("Board Building", boardBuilding);
		int removalCount=0;
		Map<Card,Integer>removal=service.getRemovalCardsFromDeck(deck);
		for (int i : removal.values()) {
			removalCount+=i;
		}
		categorymap.put("Removal", removal);
		int resourceCount=0;
		Map<Card,Integer>resource=service.getResourceCardsFromDeck(deck);
		for (int i : resource.values()) {
			resourceCount+=i;
		}
		categorymap.put("Resources", resource);
		int followerCount=0;
		Map<Card,Integer>followercards=service.getFollowerCardsFromDeck(deck);
		for (int i : followercards.values()) {
			followerCount+=i;
		}
		categorymap.put("Followers",followercards);
		int spellCount=0;
		Map<Card,Integer>spellcards=service.getSpellCardsFromDeck(deck);
		for (int i : spellcards.values()) {
			spellCount+=i;
		}
		categorymap.put("Spells", spellcards);
		int amuletCount=0;
		Map<Card,Integer>amuletcards=service.getAmuletCardsFromDeck(deck);
		for (int i : amuletcards.values()) {
			amuletCount+=i;
		}
		categorymap.put("Amulets",amuletcards);
		int neutralCount=0;
		Map<Card,Integer>neutralcards=service.getNeutralCardsFromDeck(deck);
		for (int i : neutralcards.values()) {
			neutralCount+=i;
		}
		categorymap.put("Class Cards", service.getClassCardsFromDeck(deck));
		categorymap.put("Neutral Cards", neutralcards);
		categorymap.put("Evolve", service.getEvolveCardsFromDeck(deck));
		categorymap.put("Early Game", service.getEarlyCardsFromDeck(deck));
		categorymap.put("Mid Game", service.getMidCardsFromDeck(deck));
		categorymap.put("Late Game", service.getLateCardsFromDeck(deck));
		if (queries!=null && !queries.equals("")) {
		getQueryCategories(categorymap,deck,queries);
		}
		int zerocost=0;
		int onecost=0;
		int twocost=0;
		int threecost=0;
		int fourcost=0;
		int fivecost=0;
		int sixcost=0;
		int sevencost=0;
		int eightcost=0;
		int ninecost=0;
		int tencost=0;
		for (Card c:deck) {
			if (c.getCost()==0) {
				zerocost++;
			}if (c.getCost()==1) {
				onecost++;
			}if (c.getCost()==2) {
				twocost++;
			}if (c.getCost()==3) {
				threecost++;
			}if (c.getCost()==4) {
				fourcost++;
			}if (c.getCost()==5) {
				fivecost++;
			}if (c.getCost()==6) {
				sixcost++;
			}if (c.getCost()==7) {
				sevencost++;
			}if (c.getCost()==8) {
				eightcost++;
			}if (c.getCost()==9) {
				ninecost++;
			}
			if (c.getCost()>9) {
				tencost++;
			}
		}	

		JSONObject data=readJsonFromUrl("https://shadowverse-portal.com/api/v1/deck/import?format=json&deck_code="+code).getJSONObject("data");
		String deckhash=data.getString("hash");
		String deckclass=getCSS(data.getInt("clan"));

		File htmlTemplateFile = new File(getClass().getClassLoader().getResource(("template.html")).getFile());
		String htmlString = FileUtils.readFileToString(htmlTemplateFile);
		String title = "New Page";
		htmlString = htmlString.replace("$title", title);
		htmlString = htmlString.replace("$deck_hash", deckhash);
		htmlString = htmlString.replace("$deckclass", deckclass);
		htmlString = htmlString.replace("$cardcategory", "["+boardBuildingCount+", "+removalCount+", "+resourceCount+"]");
		htmlString = htmlString.replace("$cardcosts", "["+zerocost+", "+onecost+", "+twocost+", "+threecost+", "+fourcost+", "+fivecost+", "+sixcost+", "+sevencost+", "+eightcost+", "+ninecost+","+tencost+"]");
		htmlString = htmlString.replace("$cardcostline", "["+(zerocost+onecost)+", "+(twocost+zerocost+onecost)+", "+(threecost+twocost+zerocost+onecost)+", "+(fourcost+threecost+twocost+zerocost+onecost)+", "+(fivecost+fourcost+threecost+twocost+zerocost+onecost)+", "+(sixcost+fivecost+fourcost+threecost+twocost+zerocost+onecost)+", "+(sevencost+sixcost+fivecost+fourcost+threecost+twocost+zerocost+onecost)+", "+(eightcost+sevencost+sixcost+fivecost+fourcost+threecost+twocost+zerocost+onecost)+", "+(ninecost+eightcost+sevencost+sixcost+fivecost+fourcost+threecost+twocost+zerocost+onecost)+","+(tencost+ninecost+eightcost+sevencost+sixcost+fivecost+fourcost+threecost+twocost+zerocost+onecost)+"]");
		htmlString = htmlString.replace("$cardtypes", "["+followerCount+", "+spellCount+", "+amuletCount+"]");
		htmlString = htmlString.replace("$cardclasses", "["+(40-neutralCount)+", "+neutralCount+"]");
		htmlString = htmlString.replace("$cardcategories", generateCardCategoryDivs(categorymap));
		return htmlString;
		}catch(Exception e) {
			File htmlTemplateFile = new File(getClass().getClassLoader().getResource(("index.html")).getFile());
			String htmlString = FileUtils.readFileToString(htmlTemplateFile);
			return htmlString;
		}
	}
	
	
	
	private Map<String,Map<Card,Integer>> getQueryCategories(Map<String,Map<Card,Integer>>categorymap, ArrayList<Card>deck,String s){
		if (s.contains("Accelerate")) {
			 categorymap.put("Accelerate",service.getAccelerateCardsFromDeck(deck));
		}
		if (s.contains("Ambush")) {
			categorymap.put("Ambush",service.getAmbushCardsFromDeck(deck));
		}
		if (s.contains("AoERemoval")) {
			categorymap.put("AoE Removal",service.getAoERemovalCardsFromDeck(deck));
		}
		if (s.contains("Artifact")){
			categorymap.put("Artifacts",service.getArtifactCardsFromDeck(deck));
		}
		if (s.contains("Bounce")) {
			categorymap.put("Bounce",service.getBounceCardsFromDeck(deck));
		}
		if (s.contains("Buff")) {
			categorymap.put("Buffs",service.getBuffCardsFromDeck(deck));
		}
		if (s.contains("Burial Rite")) {
			categorymap.put("Burial Rite",service.getBurialRiteCardsFromDeck(deck));
		}
		if (s.contains("Burn")) {
			categorymap.put("Burn",service.getBurnCardsFromDeck(deck));
		}
		if (s.contains("Commander")) {
			categorymap.put("Commanders",service.getCommanderCardsFromDeck(deck));
		}
		if (s.contains("Countdown")) {
			categorymap.put("Countdown Amulets",service.getCountdownCardsFromDeck(deck));
		}
		if (s.contains("CardDraw")) {
			categorymap.put("Card Draw",service.getDrawCardsFromDeck(deck));
		}
		if (s.contains("EarthSigil")) {
			categorymap.put("Earth Sigils", service.getEarthSigilCardsFromDeck(deck));
		}
		if (s.contains("Enhance")) {
			categorymap.put("Enhance",service.getEnhanceCardsFromDeck(deck));
		}
		if (s.contains("EvoPoint")) {
			categorymap.put("Evolve Points",service.getEvoPointCardsFromDeck(deck));
		}
		if (s.contains("Fanfare")) {
			categorymap.put("Fanfare",service.getFanfareCardsFromDeck(deck));
		}
		if (s.contains("FriendlyFire")) {
			categorymap.put("Friendly Fire",service.getFriendlyFireCardsFromDeck(deck));
		}
		if (s.contains("Generation")) {
			categorymap.put("Resource Generation",service.getGenerationCardsFromDeck(deck));
		}
		if (s.contains("Heal")) {
			categorymap.put("Healing",service.getHealCardsFromDeck(deck));
		}
		if (s.contains("Invocation")) {
			categorymap.put("Invocation",service.getInvocationCardsFromDeck(deck));
		}
		if (s.contains("LastWords")) {
			categorymap.put("Last Words", service.getLastWordsCardsFromDeck(deck));
		}
		if (s.contains("Loot")) {
			categorymap.put("Loot", service.getLootCardsFromDeck(deck));
		}
		if (s.contains("Mysteria")) {
			categorymap.put("Mysteria", service.getMysteriaCardsFromDeck(deck));
		}
		if (s.contains("Necromancy")) {
			categorymap.put("Necromancy", service.getNecromancyCardsFromDeck(deck));
		}
		if (s.contains("Officer")) {
			categorymap.put("Officers", service.getOfficerCardsFromDeck(deck));
		}
		if (s.contains("Permanent")) {
			categorymap.put("Permanent Effects",service.getPermanentCardsFromDeck(deck));
		}
		if (s.contains("Reanimate")) {
			categorymap.put("Reanimate",service.getReanimateCardsFromDeck(deck));
		}
		if (s.contains("Rush")) {
			categorymap.put("Rush",service.getRushCardsFromDeck(deck));
		}
		if (s.contains("Storm")) {
			categorymap.put("Storm",service.getStormCardsFromDeck(deck));
		}
		if (s.contains("Subtraction")) {
			categorymap.put("Countdown Subtraction",service.getSubtractionCardsFromDeck(deck));
		}
		if (s.contains("Spellboost")) {
			categorymap.put("Spellboost",service.getSpellboostCardsFromDeck(deck));
		}
		if (s.contains("Summoner")) {
			categorymap.put("Summoning",service.getSummonerCardsFromDeck(deck));
		}
		if (s.contains("Tutor")) {
			categorymap.put("Tutors",service.getTutorCardsFromDeck(deck));
		}
		if (s.contains("Ward")) {
			categorymap.put("Ward",service.getWardCardsFromDeck(deck));
		}
		if (s.contains("Undamagable")) {
			categorymap.put("Spell & Effect damage resistance",service.getUndamagableCardsFromDeck(deck));
		}
		if (s.contains("Undestroyable")) {
			categorymap.put("Spell & Effect destruction resistance",service.getUndestoryableCardsFromDeck(deck));
		}
		if (s.contains("Untargetable")) {
			categorymap.put("Can't be targeted by Spells & Effects",service.getUntargetableCardsFromDeck(deck));
		}
		if (s.contains("Unattackable")) {
			categorymap.put("Can't be attacked",service.getUnattackableCardsFromDeck(deck));
		}
		if (s.contains("DamageLimiter")) {
			categorymap.put("Damage Limiters",service.getDamageLimiterCardsFromDeck(deck));
		}
		else {
			return null;
		}
		return categorymap;
	}
	
	private String generateCardCategoryDivs(Map<String,Map<Card,Integer>> categorymap) {
		String body="";
		int counter=0;
		for (Entry<String, Map<Card, Integer>> map:categorymap.entrySet()) {
			if (counter==0){
				body+=" <div class=\"w3-row-padding\">";
			}
			body+=generateCardCategory(map.getKey(),map.getValue());
			if (counter==2) {
				body+="</div>";
				counter=-1;
			}
			counter++;
		}
		return body;
	}
	
	private String generateCardCategory(String categoryname,Map<Card,Integer>category) {
		String body="<div class=\"svheader w3-third w3-container w3-margin-bottom\">\r\n" + 
				"<div class=svheadertext id=followerHeader><h5>"+categoryname+"</h5><hr>\r\n" + 
				"</div>";
		if(!category.isEmpty()) {
		for (Map.Entry<Card, Integer> entry:category.entrySet()) {
		body+=generateCardDiv(entry.getKey(),entry.getValue());
			}}
		else {
			body+="This deck contains no cards of this category";
		}
		body+="</div>";
		return body;
	}
	
	private String generateCardDiv(Card card,int count) {
		String body="<div class=cardcontainer>\r\n" + 
				"<div class=costcontainer>\r\n" + 
				"<img class=costicon src=https://shadowverse-portal.com/public/assets/image/common/global/cost_$cost.png></div>\r\n" + 
				"<div class=raritycontainer>\r\n" + 
				"<img class=rarityicon src=https://shadowverse-portal.com/public/assets/image/common/en/rarity_$rarity.png>\r\n" + 
				"</div>\r\n" + 
				"<div class=cardnamecontainer>\r\n" + 
				"$card_name\r\n" + 
				"</div>\r\n" +
				"<div class=amountcontainer>\r\n" + 
				"x$amount</div>"+
				"<img class=cardbanner src=https://shadowverse-portal.com/image/card/en/L_$card_id.jpg>\r\n" + 
				"</div>";
		body=body.replace("$cost", String.valueOf(card.getCost()));
		body=body.replace("$rarity", convertRarity(card.getRarity()));
		body=body.replace("$card_name", card.getCard_name());
		body=body.replace("$amount", String.valueOf(count));
		body=body.replace("$card_id", String.valueOf(card.getCard_id()));
		return body;
	}
	
	/*@GET
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
		
	}*/
	
	private ArrayList<Card> getDeckFromCode(String code) throws Exception{
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

	private String convertRarity(int i) {
		if (i==1) {
			return "bronze";
		}
		if (i==2) {
			return "silver";
		}
		if (i==3) {
			return "gold";
		}
		if (i==4) {
			return "legend";
		}return null;
	}
	private String getCSS(int i) {
		if (i==1) {
			return "forest";
		}
		if (i==2) {
			return "sword";
		}
		if (i==3) {
			return "rune";
		}
		if (i==4) {
			return "dragon";
		}
		if (i==5) {
			return "shadow";
		}
		if (i==6) {
			return "blood";
		}
		if (i==7) {
			return "haven";
		}
		if (i==8) {
			return "portal";
		}return null;
	}
}
