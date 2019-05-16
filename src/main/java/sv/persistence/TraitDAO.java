package sv.persistence;

import java.util.List;
import java.util.Map;

import sv.domain.Card;

public class TraitDAO extends BaseDAO {
	private static TraitDAO instance;
	
	private TraitDAO() {}
	
	public static TraitDAO getInstance() {
		if (instance==null) {
			instance=new TraitDAO();
		}
		return instance;
	}

	public Map<Card, Integer> getEarthSigilCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND tribe_name='Earth Sigil'", deck);
	}

	public Map<Card, Integer> getLootCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND tribe_name='Loot'", deck);
	}

	public Map<Card, Integer> getArtifactCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND tribe_name='Artifact'", deck);
	}
	
	public Map<Card, Integer> getMachinaCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND tribe_name='Machina'", deck);
	}

	public Map<Card, Integer> getMysteriaCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND tribe_name='Mysteria'", deck);
	}

	public Map<Card, Integer> getOfficerCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND tribe_name='Officer'", deck);
	}

	public Map<Card, Integer> getCommanderCardsFromDeck(List<Card> deck) {
		return super.getCardsFromStatement("select* from cards where card_id=? AND tribe_name='Commander'", deck);
	}
}
