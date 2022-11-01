package onboarding;

import java.util.*;


public class Problem7 {

    private final static int SCORE_OF_FRIENDS_WITH_USER = 10;
    private final static int SCORE_OF_TIMELINE_VISIT = 1;
    private final static int RECOMMEND_NUMBER_OF_PEOPLE = 5;

    public static List<String> solution(String user, List<List<String>> friends, List<String> visitors) {
        Set<String> allNames = getAllNames(friends, visitors, user);
        List<Integer>[] friendship = new List[allNames.size()];
        friendshipInnerInit(allNames, friendship);

        Map<String, Integer> nameToIdMap = new HashMap<>();
        Map<Integer, String> idToNameMap = new HashMap<>();

        setId(allNames, nameToIdMap, idToNameMap);
        setFriendship(friends, friendship, nameToIdMap);

        Set<String> userFriends = new HashSet<>();
        userFriends.add(user);

        int[] scores = new int[allNames.size()];
        for (int i = 0; i < friendship[nameToIdMap.get(user)].size(); i++) {
            int numberOfUserFriend = friendship[nameToIdMap.get(user)].get(i);
            userFriends.add(idToNameMap.get(numberOfUserFriend));
            scoreTogetherWithFriendCalculate(scores, friendship[numberOfUserFriend]);
        }

        visitScoreCalculate(visitors, scores, nameToIdMap);

        Queue<NameAndScore> recommendedAlgorithmRank = new PriorityQueue<>();
        for (int i = 0; i < scores.length; i++) {
            if (userFriends.add(idToNameMap.get(i))) {
                NameAndScore nameAndScore = new NameAndScore(idToNameMap.get(i), scores[i]);
                recommendedAlgorithmRank.add(nameAndScore);
            }
        }

        List<String> ranks = new ArrayList<>();
        removeZeroScore(recommendedAlgorithmRank, ranks);

        return ranks;
    }

    private static Set<String> getAllNames(List<List<String>> friends, List<String> visitor, String user) {
        Set<String> allNames = new HashSet<>();

        for (List<String> friend : friends) {
            for (String name : friend) {
                allNames.add(name);
            }
        }

        for (String name : visitor) {
            allNames.add(name);
        }

        allNames.add(user);

        return allNames;
    }

    private static void friendshipInnerInit(Set<String> allNames, List<Integer>[] friendship) {
        for (int i = 0; i < allNames.size(); i++) {
            friendship[i] = new ArrayList<>();
        }
    }

    private static void setId(Set<String> allNames, Map<String, Integer> nameToIdMap, Map<Integer, String> idToNameMap) {
        int sequence = 0;
        for (String name : allNames) {
            nameToIdMap.put(name, sequence);
            idToNameMap.put(sequence, name);

            sequence++;
        }
    }

    private static void setFriendship(List<List<String>> friends, List<Integer>[] friendship, Map<String, Integer> nameToIdMap) {
        for (int i = 0; i < friends.size(); i++) {
            int people = nameToIdMap.get(friends.get(i).get(0));
            int otherPeople = nameToIdMap.get(friends.get(i).get(1));

            friendship[people].add(otherPeople);
            friendship[otherPeople].add(people);
        }
    }

    private static void scoreTogetherWithFriendCalculate(int[] scores, List<Integer> friendship) {
        for (int j = 0; j < friendship.size(); j++) {
            int myFriendOfFriend = friendship.get(j);
            scores[myFriendOfFriend] += SCORE_OF_FRIENDS_WITH_USER;
        }
    }

    private static void visitScoreCalculate(List<String> visitors, int[] scores, Map<String, Integer> nameToIdMap) {
        for (int i = 0; i < visitors.size(); i++) {
            int visitorNumber = nameToIdMap.get(visitors.get(i));
            scores[visitorNumber] += SCORE_OF_TIMELINE_VISIT;
        }
    }

    private static void removeZeroScore(Queue<NameAndScore> recommendedAlgorithmRank, List<String> answer) {
        for (int i = 0; i < RECOMMEND_NUMBER_OF_PEOPLE; i++) {

            if (!recommendedAlgorithmRank.isEmpty()) {
                NameAndScore nameAndScore = recommendedAlgorithmRank.poll();
                int score = nameAndScore.score;

                if (isZero(score)) {
                    continue;
                }

                answer.add(nameAndScore.name);
            }
        }
    }

    private static boolean isZero(int num) {
        return num == 0;
    }

    private static class NameAndScore implements Comparable<NameAndScore> {
        private String name;
        private int score;

        public NameAndScore(String userName, int score) {
            this.name = userName;
            this.score = score;
        }

        @Override
        public int compareTo(NameAndScore o) {
            if (score == o.score) {
                return name.compareTo(o.name);
            }
            return o.score - score;
        }
    }
}
