package sv.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sv.domain.Card;

public class CardDAO {

	public CardDAO() {}
	
	private Card convertCard(ResultSet rs) throws SQLException {
		Card card = new Card();
		card.setCard_id(rs.getInt("card_id"));
        card.setFoil_card_id(rs.getInt("foil_card_id"));
        card.setCard_set_id(rs.getInt("card_set_id"));
        card.setCard_name(rs.getString("card_name"));
        card.setIs_foil(rs.getInt("is_foil"));
        card.setChar_type(rs.getInt("char_type"));
        card.setClan(rs.getInt("clan")); 
        card.setTribe_name(rs.getString("tribe_name"));
        card.setSkill_disc(rs.getString("skill_disc"));
        card.setEvo_skill_disc(rs.getString("evo_skill_disc")); 
        card.setCost(rs.getInt("cost"));
        card.setAtk(rs.getInt("atk")); 
        card.setLife(rs.getInt("life")); 
        card.setEvo_atk(rs.getInt("evo_atk")); 
        card.setEvo_life(rs.getInt("evo_life")); 
        card.setRarity(rs.getInt("rarity")); 
        card.setGet_red_ether(rs.getInt("get_red_ether")); 
        card.setUse_red_ether(rs.getInt("use_red_ether")); 
        card.setDescription(rs.getString("description")); 
        card.setEvo_description(rs.getString("evo_description")); 
        card.setCv(null);
        card.setBase_card_id(rs.getInt("base_card_id")); 
        card.setTokens(rs.getString("tokens"));
        card.setNormal_card_id(rs.getInt("normal_card_id")); 
        card.setFormat_type(rs.getInt("format_type")); 
        card.setRestricted_count(rs.getInt("restricted_count"));
		return card;
	}
	public ArrayList<Card> getAllCards(){
		ArrayList<Card>cards=new ArrayList<Card>();
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("SELECT * FROM CARDS");
		ResultSet results=stmt.executeQuery();
		while(results.next()) {
			Card card=convertCard(results);
			cards.add(card);
		}
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return cards;
	}
	
