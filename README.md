# ShadowverseDeckAnalyzer
A web application that analyzes decks from Shadowverse. Provide a deck code and this tool will provide a detailed analysis of it's content.

## Tools
The application uses a front-end that utilizes CSS, HTML5 and JavaScript. The application also uses a Java backend that communicates with
a Database. The front-end communicates with the backend through REST calls. SVDeckAnalyzer uses the Shadowverse-Portal API to receive the
contents of the deck that is associated with any given deck code. Because of this, you can use the ingame deck-editor or any custom
deck-editor that uses Shadowverse-Portal to generate a deck code for the DeckAnalyzer. The application is currently hosted on 
https://svdeckanalyzer.herokuapp.com/, so feel free to try it out.

## Screenshots
![alt text](https://raw.githubusercontent.com/luccahuijgens/ShadowverseDeckAnalyzer/master/index.png)
**Provide a deck code and select all the card categories you want to be highlighted.**

![alt text](https://raw.githubusercontent.com/luccahuijgens/ShadowverseDeckAnalyzer/master/graphs.png)
**Visualize the content of the deck with graphs**

![alt text](https://raw.githubusercontent.com/luccahuijgens/ShadowverseDeckAnalyzer/master/categories.png)
**View the cards per card type, keyword, or mechanic**
