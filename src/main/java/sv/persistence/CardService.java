package sv.persistence;

import java.util.ArrayList;

import sv.domain.Card;

public class CardService {
private CardDAO cardDAO=new CardDAO();

public CardService() {};

public ArrayList<Card> getAllCards(){
	return cardDAO.getAllCards();
}

public ArrayList<Card> getCardsFromDeck(ArrayList<Card>deck){
	return cardDAO.getCardsFromDeck(deck);
}

public ArrayList<Card> getRemovalCardsFromDeck(ArrayList<Card>deck){
	return cardDAO.getRemovalCardsFromDeck(deck);
}

public ArrayList<Card> getAoERemovalCardsFromDeck(ArrayList<Card>deck){
	return cardDAO.getAoERemovalCardsFromDeck(deck);
}

public ArrayList<Card> getBurnCardsFromDeck(ArrayList<Card>deck){
	return cardDAO.getBurnCardsFromDeck(deck);
}

public ArrayList<Card> getSummonerCardsFromDeck(ArrayList<Card>deck){
	return cardDAO.getSummonerCardsFromDeck(deck);
}

public Card getCardByID(int id) {
	return cardDAO.getCardByID(id);
}

public ArrayList<Card> getTutorCardsFromDeck(ArrayList<Card> deck) {
	return cardDAO.getTutorCardsFromDeck(deck);
}

public ArrayList<Card> getDrawCardsFromDeck(ArrayList<Card> deck) {
	return cardDAO.getDrawCardsFromDeck(deck);
}
public ArrayList<Card> getBoardBuildingCardsFromDeck(ArrayList<Card> deck) {
	return cardDAO.getBoardBuildingCardsFromDeck(deck);
}
public ArrayList<Card> getEarthSigilCardsFromDeck(ArrayList<Card> deck){
	return cardDAO.getEarthSigilCardsFromDeck(deck);
}

public ArrayList<Card> getFollowerCardsFromDeck(ArrayList<Card> deck) {
	return cardDAO.getFollowerCardsFromDeck(deck);
}
public ArrayList<Card> getSpellCardsFromDeck(ArrayList<Card> deck) {
	return cardDAO.getSpellCardsFromDeck(deck);
}
public ArrayList<Card> getAmuletCardsFromDeck(ArrayList<Card> deck) {
	return cardDAO.getAmuletCardsFromDeck(deck);
}
public ArrayList<Card> getEarlyCardsFromDeck(ArrayList<Card> deck) {
	return cardDAO.getEarlyCardsFromDeck(deck);
}
public ArrayList<Card> getMidCardsFromDeck(ArrayList<Card> deck) {
	return cardDAO.getMidCardsFromDeck(deck);
}
public ArrayList<Card> getLateCardsFromDeck(ArrayList<Card> deck) {
	return cardDAO.getLateCardsFromDeck(deck);
}
}