	public ArrayList<Card> getAmuletCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		if(c.getChar_type()==3||c.getChar_type()==2) {
			cards.add(c);
		}}
		return cards;
	}
	
	public ArrayList<Card> getAoERemovalCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where ((skill_disc like '%destroy%' OR skill_disc like '%Destroy%') OR (skill_disc like '%Banish%' OR skill_disc like '%Banish%') OR skill_disc like '%damage to%') and skill_disc not like '%no enemy followers in play%' and (skill_disc like '%all enemies%' OR skill_disc like '%all followers%' OR skill_disc like '%all enemy followers%' OR (skill_disc like '%time%' AND skill_disc like '%enemy follower%'))");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where ((skill_disc like '%destroy%' OR skill_disc like '%Destroy%') OR (skill_disc like '%Banish%' OR skill_disc like '%Banish%') OR skill_disc like '%damage to%') and skill_disc not like '%no enemy followers in play%' and (skill_disc like '%all enemies%' OR skill_disc like '%all followers%' OR skill_disc like '%all enemy followers%' OR (skill_disc like '%time%' AND skill_disc like '%enemy follower%'))");
	    		stmt.setInt(1,Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getArtifactCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Artifact'");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Artifact'");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getBounceCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND clan=1 AND (((skill_disc like '%return%') and (skill_disc not like '% in return%') and skill_disc not like '%is returned%') or ((evo_skill_disc like '%return%') and (evo_skill_disc not like '% in return%') and clan=1 and evo_skill_disc not like '%is returned%'))");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND clan=1 AND (((skill_disc like '%return%') and (skill_disc not like '% in return%') and skill_disc not like '%is returned%') or ((evo_skill_disc like '%return%') and (evo_skill_disc not like '% in return%') and clan=1 and evo_skill_disc not like '%is returned%'))");
	    		stmt.setInt(1,Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getBurialRiteCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND ((skill_disc like '%Burial Rite: %' or skill_disc like '%Burial Rite -%') or (evo_skill_disc like '%Burial Rite: %' or evo_skill_disc like '%Burial Rite -%'))");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND ((skill_disc like '%Burial Rite: %' or skill_disc like '%Burial Rite -%') or (evo_skill_disc like '%Burial Rite: %' or evo_skill_disc like '%Burial Rite -%'))");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getBurnCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND (((evo_skill_disc like '%Storm%') OR (evo_skill_disc like '%damage to all enemies%' OR evo_skill_disc like '% damage to an enemy.%' or evo_skill_disc like '%damage to the enemy leader%')) OR ((skill_disc like '%Storm%') OR (skill_disc like '%damage to all enemies%' OR skill_disc like '% damage to an enemy.%' or skill_disc like '%damage to the enemy leader%')))");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	if (!items.contains(String.valueOf(c.getCard_id()))){
	    	for (String s:items) {
	    		System.out.println(c.getCard_id()+" - "+Integer.parseInt(s));
	    		System.out.println(Integer.parseInt(s)!=c.getCard_id());
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND (((evo_skill_disc like '%Storm%') OR (evo_skill_disc like '%damage to all enemies%' OR evo_skill_disc like '% damage to an enemy.%' or evo_skill_disc like '%damage to the enemy leader%')) OR ((skill_disc like '%Storm%') OR (skill_disc like '%damage to all enemies%' OR skill_disc like '% damage to an enemy.%' or skill_disc like '%damage to the enemy leader%')))");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    	if (hasApplicableTokens==true) {
				cards.add(c);
		    }}
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public Card getCardByID(int id){
		Card card=null;
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("SELECT * FROM CARDS WHERE CARD_ID=?");
		stmt.setInt(1, id);
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			card=convertCard(results);
		}
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return card;
	}
	
	public ArrayList<Card> getCardsFromDeck(ArrayList<Card>deck){
			ArrayList<Card>cards=new ArrayList<Card>();
			for (Card c:deck) {
			try(Connection conn=getConnection()){
			PreparedStatement stmt=conn.prepareStatement("SELECT * FROM CARDS WHERE CARD_ID=?");
			stmt.setInt(1, c.getCard_id());
			ResultSet results=stmt.executeQuery();
			while(results.next()) {
				cards.add(c);
			}
			stmt.close();
			conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}}
			return cards;
		}
	
	
	
	public ArrayList<Card> getCommanderCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Commander'");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Commander'");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	private Connection getConnection() {
		try {
			// Step 1: Allocate a database 'Connection' object
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/svcardstest", "lhuijgens",
					"lhuijgens");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
			return conn;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Card> getCountdownCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND char_type=3");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND char_type=3");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getWardCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND ((skill_disc like '%Ward. <br>%' or skill_disc like '%Gain Ward%' or skill_disc like '%Gains Ward%' or (skill_disc like '%Gain%' and (skill_disc like '%and Ward%' or skill_disc like '%or Ward%' or skill_disc like '%, Ward%')) or (skill_disc like '%Summon%' and skill_disc like '%Ward%' and skill_disc like '%Give%')or skill_disc like '%Ward.<br>%' or skill_disc like '%<br>Ward%' or skill_disc='Ward.') or  (evo_skill_disc like '%Ward. <br>%' or evo_skill_disc like '%Gain Ward%' or evo_skill_disc like '%Gains Ward%' or (evo_skill_disc like '%Gain%' and (evo_skill_disc like '%and Ward%' or evo_skill_disc like '%or Ward%' or evo_skill_disc like '%, Ward%')) or (evo_skill_disc like '%Summon%' and evo_skill_disc like '%Ward%' and evo_skill_disc like '%Give%')or evo_skill_disc like '%Ward.<br>%' or evo_skill_disc like '%<br>Ward%' or evo_skill_disc='Ward.'))");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND ((skill_disc like '%Ward. <br>%' or skill_disc like '%Gain Ward%' or skill_disc like '%Gains Ward%' or (skill_disc like '%Gain%' and (skill_disc like '%and Ward%' or skill_disc like '%or Ward%' or skill_disc like '%, Ward%')) or (skill_disc like '%Summon%' and skill_disc like '%Ward%' and skill_disc like '%Give%')or skill_disc like '%Ward.<br>%' or skill_disc like '%<br>Ward%' or skill_disc='Ward.') or  (evo_skill_disc like '%Ward. <br>%' or evo_skill_disc like '%Gain Ward%' or evo_skill_disc like '%Gains Ward%' or (evo_skill_disc like '%Gain%' and (evo_skill_disc like '%and Ward%' or evo_skill_disc like '%or Ward%' or evo_skill_disc like '%, Ward%')) or (evo_skill_disc like '%Summon%' and evo_skill_disc like '%Ward%' and evo_skill_disc like '%Give%')or evo_skill_disc like '%Ward.<br>%' or evo_skill_disc like '%<br>Ward%' or evo_skill_disc='Ward.'))");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getLastWordsCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND skill_disc like '%Last Words:%' or evo_skill_disc like '%Last Words:%'");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND skill_disc like '%Last Words:%' or evo_skill_disc like '%Last Words:%'");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getFanfareCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND skill_disc like '%Fanfare:%'");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND skill_disc like '%Fanfare:%'");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getEnhanceCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND skill_disc like '%Enhance (%'");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND skill_disc like '%Enhance (%'");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getAccelerateCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND skill_disc like '%Accelerate (%'");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND skill_disc like '%Accelerate (%'");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getPermanentCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND (skill_disc like '% lasts for the rest of the match%' or evo_skill_disc like '% lasts for the rest of the match%')");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND (skill_disc like '% lasts for the rest of the match%' or evo_skill_disc like '% lasts for the rest of the match%')");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getHealCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND (skill_disc like '%restore%' and skill_disc like '%defense to%') and card_name!=\"Tenko's Shrine\"");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND (skill_disc like '%restore%' and skill_disc like '%defense to%') and card_name!=\"Tenko's Shrine\"");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getRushCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND (skill_disc like '%gain Rush%' or skill_disc like '%or Rush%' or skill_disc like '%, Rush,%' or skill_disc like '%and Rush.%' or skill_disc like '%Rush.<br>%' or skill_disc like '%Rush. <br>%' or skill_disc='Rush.' or (skill_disc like '%give%' and skill_disc like '%Rush%' and (skill_disc like '%all allied%' or skill_disc like '%other%')) or (skill_disc like '%give%' and skill_disc like '%Rush%' and skill_disc like '%it%')) or (evo_skill_disc like '%gain Rush%' or evo_skill_disc like '%or Rush%' or evo_skill_disc like '%, Rush,%' or evo_skill_disc like '%and Rush.%' or evo_skill_disc like '%Rush.<br>%' or evo_skill_disc like '%Rush. <br>%' or evo_skill_disc='Rush.' or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and (evo_skill_disc like '%all allied%' or evo_skill_disc like '%other%')) or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and evo_skill_disc like '%it%'))");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND (skill_disc like '%gain Rush%' or skill_disc like '%or Rush%' or skill_disc like '%, Rush,%' or skill_disc like '%and Rush.%' or skill_disc like '%Rush.<br>%' or skill_disc like '%Rush. <br>%' or skill_disc='Rush.' or (skill_disc like '%give%' and skill_disc like '%Rush%' and (skill_disc like '%all allied%' or skill_disc like '%other%')) or (skill_disc like '%give%' and skill_disc like '%Rush%' and skill_disc like '%it%')) or (evo_skill_disc like '%gain Rush%' or evo_skill_disc like '%or Rush%' or evo_skill_disc like '%, Rush,%' or evo_skill_disc like '%and Rush.%' or evo_skill_disc like '%Rush.<br>%' or evo_skill_disc like '%Rush. <br>%' or evo_skill_disc='Rush.' or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and (evo_skill_disc like '%all allied%' or evo_skill_disc like '%other%')) or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and evo_skill_disc like '%it%'))");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getStormCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND  where (skill_disc like '%gain Storm%' or skill_disc='Storm.' or skill_disc like '%Storm. <br>%' or skill_disc like '%Storm.<br>%' or (skill_disc like '%gain%' and skill_disc like '%Storm%')) or (evo_skill_disc like '%gain Storm%' or evo_skill_disc='Storm.' or evo_skill_disc like '%Storm. <br>%' or evo_skill_disc like '%Storm.<br>%' or (evo_skill_disc like '%gain%' and evo_skill_disc like '%Storm%'))");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND select* from cards where (skill_disc like '%gain Rush%' or skill_disc like '%or Rush%' or skill_disc like '%, Rush,%' or skill_disc like '%and Rush.%' or skill_disc like '%Rush.<br>%' or skill_disc like '%Rush. <br>%' or skill_disc='Rush.' or (skill_disc like '%give%' and skill_disc like '%Rush%' and (skill_disc like '%all allied%' or skill_disc like '%other%')) or (skill_disc like '%give%' and skill_disc like '%Rush%' and skill_disc like '%it%')) or (evo_skill_disc like '%gain Rush%' or evo_skill_disc like '%or Rush%' or evo_skill_disc like '%, Rush,%' or evo_skill_disc like '%and Rush.%' or evo_skill_disc like '%Rush.<br>%' or evo_skill_disc like '%Rush. <br>%' or evo_skill_disc='Rush.' or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and (evo_skill_disc like '%all allied%' or evo_skill_disc like '%other%')) or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and evo_skill_disc like '%it%'))");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getDrawCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND (((skill_disc like '%draw%' or skill_disc like '%Draw%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%draw%' or evo_skill_disc like '%Draw%')) OR ((skill_disc like '%from your deck%' AND (skill_disc like '%into your hand%' or skill_disc like '%to your hand%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%to your hand%' or evo_skill_disc like '%into your hand%'))))");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		boolean isAdded=false;
		if(results.next()) {
			cards.add(c);
			isAdded=true;
		}
		
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND (((skill_disc like '%draw%' or skill_disc like '%Draw%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%draw%' or evo_skill_disc like '%Draw%')) OR ((skill_disc like '%from your deck%' AND (skill_disc like '%into your hand%' or skill_disc like '%to your hand%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%to your hand%' or evo_skill_disc like '%into your hand%'))))");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true&&isAdded==false) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getEarlyCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		if(c.getCost()<4) {
			cards.add(c);
		}}
		return cards;
	}
	public ArrayList<Card> getEarthSigilCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Earth Sigil'");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Earth Sigil'");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getFollowerCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		if(c.getChar_type()==1) {
			cards.add(c);
		}}
		return cards;
	}
	
	public ArrayList<Card> getFriendlyFireCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND (skill_disc like '%banish an allied%') or (skill_disc like '%damage to an allied%') or (skill_disc like '%destroy an allied%') OR (skill_disc like '%damage to a follower%') or ((skill_disc like '%damage to a follower%') AND (skill_disc like '%to that follower%'))");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND (skill_disc like '%banish an allied%') or (skill_disc like '%damage to an allied%') or (skill_disc like '%destroy an allied%') OR (skill_disc like '%damage to a follower%') or ((skill_disc like '%damage to a follower%') AND (skill_disc like '%to that follower%'))");
	    		stmt.setInt(1,Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getInvocationCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND (skill_disc like '%Invocation:%')");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND (skill_disc like '%Invocation:%')");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getLateCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		if(c.getCost()>6) {
			cards.add(c);
		}}
		return cards;
	}
	
	public ArrayList<Card> getLootCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Loot'");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Loot'");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getMidCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		if(c.getCost()>=4 && c.getCost()<=6) {
			cards.add(c);
		}}
		return cards;
	}
	
	//tribes
	public ArrayList<Card> getMysteriaCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Mysteria'");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Mysteria'");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getNecromancyCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND clan=7 AND (skill_disc like '%Countdown%' and skill_disc like '%Subtract%' and (skill_disc like '%allied amulet%' or skill_disc like '%that%')) or (evo_skill_disc like '%Countdown%' and evo_skill_disc like '%Subtract%' and (evo_skill_disc like '%allied amulet%' or evo_skill_disc like '%that%'))");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND select* from cards where (skill_disc like '%Necromancy (%' or evo_skill_disc like '%Necromancy (%')");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getOfficerCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Officer'");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Officer'");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getReanimateCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND (skill_disc like '%Reanimate (%' or evo_skill_disc like '%Reanimate (%' )");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND (skill_disc like '%Reanimate (%' or evo_skill_disc like '%Reanimate (%' )");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getRemovalCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND ((skill_disc like '%Bane%' and (skill_disc like '%Rush%' or skill_disc like '%Storm%')) or (skill_disc like '%destroy%' OR skill_disc like '%Destroy%') OR \r\n" + 
				"(skill_disc like '%Banish%' OR skill_disc like '%banish%') OR skill_disc like '%damage to%') and \r\n" + 
				"(((skill_disc like '%damage to your leader%' or skill_disc like 'to the enemy leader') AND (skill_disc like '%enemy follower%' OR skill_disc like '%all enemies%' OR skill_disc like '%all followers%' OR skill_disc like '%an enemy%')) or\r\n" + 
				"((skill_disc not like '%damage to your leader%' or skill_disc not like 'to the enemy leader') AND (skill_disc like '%enemy follower%' OR skill_disc like '%all enemies%' OR skill_disc like '%all followers%' OR skill_disc like '%an enemy%')))");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND ((skill_disc like '%Bane%' and (skill_disc like '%Rush%' or skill_disc like '%Storm%')) or (skill_disc like '%destroy%' OR skill_disc like '%Destroy%') OR \r\n" + 
	    				"(skill_disc like '%Banish%' OR skill_disc like '%banish%') OR skill_disc like '%damage to%') and \r\n" + 
	    				"(((skill_disc like '%damage to your leader%' or skill_disc like 'to the enemy leader') AND (skill_disc like '%enemy follower%' OR skill_disc like '%all enemies%' OR skill_disc like '%all followers%' OR skill_disc like '%an enemy%')) or\r\n" + 
	    				"((skill_disc not like '%damage to your leader%' or skill_disc not like 'to the enemy leader') AND (skill_disc like '%enemy follower%' OR skill_disc like '%all enemies%' OR skill_disc like '%all followers%' OR skill_disc like '%an enemy%')))");
	    		stmt.setInt(1,Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
	    	cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getSpellCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		if(c.getChar_type()==4) {
			cards.add(c);
		}}
		return cards;
	}
	
	public ArrayList<Card> getSubtractionCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
		PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND clan=7 AND (skill_disc like '%Countdown%' and skill_disc like '%Subtract%' and (skill_disc like '%allied amulet%' or skill_disc like '%that%')) or (evo_skill_disc like '%Countdown%' and evo_skill_disc like '%Subtract%' and (evo_skill_disc like '%allied amulet%' or evo_skill_disc like '%that%'))");
		stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		if(results.next()) {
			cards.add(c);
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND clan=7 AND (skill_disc like '%Countdown%' and skill_disc like '%Subtract%' and (skill_disc like '%allied amulet%' or skill_disc like '%that%')) or (evo_skill_disc like '%Countdown%' and evo_skill_disc like '%Subtract%' and (evo_skill_disc like '%allied amulet%' or evo_skill_disc like '%that%'))");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if(tokenresults.next() && hasApplicableTokens==false) {
	    			hasApplicableTokens=true;
	    		}
	    	}
	    }
	    if (hasApplicableTokens==true) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getBoardBuildingCardsFromDeck(ArrayList<Card>deck){
		ArrayList<Card>cards=new ArrayList<Card>();
		for (Card c:deck) {
		try(Connection conn=getConnection()){
			PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND ((skill_disc like '%Summon%' or skill_disc like '%summon%') OR (evo_skill_disc like '%Summon%' or evo_skill_disc like '%summon%'))");
			stmt.setInt(1, c.getCard_id());
		ResultSet results=stmt.executeQuery();
		boolean doesItSummon=false;
		boolean isAdded=false;
		if (c.getChar_type()==1) {
			cards.add(c);
			isAdded=true;
		}else
		if(results.next()) {
			doesItSummon=true;
		}
		boolean hasApplicableTokens=false;
	    if(c.getTokens()!=null&&doesItSummon==true) {
	    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
	    	for (String s:items) {
	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND ((skill_disc like '%Summon%' or skill_disc like '%summon%') OR (evo_skill_disc like '%Summon%' or evo_skill_disc like '%summon%'))");
	    		stmt.setInt(1, Integer.parseInt(s));
	    		ResultSet tokenresults=stmt.executeQuery();
	    		if((tokenresults.next())) {
	    			List<String> tokenitems = Arrays.asList(tokenresults.getString("tokens").split("\\s*,\\s*"));
	    	    	for (String token_s:tokenitems) {
	    	    		stmt=conn.prepareStatement("select* from cards where card_id=? AND char_type=1");
	    	    		stmt.setInt(1, Integer.parseInt(token_s));
	    	    		ResultSet tokentokenresults=stmt.executeQuery();
	    	    		if(tokentokenresults.next() && hasApplicableTokens==false) {
	    	    			hasApplicableTokens=true;
	    	    		}
	    		}
	    	}
	    }
	    }
	    if (hasApplicableTokens==true && isAdded==false) {
			cards.add(c);
	    }
		stmt.close();
		conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}}
		return cards;
	}
	
	public ArrayList<Card> getSummonerCardsFromDeck(ArrayList<Card>deck){
	ArrayList<Card>cards=new ArrayList<Card>();
	for (Card c:deck) {
	try(Connection conn=getConnection()){
	PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND ((skill_disc like '%Summon%' or skill_disc like '%summon%') OR (evo_skill_disc like '%Summon%' or evo_skill_disc like '%summon%'))");
	stmt.setInt(1, c.getCard_id());
	ResultSet results=stmt.executeQuery();
	if(results.next()) {
		cards.add(c);
	}
	boolean hasApplicableTokens=false;
    if(c.getTokens()!=null) {
    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
    	if (!items.contains(String.valueOf(c.getCard_id()))){
    	for (String s:items) {
    		if (Integer.parseInt(s)!=c.getCard_id()) {
    		stmt=conn.prepareStatement("select* from cards where card_id=? AND ((skill_disc like '%Summon%' or skill_disc like '%summon%') OR (evo_skill_disc like '%Summon%' or evo_skill_disc like '%summon%'))");
    		stmt.setInt(1, Integer.parseInt(s));
    		ResultSet tokenresults=stmt.executeQuery();
    		if(tokenresults.next() && hasApplicableTokens==false) {
    			hasApplicableTokens=true;
    		}
    	}}
    }
    	if (hasApplicableTokens==true) {
			cards.add(c);
	    }}
	stmt.close();
	conn.close();
	}catch(SQLException e) {
		e.printStackTrace();
	}}
	return cards;
}
	
	public ArrayList<Card> getTutorCardsFromDeck(ArrayList<Card>deck){
	ArrayList<Card>cards=new ArrayList<Card>();
	for (Card c:deck) {
	try(Connection conn=getConnection()){
	PreparedStatement stmt=conn.prepareStatement("select* from cards where card_id=? AND ((skill_disc like '%from your deck%' AND (skill_disc like '%into your hand%' or skill_disc like '%to your hand%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%to your hand%' or evo_skill_disc like '%into your hand%')))");
	stmt.setInt(1, c.getCard_id());
	ResultSet results=stmt.executeQuery();
	if(results.next()) {
		cards.add(c);
	}
	boolean hasApplicableTokens=false;
    if(c.getTokens()!=null) {
    	List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
    	for (String s:items) {
    		stmt=conn.prepareStatement("select* from cards where card_id=? AND ((skill_disc like '%from your deck%' AND (skill_disc like '%into your hand%' or skill_disc like '%to your hand%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%to your hand%' or evo_skill_disc like '%into your hand%')))");
    		stmt.setInt(1, Integer.parseInt(s));
    		ResultSet tokenresults=stmt.executeQuery();
    		if(tokenresults.next() && hasApplicableTokens==false) {
    			hasApplicableTokens=true;
    		}
    	}
    }
    if (hasApplicableTokens==true) {
		cards.add(c);
    }
	stmt.close();
	conn.close();
	}catch(SQLException e) {
		e.printStackTrace();
	}}
	return cards;
}
}
