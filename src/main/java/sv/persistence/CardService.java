package sv.persistence;

import java.util.List;
import java.util.Map;

import sv.domain.Card;

public class CardService {
	private static CardService instance;
	private KeywordDAO keywordDAO = KeywordDAO.getInstance();
	private GeneralDAO generalDAO = GeneralDAO.getInstance();
	private MechanicDAO mechanicDAO = MechanicDAO.getInstance();
	private TraitDAO traitDAO = TraitDAO.getInstance();
	
	private CardService() {}
	
	public static CardService getInstance() {
		if (instance==null) {
			instance=new CardService();
		}
		return instance;
	}

	public Map<Card, Integer> getAllCards() {
		return generalDAO.getAllCards();
	}

	public Map<Card, Integer> getCardsFromDeck(List<Card> deck) {
		return generalDAO.getCardsFromDeck(deck);
	}

	public Map<Card, Integer> getRemovalCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getRemovalCardsFromDeck(deck);
	}

	public Map<Card, Integer> getAoERemovalCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getAoERemovalCardsFromDeck(deck);
	}

	public Map<Card, Integer> getBurnCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getBurnCardsFromDeck(deck);
	}

	public Map<Card, Integer> getSummonerCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getSummonerCardsFromDeck(deck);
	}

	public Card getCardByID(int id) {
		return generalDAO.getCardByID(id);
	}

	public Map<Card, Integer> getTutorCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getTutorCardsFromDeck(deck);
	}

	public Map<Card, Integer> getDrawCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getDrawCardsFromDeck(deck);
	}

	public Map<Card, Integer> getBoardBuildingCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getBoardBuildingCardsFromDeck(deck);
	}

	public Map<Card, Integer> getEarthSigilCardsFromDeck(List<Card> deck) {
		return traitDAO.getEarthSigilCardsFromDeck(deck);
	}

	public Map<Card, Integer> getFollowerCardsFromDeck(List<Card> deck) {
		return keywordDAO.getFollowerCardsFromDeck(deck);
	}

	public Map<Card, Integer> getSpellCardsFromDeck(List<Card> deck) {
		return keywordDAO.getSpellCardsFromDeck(deck);
	}

	public Map<Card, Integer> getAmuletCardsFromDeck(List<Card> deck) {
		return keywordDAO.getAmuletCardsFromDeck(deck);
	}

	public Map<Card, Integer> getEarlyCardsFromDeck(List<Card> deck) {
		return generalDAO.getEarlyCardsFromDeck(deck);
	}

	public Map<Card, Integer> getMidCardsFromDeck(List<Card> deck) {
		return generalDAO.getMidCardsFromDeck(deck);
	}

	public Map<Card, Integer> getLateCardsFromDeck(List<Card> deck) {
		return generalDAO.getLateCardsFromDeck(deck);
	}

	public Map<Card, Integer> getResourceCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getResourceCardsFromDeck(deck);
	}

	public Map<Card, Integer> getHealCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getHealCardsFromDeck(deck);
	}

	public Map<Card, Integer> getBuffCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getBuffCardsFromDeck(deck);
	}

	public Map<Card, Integer> getClassCardsFromDeck(List<Card> deck) {
		return generalDAO.getClassCardsFromDeck(deck);
	}

	public Map<Card, Integer> getNeutralCardsFromDeck(List<Card> deck) {
		return generalDAO.getNeutralCardsFromDeck(deck);
	}

	public Map<Card, Integer> getAccelerateCardsFromDeck(List<Card> deck) {
		return keywordDAO.getAccelerateCardsFromDeck(deck);
	}

	public Map<Card, Integer> getAmbushCardsFromDeck(List<Card> deck) {
		return keywordDAO.getAmbushCardsFromDeck(deck);
	}

	public Map<Card, Integer> getArtifactCardsFromDeck(List<Card> deck) {
		return traitDAO.getArtifactCardsFromDeck(deck);
	}

	public Map<Card, Integer> getBounceCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getBounceCardsFromDeck(deck);
	}

	public Map<Card, Integer> getBurialRiteCardsFromDeck(List<Card> deck) {
		return keywordDAO.getBurialRiteCardsFromDeck(deck);
	}

	public Map<Card, Integer> getCommanderCardsFromDeck(List<Card> deck) {
		return traitDAO.getCommanderCardsFromDeck(deck);
	}

	public Map<Card, Integer> getOfficerCardsFromDeck(List<Card> deck) {
		return traitDAO.getOfficerCardsFromDeck(deck);
	}

	public Map<Card, Integer> getCountdownCardsFromDeck(List<Card> deck) {
		return keywordDAO.getCountdownCardsFromDeck(deck);
	}

	public Map<Card, Integer> getEnhanceCardsFromDeck(List<Card> deck) {
		return keywordDAO.getEnhanceCardsFromDeck(deck);
	}

	public Map<Card, Integer> getEvolveCardsFromDeck(List<Card> deck) {
		return keywordDAO.getEvolveCardsFromDeck(deck);
	}

	public Map<Card, Integer> getEvoPointCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getEvoPointCardsFromDeck(deck);
	}

	public Map<Card, Integer> getFanfareCardsFromDeck(List<Card> deck) {
		return keywordDAO.getFanfareCardsFromDeck(deck);
	}

	public Map<Card, Integer> getFriendlyFireCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getFriendlyFireCardsFromDeck(deck);
	}

	public Map<Card, Integer> getGenerationCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getGenerationCardsFromDeck(deck);
	}

	public Map<Card, Integer> getInvocationCardsFromDeck(List<Card> deck) {
		return keywordDAO.getInvocationCardsFromDeck(deck);
	}

	public Map<Card, Integer> getLastWordsCardsFromDeck(List<Card> deck) {
		return keywordDAO.getLastWordsCardsFromDeck(deck);
	}

	public Map<Card, Integer> getLootCardsFromDeck(List<Card> deck) {
		return traitDAO.getLootCardsFromDeck(deck);
	}

	public Map<Card, Integer> getMysteriaCardsFromDeck(List<Card> deck) {
		return traitDAO.getMysteriaCardsFromDeck(deck);
	}

	public Map<Card, Integer> getNecromancyCardsFromDeck(List<Card> deck) {
		return keywordDAO.getNecromancyCardsFromDeck(deck);
	}

	public Map<Card, Integer> getPermanentCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getPermanentCardsFromDeck(deck);
	}

	public Map<Card, Integer> getReanimateCardsFromDeck(List<Card> deck) {
		return keywordDAO.getReanimateCardsFromDeck(deck);
	}

	public Map<Card, Integer> getRushCardsFromDeck(List<Card> deck) {
		return keywordDAO.getRushCardsFromDeck(deck);
	}

	public Map<Card, Integer> getStormCardsFromDeck(List<Card> deck) {
		return keywordDAO.getStormCardsFromDeck(deck);
	}

	public Map<Card, Integer> getSubtractionCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getSubtractionCardsFromDeck(deck);
	}

	public Map<Card, Integer> getWardCardsFromDeck(List<Card> deck) {
		return keywordDAO.getWardCardsFromDeck(deck);
	}

	public Map<Card, Integer> getUnattackableCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getUnattackableCardsFromDeck(deck);
	}

	public Map<Card, Integer> getUndamagableCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getUndamageableCardsFromDeck(deck);
	}

	public Map<Card, Integer> getUndestoryableCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getUndestroyableCardsFromDeck(deck);
	}

	public Map<Card, Integer> getUntargetableCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getUntargatableCardsFromDeck(deck);
	}

	public Map<Card, Integer> getDamageLimiterCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getDamageLimiterCardsFromDeck(deck);
	}
	
	public Map<Card, Integer> getSpellboostCardsFromDeck(List<Card> deck) {
		return keywordDAO.getSpellboostCardsFromDeck(deck);
	}
	
	public Map<Card, Integer> getRampCardsFromDeck(List<Card> deck) {
		return mechanicDAO.getRampCardsFromDeck(deck);
	}
	
	public Map<Card, Integer> getChooseCardsFromDeck(List<Card> deck) {
		return keywordDAO.getChooseCardsFromDeck(deck);
	}
	
	public Map<Card, Integer> getBaneCardsFromDeck(List<Card> deck) {
		return keywordDAO.getBaneCardsFromDeck(deck);
	}
	
	public Map<Card, Integer> getClashCardsFromDeck(List<Card> deck) {
		return keywordDAO.getClashCardsFromDeck(deck);
	}
	
	public Map<Card, Integer> getMachinaCardsFromDeck(List<Card> deck) {
		return traitDAO.getMachinaCardsFromDeck(deck);
	}
}
