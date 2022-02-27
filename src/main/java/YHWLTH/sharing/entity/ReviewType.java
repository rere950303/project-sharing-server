package YHWLTH.sharing.entity;

public enum ReviewType {

    LENDER("lender"), BORROWER("borrower");

    private final String desc;

    ReviewType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}