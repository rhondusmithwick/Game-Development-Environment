package voogasalad.util.facebookutil.scores;

import java.util.Comparator;

/**
 * Enum to help sort the high score board by various
 * fields
 * @author Tommy
 *
 */
public enum ScoreOrder {

                        HIGHEST(compareScore().reversed()),
                        LOWEST(compareScore()),
                        ALPHABETICAL(compareAlphabetical()),
                        REVERSE_ALPHABETICAL(compareAlphabetical().reversed()),
                        NEWEST(compareDate().reversed()),
                        OLDEST(compareDate());

    private Comparator<Score> myComparator;

    private ScoreOrder (Comparator<Score> comparator) {
        myComparator = comparator;
    }

    /**
     * Get the comparator to use in list sorting
     * @param reverse - true if you want to reverse the order
     * @return
     */
    public Comparator<Score> getComparator () {
        return myComparator;
    }

    /**
     * Compare by the points
     * @return
     */
    private static Comparator<Score> compareScore () {
        return (Score s1, Score s2) -> Integer.compare(s1.getPoints(),
                                                       s2.getPoints());
    }

    /**
     * Compare the user's emails in alphabetical order
     * @return
     */
    private static Comparator<Score> compareAlphabetical () {
        return (Score s1, Score s2) -> s1.getUser().getUserEmail().getName()
                .compareTo(s2.getUser().getUserEmail().getName());
    }

    /**
     * Compare the date the user achieved the score
     * @return
     */
    private static Comparator<Score> compareDate () {
        return (Score s1, Score s2) -> s1.getDate().compareTo(s2.getDate());
    }

}
