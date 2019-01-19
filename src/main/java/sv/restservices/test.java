package sv.restservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import sv.domain.Card;
import sv.persistence.CardService;
import sv.persistence.CardServiceProvider;

public class test {
	public static void main(String[] args) {
		CardService service=CardServiceProvider.getCardService();
		String code="c6ad";
		JSONObject data=readJsonFromUrl("https://shadowverse-portal.com/api/v1/deck/import?format=json&deck_code="+code).getJSONObject("data");
		String deckhash=data.getString("hash");
		
		JSONObject deckdata=readJsonFromUrl("https://shadowverse-portal.com/api/v1/deck?format=json&lang=EN&hash="+deckhash).getJSONObject("data").getJSONObject("deck");
		JSONArray cards=deckdata.getJSONArray("cards");

		ArrayList<Card>deck=new ArrayList<Card>();
		for (int i = 0; i < cards.length(); ++i) {
	        JSONObject obj = cards.getJSONObject(i);
	        deck.add(service.getCardByID(obj.getInt("card_id")));
		}
		for (Card c:service.getBoardBuildingCardsFromDeck(deck)) {
			System.out.println(c.getCard_name());
		}
	}
	private static JSONObject readJsonFromUrl(String url) {
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

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
}
