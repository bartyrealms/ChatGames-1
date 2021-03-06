package net.draycia.chatgames.games;

import net.draycia.chatgames.ChatGames;
import net.draycia.chatgames.util.HangmanConfig;
import net.draycia.chatgames.util.MessageKey;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HangmanGame extends ChatGame {

    private final String display;
    private final String word;

    HangmanGame(ChatGames main) {
        super(main);

        word = getRandomWord();
        display = makeDashedWord(word);

        super.onStart(getConfig().getMessage(MessageKey.HANGMAN_START).get(0));
    }

    @Override
    public String getAnswer() {
        return word;
    }

    @Override
    public String getDisplayText() {
        return display;
    }

    @Override
    long getReward() {
        return 0;
    }

    @Override
    HangmanConfig getGameConfig() {
        return (HangmanConfig) getConfig().getGameConfig(GameType.HANGMAN);
    }

    @Override
    List<String> getRewardCommands(int place) {
        return getGameConfig().getCommandRewards(place);
    }

    @Override
    GameType getGameType() {
        return GameType.HANGMAN;
    }

    public String makeDashedWord(String word) {
        int dashCount = (int) Math.round(Math.max(word.length() * getGameConfig().getDashPercentage(), 1));
        dashCount = Math.min(word.length() - 1, dashCount);

        StringBuffer sb = new StringBuffer(word);

        ThreadLocalRandom.current().ints(0, word.length())
                .distinct()
                .limit(dashCount)
                .forEach(i -> sb.setCharAt(i, '_'));

        return sb.toString();
    }
}
