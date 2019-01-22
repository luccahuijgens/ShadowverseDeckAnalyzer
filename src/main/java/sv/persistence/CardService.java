package sv.persistence;

import java.util.ArrayList;
import java.util.Map;

import sv.domain.Card;

public class CardService {
	private KeywordDAO keywordDAO = new KeywordDAO();
	private GeneralDAO generalDAO = new GeneralDAO();
	private MechanicDAO mechanicDAO = new MechanicDAO();
	private TraitDAO traitDAO = new TraitDAO();

	public CardService() {
	};

	public Map<Card, Integer> getAllCards() {
		return generalDAO.getAllCards();
	}

	public Map<Card, Integer> getCardsFromDeck(ArrayList<Card> deck) {
		return generalDAO.getCardsFromDeck(deck);
	}

	public Map<Card, Integer> getRemovalCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getRemovalCardsFromDeck(deck);
	}

	public Map<Card, Integer> getAoERemovalCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getAoERemovalCardsFromDeck(deck);
	}

	public Map<Card, Integer> getBurnCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getBurnCardsFromDeck(deck);
	}

	public Map<Card, Integer> getSummonerCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getSummonerCardsFromDeck(deck);
	}

	public Card getCardByID(int id) {
		return generalDAO.getCardByID(id);
	}

	public Map<Card, Integer> getTutorCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getTutorCardsFromDeck(deck);
	}

	public Map<Card, Integer> getDrawCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getDrawCardsFromDeck(deck);
	}

	public Map<Card, Integer> getBoardBuildingCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getBoardBuildingCardsFromDeck(deck);
	}

	public Map<Card, Integer> getEarthSigilCardsFromDeck(ArrayList<Card> deck) {
		return traitDAO.getEarthSigilCardsFromDeck(deck);
	}

	public Map<Card, Integer> getFollowerCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getFollowerCardsFromDeck(deck);
	}

	public Map<Card, Integer> getSpellCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getSpellCardsFromDeck(deck);
	}

	public Map<Card, Integer> getAmuletCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getAmuletCardsFromDeck(deck);
	}

	public Map<Card, Integer> getEarlyCardsFromDeck(ArrayList<Card> deck) {
		return generalDAO.getEarlyCardsFromDeck(deck);
	}

	public Map<Card, Integer> getMidCardsFromDeck(ArrayList<Card> deck) {
		return generalDAO.getMidCardsFromDeck(deck);
	}

	public Map<Card, Integer> getLateCardsFromDeck(ArrayList<Card> deck) {
		return generalDAO.getLateCardsFromDeck(deck);
	}

	public Map<Card, Integer> getResourceCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getResourceCardsFromDeck(deck);
	}

	public Map<Card, Integer> getHealCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getHealCardsFromDeck(deck);
	}

	public Map<Card, Integer> getBuffCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getBuffCardsFromDeck(deck);
	}

	public Map<Card, Integer> getClassCardsFromDeck(ArrayList<Card> deck) {
		return generalDAO.getClassCardsFromDeck(deck);
	}

	public Map<Card, Integer> getNeutralCardsFromDeck(ArrayList<Card> deck) {
		return generalDAO.getNeutralCardsFromDeck(deck);
	}

	public Map<Card, Integer> getAccelerateCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getAccelerateCardsFromDeck(deck);
	}

	public Map<Card, Integer> getAmbushCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getAmbushCardsFromDeck(deck);
	}

	public Map<Card, Integer> getArtifactCardsFromDeck(ArrayList<Card> deck) {
		return traitDAO.getArtifactCardsFromDeck(deck);
	}

	public Map<Card, Integer> getBounceCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getBounceCardsFromDeck(deck);
	}

	public Map<Card, Integer> getBurialRiteCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getBurialRiteCardsFromDeck(deck);
	}

	public Map<Card, Integer> getCommanderCardsFromDeck(ArrayList<Card> deck) {
		return traitDAO.getCommanderCardsFromDeck(deck);
	}

	public Map<Card, Integer> getOfficerCardsFromDeck(ArrayList<Card> deck) {
		return traitDAO.getOfficerCardsFromDeck(deck);
	}

	public Map<Card, Integer> getCountdownCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getCountdownCardsFromDeck(deck);
	}

	public Map<Card, Integer> getEnhanceCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getEnhanceCardsFromDeck(deck);
	}

	public Map<Card, Integer> getEvolveCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getEvolveCardsFromDeck(deck);
	}

	public Map<Card, Integer> getEvoPointCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getEvoPointCardsFromDeck(deck);
	}

	public Map<Card, Integer> getFanfareCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getFanfareCardsFromDeck(deck);
	}

	public Map<Card, Integer> getFriendlyFireCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getFriendlyFireCardsFromDeck(deck);
	}

	public Map<Card, Integer> getGenerationCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getGenerationCardsFromDeck(deck);
	}

	public Map<Card, Integer> getInvocationCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getInvocationCardsFromDeck(deck);
	}

	public Map<Card, Integer> getLastWordsCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getLastWordsCardsFromDeck(deck);
	}

	public Map<Card, Integer> getLootCardsFromDeck(ArrayList<Card> deck) {
		return traitDAO.getLootCardsFromDeck(deck);
	}

	public Map<Card, Integer> getMysteriaCardsFromDeck(ArrayList<Card> deck) {
		return traitDAO.getMysteriaCardsFromDeck(deck);
	}

	public Map<Card, Integer> getNecromancyCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getNecromancyCardsFromDeck(deck);
	}

	public Map<Card, Integer> getPermanentCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getPermanentCardsFromDeck(deck);
	}

	public Map<Card, Integer> getReanimateCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getReanimateCardsFromDeck(deck);
	}

	public Map<Card, Integer> getRushCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getRushCardsFromDeck(deck);
	}

	public Map<Card, Integer> getStormCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getStormCardsFromDeck(deck);
	}

	public Map<Card, Integer> getSubtractionCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getSubtractionCardsFromDeck(deck);
	}

	public Map<Card, Integer> getWardCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getWardCardsFromDeck(deck);
	}

	public Map<Card, Integer> getUnattackableCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getUnattackableCardsFromDeck(deck);
	}

	public Map<Card, Integer> getUndamagableCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getUndamageableCardsFromDeck(deck);
	}

	public Map<Card, Integer> getUndestoryableCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getUndestroyableCardsFromDeck(deck);
	}

	public Map<Card, Integer> getUntargetableCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getUntargatableCardsFromDeck(deck);
	}

	public Map<Card, Integer> getDamageLimiterCardsFromDeck(ArrayList<Card> deck) {
		return mechanicDAO.getDamageLimiterCardsFromDeck(deck);
	}
	
	public Map<Card, Integer> getSpellboostCardsFromDeck(ArrayList<Card> deck) {
		return keywordDAO.getSpellboostCardsFromDeck(deck);
	}
}
