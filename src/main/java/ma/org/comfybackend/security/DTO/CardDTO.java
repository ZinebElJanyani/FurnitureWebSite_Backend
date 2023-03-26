package ma.org.comfybackend.security.DTO;

public class CardDTO {
    String name;

    String cardNumber;

    int CVC;

    public String getName() {
        return name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getCVC() {
        return CVC;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCVC(int CVC) {
        this.CVC = CVC;
    }
}
