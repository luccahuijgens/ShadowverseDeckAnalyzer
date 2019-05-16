package sv.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import sv.domain.Card;

public class MechanicDAO extends BaseDAO {
	private String removalExceptions = "110521020";
	private String aoeRemovalExceptions = "110641010";
	private String healExceptions = "108732010";
	private static MechanicDAO instance;

	private MechanicDAO() {
	}

	public static MechanicDAO getInstance() {
		if (instance == null) {
			instance = new MechanicDAO();
		}
		return instance;
	}

	public Map<Card, Integer> getFriendlyFireCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND ((skill_disc ilike '%banish an allied%') or (skill_disc ilike '%damage to an allied%') or (skill_disc ilike '%destroy an allied%') OR (skill_disc ilike '%damage to a follower%') or ((skill_disc ilike '%damage to a follower%') AND (skill_disc ilike '%to that follower%')))",
				deck);
	}

	public Map<Card, Integer> getRemovalCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND card_id not in (" + removalExceptions
				+ ") AND (((skill_disc ilike '%gain Rush%' or (skill_disc ilike '%gain%' AND (skill_disc ilike '%or Rush%' or skill_disc ilike '%, Rush,%' or skill_disc ilike '%and Rush.%')) or skill_disc ilike '%Rush.<br>%' or skill_disc ilike '%Rush. <br>%' or skill_disc='Rush.' or (skill_disc ilike '%give%' and skill_disc ilike '%Rush%' and (skill_disc ilike '%all allied%' or skill_disc ilike '%other%')) or (skill_disc ilike '%give%' and skill_disc ilike '%Rush%' and skill_disc ilike '%it%')) or (evo_skill_disc ilike '%gain Rush%' or (evo_skill_disc ilike '%gain%' AND (evo_skill_disc ilike '%or Rush%' or evo_skill_disc ilike '%, Rush,%' or evo_skill_disc ilike '%and Rush.%')) or evo_skill_disc ilike '%Rush.<br>%' or evo_skill_disc ilike '%Rush. <br>%' or evo_skill_disc='Rush.' or (evo_skill_disc ilike '%give%' and evo_skill_disc ilike '%Rush%' and (evo_skill_disc ilike '%all allied%' or evo_skill_disc ilike '%other%')) or (evo_skill_disc ilike '%give%' and evo_skill_disc ilike '%Rush%' and evo_skill_disc ilike '%it%'))) OR ((skill_disc ilike '%gain Storm%' or skill_disc='Storm.' or skill_disc ilike '%Storm. <br>%' or skill_disc ilike '%Storm.<br>%' or (skill_disc ilike '%gain%' and skill_disc ilike '%Storm%')) or (evo_skill_disc ilike '%gain Storm%' or evo_skill_disc='Storm.' or evo_skill_disc ilike '%Storm. <br>%' or evo_skill_disc ilike '%Storm.<br>%' or (evo_skill_disc ilike '%gain%' and evo_skill_disc ilike '%Storm%'))) OR (((skill_disc ilike '%Bane%' and (skill_disc ilike '%Rush%' or skill_disc ilike '%Storm%')) or (skill_disc ilike '%destroy%' OR skill_disc ilike '%Destroy%') OR\r\n"
				+ "(skill_disc ilike '%Banish%' OR skill_disc ilike '%banish%') OR skill_disc ilike '%damage to%') and\r\n"
				+ "(((skill_disc ilike '%damage to your leader%' or skill_disc like 'to the enemy leader') AND (skill_disc ilike '%enemy follower%' OR skill_disc ilike '%all enemies%' OR skill_disc ilike '%all followers%' OR skill_disc ilike '%an enemy%')) or\r\n"
				+ "((skill_disc not ilike '%damage to your leader%' or skill_disc not like 'to the enemy leader') AND (skill_disc ilike '%enemy follower%' OR skill_disc ilike '%all enemies%' OR skill_disc ilike '%all followers%' OR skill_disc ilike '%an enemy%')))))",
				deck);
	}

	public Map<Card, Integer> getSubtractionCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND clan=7 AND ((skill_disc ilike '%Countdown%' and skill_disc ilike '%Subtract%' and (skill_disc ilike '%allied amulet%' or skill_disc ilike '%that%')) or (evo_skill_disc ilike '%Countdown%' and evo_skill_disc ilike '%Subtract%' and (evo_skill_disc ilike '%allied amulet%' or evo_skill_disc ilike '%that%')))",
				deck);
	}

	public Map<Card, Integer> getRampCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (skill_disc ilike '%gain an empty play point%' or evo_skill_disc ilike '%gain an empty play point%')",
				deck);
	}

	public Map<Card, Integer> getSummonerCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND ((skill_disc ilike '%Summon%' or skill_disc ilike '%summon%') OR (evo_skill_disc ilike '%Summon%' or evo_skill_disc ilike '%summon%') OR (skill_disc ilike '%Reanimate (%' or evo_skill_disc ilike '%Reanimate (%' ))",
				deck);
	}

	public Map<Card, Integer> getTutorCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND ((skill_disc ilike '%from your deck%' AND (skill_disc ilike '%into your hand%' or skill_disc ilike '%to your hand%')) OR (evo_skill_disc ilike '%from your deck%' AND (evo_skill_disc ilike '%to your hand%' or evo_skill_disc ilike '%into your hand%')))",
				deck);
	}

	public Map<Card, Integer> getAoERemovalCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND card_id not in ("
				+ aoeRemovalExceptions
				+ ") AND ((skill_disc ilike '%destroy%' OR skill_disc ilike '%Destroy%') OR (skill_disc ilike '%Banish%' OR skill_disc ilike '%Banish%') OR skill_disc ilike '%damage to%') and skill_disc not ilike '%no enemy followers in play%' and (skill_disc ilike '%all enemies%' OR skill_disc ilike '%all followers%' OR skill_disc ilike '%all enemy followers%' OR (skill_disc ilike '%time%' AND skill_disc ilike '%enemy follower%'))",
				deck);
	}

	public Map<Card, Integer> getBoardBuildingCardsFromDeck(List<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (((skill_disc ilike '%Summon%' or skill_disc ilike '%summon%') OR (evo_skill_disc ilike '%Summon%' or evo_skill_disc ilike '%summon%')) OR tokens is not null OR (skill_disc ilike '%Reanimate (%' or evo_skill_disc ilike '%Reanimate (%' ))");
				stmt.setInt(1, c.getCardID());
				ResultSet results = stmt.executeQuery();
				boolean doesItSummon = false;
				boolean isAdded = false;
				if (c.getCharType() == 1) {
					super.checkAlreadyPresent(cards, c);
					isAdded = true;
				} else if (results.next()) {
					doesItSummon = true;
				}
				boolean hasApplicableTokens = false;
				if (c.getTokens() != null && doesItSummon == true) {
					List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
					for (String s : items) {
						stmt = conn.prepareStatement("select* from cards where card_id=? AND char_type=1");
						stmt.setInt(1, Integer.parseInt(s));
						ResultSet summonresults = stmt.executeQuery();
						if (!summonresults.next()) {
							stmt = conn.prepareStatement(
									"select* from cards where card_id=? and ((skill_disc ilike '%Summon%' or skill_disc ilike '%summon%') OR (evo_skill_disc ilike '%Summon%' or evo_skill_disc ilike '%summon%') OR (skill_disc ilike '%Reanimate (%' or evo_skill_disc ilike '%Reanimate (%' ))");
							stmt.setInt(1, Integer.parseInt(s));
							ResultSet tokenresults = stmt.executeQuery();
							if ((tokenresults.next()) && tokenresults.getString("tokens") != null) {
								List<String> tokenitems = Arrays
										.asList(tokenresults.getString("tokens").split("\\s*,\\s*"));
								for (String token_s : tokenitems) {

									stmt = conn.prepareStatement("select* from cards where card_id=? AND char_type=1");
									stmt.setInt(1, Integer.parseInt(token_s));
									ResultSet tokentokenresults = stmt.executeQuery();
									if (tokentokenresults.next() && !hasApplicableTokens) {
										hasApplicableTokens = true;
									}
								}
							}
						} else if (!hasApplicableTokens) {
							hasApplicableTokens = true;
						}
					}
				}
				if (hasApplicableTokens && !isAdded) {
					super.checkAlreadyPresent(cards, c);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cards;
	}

	public Map<Card, Integer> getBounceCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND clan=1 AND (((skill_disc ilike '%return%') and (skill_disc not ilike '% in return%') and skill_disc not ilike '%is returned%') or ((evo_skill_disc ilike '%return%') and (evo_skill_disc not ilike '% in return%') and clan=1 and evo_skill_disc not ilike '%is returned%'))",
				deck);
	}

	public Map<Card, Integer> getBuffCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND ((skill_disc ilike '%give%' and skill_disc ilike '%/%' and (skill_disc ilike '%in your hand%' or (skill_disc ilike '%a follower%' or skill_disc ilike '%allied%'or skill_disc ilike '%other%'))) or (evo_skill_disc ilike '%give%' and evo_skill_disc ilike '%/%' and (evo_skill_disc ilike '%in your hand%' or (evo_skill_disc ilike '%a follower%' or evo_skill_disc ilike '%allied%'or evo_skill_disc ilike '%other%'))))",
				deck);
	}

	public Map<Card, Integer> getBurnCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (((evo_skill_disc ilike '%Storm%') OR (evo_skill_disc ilike '%damage to all enemies%' OR evo_skill_disc ilike '% damage to an enemy.%' or evo_skill_disc ilike '%damage to the enemy leader%')) OR ((skill_disc ilike '%Storm%') OR (skill_disc ilike '%damage to all enemies%' OR skill_disc ilike '% damage to an enemy.%' or skill_disc ilike '%damage to the enemy leader%')))",
				deck);
	}

	public Map<Card, Integer> getUntargatableCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND ((skill_disc ilike '%can''t be targeted by %' or skill_disc ilike '%gain resistance to targeted%') or (evo_skill_disc ilike '%can''t be targeted by %' or evo_skill_disc ilike '%gain resistance to targeted%'))",
				deck);
	}

	public Map<Card, Integer> getUndestroyableCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (skill_disc ilike '%Can''t be destroyed by spells and effects%' or evo_skill_disc ilike '%Can''t be destroyed by spells and effects%')",
				deck);
	}

	public Map<Card, Integer> getUndamageableCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (skill_disc ilike '%Reduce damage from spells and effects%' or evo_skill_disc ilike '%Reduce damage from spells and effects%')",
				deck);
	}

	public Map<Card, Integer> getEvoPointCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (skill_disc ilike '%evolution point%' or evo_skill_disc ilike '%evolution point%')",
				deck);
	}

	public Map<Card, Integer> getResourceCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (((skill_disc ilike '%put%' and skill_disc ilike '%your hand%' and skill_disc not ilike '%from your deck%') or (skill_disc ilike '%put%' and (skill_disc ilike '%into your deck%' OR skill_disc ilike '%in your deck%')) OR (evo_skill_disc ilike '%put%' and evo_skill_disc ilike '%your hand%' and evo_skill_disc not ilike '%from your deck%') or (skill_disc ilike '%put%' and (evo_skill_disc ilike '%into your deck%' OR evo_skill_disc ilike '%in your deck%'))) OR (((skill_disc ilike '%draw%' or skill_disc ilike '%Draw%')) OR (evo_skill_disc ilike '%from your deck%' AND (evo_skill_disc ilike '%draw%' or evo_skill_disc ilike '%Draw%')) OR ((skill_disc ilike '%from your deck%' AND (skill_disc ilike '%into your hand%' or skill_disc ilike '%to your hand%')) OR (evo_skill_disc ilike '%from your deck%' AND (evo_skill_disc ilike '%to your hand%' or evo_skill_disc ilike '%into your hand%')))))",
				deck);
	}

	public Map<Card, Integer> getGenerationCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND ((skill_disc ilike '%put%' and skill_disc ilike '%your hand%' and skill_disc not ilike '%from your deck%') or (skill_disc ilike '%put%' and (skill_disc ilike '%into your deck%' OR skill_disc ilike '%in your deck%')) OR (evo_skill_disc ilike '%put%' and evo_skill_disc ilike '%your hand%' and evo_skill_disc not ilike '%from your deck%') or (skill_disc ilike '%put%' and (evo_skill_disc ilike '%into your deck%' OR evo_skill_disc ilike '%in your deck%')))",
				deck);
	}

	public Map<Card, Integer> getPermanentCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (skill_disc ilike '% lasts for the rest of the match%' or evo_skill_disc ilike '% lasts for the rest of the match%')",
				deck);
	}

	public Map<Card, Integer> getHealCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND card_id not in (" + healExceptions
				+ ") AND (skill_disc ilike '%Drain%' or evo_skill_disc ilike '%drain%' or skill_disc ilike '%restore%' and skill_disc ilike '%defense to%' or evo_skill_disc ilike '%restore%' and evo_skill_disc ilike '%defense to%')",
				deck);
	}

	public Map<Card, Integer> getDrawCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (((skill_disc ilike '%draw%' or skill_disc ilike '%Draw%')) OR (evo_skill_disc ilike '%from your deck%' AND (evo_skill_disc ilike '%draw%' or evo_skill_disc ilike '%Draw%')) OR ((skill_disc ilike '%from your deck%' AND (skill_disc ilike '%into your hand%' or skill_disc ilike '%to your hand%')) OR (evo_skill_disc ilike '%from your deck%' AND (evo_skill_disc ilike '%to your hand%' or evo_skill_disc ilike '%into your hand%'))))",
				deck);
	}

	public Map<Card, Integer> getDamageLimiterCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (skill_disc ilike '%can''t take more than%' or evo_skill_disc ilike '%can''t take more than%')",
				deck);
	}

	public Map<Card, Integer> getUnattackableCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (skill_disc ilike '%can''t be attacked%' or evo_skill_disc ilike '%can''t be attacked%')",
				deck);
	}
}
