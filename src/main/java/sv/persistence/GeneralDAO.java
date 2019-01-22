package sv.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import sv.domain.Card;

public class GeneralDAO extends BaseDAO{

	public GeneralDAO() {}
	
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
	
	public Map<Card, Integer> getAllCards() {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		try (Connection conn = getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CARDS");
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				Card card = convertCard(results);
				boolean isPresent = false;
				for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
					if (entry.getKey().getCard_id() == card.getCard_id()) {
						cards.put(entry.getKey(), entry.getValue() + 1);
						isPresent = true;
					}
				}
				if (isPresent == false) {
					cards.put(card, 1);
				}
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cards;
	}
	
	public Card getCardByID(int id) {
		Card card = null;
		try (Connection conn = getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CARDS WHERE CARD_ID=?");
			stmt.setInt(1, id);
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				card = convertCard(results);
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return card;
	}

	public Map<Card, Integer> getCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CARDS WHERE CARD_ID=?");
				stmt.setInt(1, c.getCard_id());
				ResultSet results = stmt.executeQuery();
				while (results.next()) {
					Card card = convertCard(results);
					boolean isPresent = false;
					for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
						if (entry.getKey().getCard_id() == card.getCard_id()) {
							cards.put(entry.getKey(), entry.getValue() + 1);
							isPresent = true;
						}
					}
					if (isPresent == false) {
						cards.put(card, 1);
					}
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cards;
	}
	
	public Map<Card, Integer> getClassCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			if (c.getClan() != 0) {
				boolean isPresent = false;
				for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
					if (entry.getKey().getCard_id() == c.getCard_id()) {
						cards.put(entry.getKey(), entry.getValue() + 1);
						isPresent = true;
					}
				}
				if (isPresent == false) {
					cards.put(c, 1);
				}
			}
		}
		return cards;
	}

	public Map<Card, Integer> getNeutralCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			if (c.getClan() == 0) {
				boolean isPresent = false;
				for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
					if (entry.getKey().getCard_id() == c.getCard_id()) {
						cards.put(entry.getKey(), entry.getValue() + 1);
						isPresent = true;
					}
				}
				if (isPresent == false) {
					cards.put(c, 1);
				}
			}
		}
		return cards;
	}
	
	public Map<Card, Integer> getEarlyCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			if (c.getCost() < 4) {
				boolean isPresent = false;
				for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
					if (entry.getKey().getCard_id() == c.getCard_id()) {
						cards.put(entry.getKey(), entry.getValue() + 1);
						isPresent = true;
					}
				}
				if (isPresent == false) {
					cards.put(c, 1);
				}
			}
		}
		return cards;
	}
	
	public Map<Card, Integer> getMidCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			if (c.getCost() >= 4 && c.getCost() <= 6) {
				boolean isPresent = false;
				for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
					if (entry.getKey().getCard_id() == c.getCard_id()) {
						cards.put(entry.getKey(), entry.getValue() + 1);
						isPresent = true;
					}
				}
				if (isPresent == false) {
					cards.put(c, 1);
				}
			}
		}
		return cards;
	}
	
	public Map<Card, Integer> getLateCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			if (c.getCost() > 6) {
				boolean isPresent = false;
				for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
					if (entry.getKey().getCard_id() == c.getCard_id()) {
						cards.put(entry.getKey(), entry.getValue() + 1);
						isPresent = true;
					}
				}
				if (isPresent == false) {
					cards.put(c, 1);
				}
			}
		}
		return cards;
	}
}
