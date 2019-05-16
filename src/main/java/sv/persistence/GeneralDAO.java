package sv.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import sv.domain.Card;

public class GeneralDAO extends BaseDAO {
	private static GeneralDAO instance;
	private GeneralDAO() {}
	
	public static GeneralDAO getInstance() {
		if (instance==null) {
			instance=new GeneralDAO();
		}
		return instance;
	}

	private Card convertCard(ResultSet rs) throws SQLException {
		Card card = new Card();
		card.setCardID(rs.getInt("card_id"));
		card.setFoilCardID(rs.getInt("foil_card_id"));
		card.setCardSetID(rs.getInt("card_set_id"));
		card.setCardName(rs.getString("card_name"));
		card.setIsFoil(rs.getInt("is_foil"));
		card.setCharType(rs.getInt("char_type"));
		card.setClan(rs.getInt("clan"));
		card.setTribeName(rs.getString("tribe_name"));
		card.setSkillDisc(rs.getString("skill_disc"));
		card.setEvoSkillDisc(rs.getString("evo_skill_disc"));
		card.setCost(rs.getInt("cost"));
		card.setAtk(rs.getInt("atk"));
		card.setLife(rs.getInt("life"));
		card.setEvoAtk(rs.getInt("evo_atk"));
		card.setEvoLife(rs.getInt("evo_life"));
		card.setRarity(rs.getInt("rarity"));
		card.setGetRedEther(rs.getInt("get_red_ether"));
		card.setUseRedEther(rs.getInt("use_red_ether"));
		card.setDescription(rs.getString("description"));
		card.setEvoDescription(rs.getString("evo_description"));
		card.setCv(null);
		card.setBaseCardID(rs.getInt("base_card_id"));
		card.setTokens(rs.getString("tokens"));
		card.setNormalCardID(rs.getInt("normal_card_id"));
		card.setFormatType(rs.getInt("format_type"));
		card.setRestrictedCount(rs.getInt("restricted_count"));
		return card;
	}

	public Map<Card, Integer> getAllCards() {
		Map<Card, Integer> cards = new LinkedHashMap<>();
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CARDS")) {
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				Card card = convertCard(results);
				super.checkAlreadyPresent(cards, card);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cards;
	}

	public Card getCardByID(int id) {
		Card card = null;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CARDS WHERE CARD_ID=?")) {
			stmt.setInt(1, id);
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				card = convertCard(results);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return card;
	}

	public Map<Card, Integer> getCardsFromDeck(List<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<>();
		for (Card c : deck) {
			try (Connection conn = getConnection();
					PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CARDS WHERE CARD_ID=?")) {
				stmt.setInt(1, c.getCardID());
				ResultSet results = stmt.executeQuery();
				while (results.next()) {
					Card card = convertCard(results);
					super.checkAlreadyPresent(cards, card);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cards;
	}

	public Map<Card, Integer> getClassCardsFromDeck(List<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<>();
		for (Card c : deck) {
			if (c.getClan() != 0) {
				super.checkAlreadyPresent(cards,c);
			}
		}
		return cards;
	}

	public Map<Card, Integer> getNeutralCardsFromDeck(List<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<>();
		for (Card c : deck) {
			if (c.getClan() == 0) {
				super.checkAlreadyPresent(cards, c);
			}
		}
		return cards;
	}

	public Map<Card, Integer> getEarlyCardsFromDeck(List<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<>();
		for (Card c : deck) {
			if (c.getCost() < 4) {
				super.checkAlreadyPresent(cards, c);
			}
		}
		return cards;
	}

	public Map<Card, Integer> getMidCardsFromDeck(List<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<>();
		for (Card c : deck) {
			if (c.getCost() >= 4 && c.getCost() <= 6) {
				super.checkAlreadyPresent(cards, c);
			}
		}
		return cards;
	}

	public Map<Card, Integer> getLateCardsFromDeck(List<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<>();
		for (Card c : deck) {
			if (c.getCost() > 6) {
				super.checkAlreadyPresent(cards, c);
			}
		}
		return cards;
	}
}
