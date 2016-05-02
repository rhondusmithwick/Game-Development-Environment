package model.physics;

public enum CollisionTypeEnum {

    TOP(TopCollision.class.getName()),
    BOTTOM(BottomCollision.class.getName()),
    LEFT(LeftCollision.class.getName()),
    RIGHT(RightCollision.class.getName());

    private String type;

    CollisionTypeEnum (String type) {
        this.type = type;
    }

    public String getType () {
        return type;
    }

}