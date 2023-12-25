package my_click.domain;

import my_click.enums.StatusEnum;

import java.time.LocalDateTime;

public class TransActionHistory {

    public int sequence = 0;

    {
        sequence++;
    }

    private Integer id = sequence;

    private Card fromCard;

    private Card toCard;

    private Double amount;

    private LocalDateTime localDateTime = LocalDateTime.now();

    private StatusEnum statusEnum;

    public TransActionHistory(Card fromCard, Card toCard, Double amount) {
        this.fromCard = fromCard;
        this.toCard = toCard;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Card getFromCard() {
        return fromCard;
    }

    public void setFromCard(Card fromCard) {
        this.fromCard = fromCard;
    }

    public Card getToCard() {
        return toCard;
    }

    public void setToCard(Card toCard) {
        this.toCard = toCard;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public StatusEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }
}
