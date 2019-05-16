package sv.persistence;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import sv.domain.Card;

public class KeywordDAO extends BaseDAO {
	private static KeywordDAO instance;
	private String rushExceptions = "110521020";
	private String stormExceptions = "110521020";

	private KeywordDAO() {
	}

	public static KeywordDAO getInstance() {
		if (instance == null) {
			instance = new KeywordDAO();
		}
		return instance;
	}

	public Map<Card, Integer> getAmuletCardsFromDeck(List<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<>();
		for (Card c : deck) {
			if (c.getCharType() == 3 || c.getCharType() == 2) {
				super.checkAlreadyPresent(cards, c);
			}
		}
		return cards;
	}

	public Map<Card, Integer> getBurialRiteCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND ((skill_disc ilike '%Burial Rite: %' or skill_disc ilike '%Burial Rite -%') or (evo_skill_disc ilike '%Burial Rite: %' or evo_skill_disc ilike '%Burial Rite -%'))",
				deck);
	}

	public Map<Card, Integer> getCountdownCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND char_type=3", deck);
	}

	public Map<Card, Integer> getChooseCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (skill_disc ilike '%Choose%' or evo_skill_disc ilike '%Choose%')",
				deck);
	}

	public Map<Card, Integer> getClashCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (skill_disc ilike '%Choose%' or evo_skill_disc ilike '%Choose%')",
				deck);
	}

	public Map<Card, Integer> getBaneCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select * from cards where card_id=? and ((skill_disc ilike '%Summon%' and skill_disc ilike '%Bane%') or (skill_disc ilike '%Bane.<br>%' or skill_disc='Bane.' or skill_disc ilike '%Bane. <br>%' or skill_disc ilike '%<br>Bane.%' or skill_disc ilike '%Gain Bane%' or (skill_disc ilike '%Gain%' and skill_disc ilike '%Bane%')) or (evo_skill_disc ilike '%Bane.<br>%' or evo_skill_disc='Bane.' or evo_skill_disc ilike '%Bane. <br>%' or evo_skill_disc ilike '%<br>Bane.%' or evo_skill_disc ilike '%Gain Bane%' or (evo_skill_disc ilike '%Summon%' and evo_skill_disc ilike '%Bane%') or (evo_skill_disc ilike '%Gain%' and evo_skill_disc ilike '%Bane%')))",
				deck);
	}

	public Map<Card, Integer> getEvolveCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND evo_skill_disc ilike '%Evolve:%'",
				deck);
	}

	public Map<Card, Integer> getAmbushCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (skill_disc ilike '%Ambush. <br>%' or skill_disc='Ambush.' or skill_disc ilike '%Ambush.<br>%' or skill_disc ilike '%<br>Ambush%' or (skill_disc ilike '%gain%' and skill_disc ilike '%ambush%'))",
				deck);
	}

	public Map<Card, Integer> getWardCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (skill_disc not ilike '%Can only attack the enemy leader and followers with Ward%' or evo_skill_disc not ilike '%Can only attack the enemy leader and followers with Ward%') AND ((skill_disc ilike '%Ward. <br>%' or skill_disc ilike '%Gain Ward%' or skill_disc ilike '%Gains Ward%' or (skill_disc ilike '%Gain%' and (skill_disc ilike '%and Ward%' or skill_disc ilike '%or Ward%' or skill_disc ilike '%, Ward%')) or (skill_disc ilike '%Summon%' and skill_disc ilike '%Ward%' and skill_disc ilike '%Give%')or skill_disc ilike '%Ward.<br>%' or skill_disc ilike '%<br>Ward%' or skill_disc='Ward.') or (evo_skill_disc ilike '%Ward. <br>%' or evo_skill_disc ilike '%Gain Ward%' or evo_skill_disc ilike '%Gains Ward%' or (evo_skill_disc ilike '%Gain%' and (evo_skill_disc ilike '%and Ward%' or evo_skill_disc ilike '%or Ward%' or evo_skill_disc ilike '%, Ward%')) or (evo_skill_disc ilike '%Summon%' and evo_skill_disc ilike '%Ward%' and evo_skill_disc ilike '%Give%')or evo_skill_disc ilike '%Ward.<br>%' or evo_skill_disc ilike '%<br>Ward%' or evo_skill_disc='Ward.'))",
				deck);
	}

	public Map<Card, Integer> getLastWordsCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (skill_disc ilike '%Last Words:%' or evo_skill_disc ilike '%Last Words:%')",
				deck);
	}

	public Map<Card, Integer> getFanfareCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND skill_disc ilike '%Fanfare:%'",
				deck);
	}

	public Map<Card, Integer> getEnhanceCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND skill_disc ilike '%Enhance (%'",
				deck);
	}

	public Map<Card, Integer> getAccelerateCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND skill_disc ilike '%Accelerate (%'",
				deck);
	}

	public Map<Card, Integer> getRushCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND card_id not in (" + rushExceptions
				+ ") AND ((skill_disc ilike '%gain Rush%' or skill_disc ilike '%or Rush%' or skill_disc ilike '%, Rush,%' or skill_disc ilike '%and Rush.%' or skill_disc ilike '%Rush.<br>%' or skill_disc ilike '%Rush. <br>%' or skill_disc='Rush.' or (skill_disc ilike '%give%' and skill_disc ilike '%Rush%' and (skill_disc ilike '%all allied%' or skill_disc ilike '%other%')) or (skill_disc ilike '%give%' and skill_disc ilike '%Rush%' and skill_disc ilike '%it%')) or (evo_skill_disc ilike '%gain Rush%' or evo_skill_disc ilike '%or Rush%' or evo_skill_disc ilike '%, Rush,%' or evo_skill_disc ilike '%and Rush.%' or evo_skill_disc ilike '%Rush.<br>%' or evo_skill_disc ilike '%Rush. <br>%' or evo_skill_disc='Rush.' or (evo_skill_disc ilike '%give%' and evo_skill_disc ilike '%Rush%' and (evo_skill_disc ilike '%all allied%' or evo_skill_disc ilike '%other%')) or (evo_skill_disc ilike '%give%' and evo_skill_disc ilike '%Rush%' and evo_skill_disc ilike '%it%')))",
				deck);
	}

	public Map<Card, Integer> getStormCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND card_id not in (" + stormExceptions
				+ ") AND ((skill_disc ilike '%gain Storm%' or skill_disc='Storm.' or skill_disc ilike '%Storm. <br>%' or skill_disc ilike '%Storm.<br>%' or (skill_disc ilike '%gain%' and skill_disc ilike '%Storm%')) or (evo_skill_disc ilike '%gain Storm%' or evo_skill_disc='Storm.' or evo_skill_disc ilike '%Storm. <br>%' or evo_skill_disc ilike '%Storm.<br>%' or (evo_skill_disc ilike '%gain%' and evo_skill_disc ilike '%Storm%')))",
				deck);
	}

	public Map<Card, Integer> getFollowerCardsFromDeck(List<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<>();
		for (Card c : deck) {
			if (c.getCharType() == 1) {
				super.checkAlreadyPresent(cards, c);
			}
		}
		return cards;
	}

	public Map<Card, Integer> getInvocationCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND (skill_disc ilike '%Invocation:%')",
				deck);
	}

	// tribes

	public Map<Card, Integer> getNecromancyCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (skill_disc ilike '%Necromancy (%' or evo_skill_disc ilike '%Necromancy (%')",
				deck);
	}

	public Map<Card, Integer> getReanimateCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (skill_disc ilike '%Reanimate (%' or evo_skill_disc ilike '%Reanimate (%' )",
				deck);
	}

	public Map<Card, Integer> getSpellboostCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement(
				"select* from cards where card_id=? AND (skill_disc ilike '%this card has been spellboosted%' or evo_skill_disc ilike '%this card has been spellboosted%' or skill_disc ilike '%spellboost:%' or evo_skill_disc ilike '%spellboost:%')",
				deck);
	}

	public Map<Card, Integer> getSpellCardsFromDeck(List<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<>();
		for (Card c : deck) {
			if (c.getCharType() == 4) {
				super.checkAlreadyPresent(cards, c);
			}
		}
		return cards;
	}
}
