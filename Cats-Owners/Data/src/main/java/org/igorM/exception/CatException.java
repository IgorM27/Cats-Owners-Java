package org.igorM.exception;

public class CatException extends RuntimeException{
    private CatException(String message) {
        super(message);
    }
    public static CatException noFriend(int catId, int friendId) {
        return new CatException("Cat with id " + catId + " and cat with id " + friendId + " are not friends");
    }
    public static CatException alreadyFriends(int catId, int friendId) {
        return new CatException("Cat with id " + catId + " and cat with id " + friendId + "are already friends");
    }

}
