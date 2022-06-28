package YHWLTH.sharing.entity;

public enum ItemType {

    CAL("계산기"), DEGREEGOWN("학위가운"), BICYCLE("자전거"), UMBRELLA("우산"), BOOK("서적"), EXPERIMENTGOWN("실험가운"), ETC("기타");

    private final String desc;

    ItemType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}