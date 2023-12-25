package my_click.service;

import my_click.domain.Card;
import my_click.domain.TransActionHistory;

import java.util.ArrayList;
import java.util.List;

public interface TransActionService {
    List<TransActionHistory> TRANS_ACTION_HISTORY_LIST = new ArrayList<>();
    void showAll(Card card);
}
