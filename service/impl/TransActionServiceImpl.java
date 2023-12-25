package my_click.service.impl;

import my_click.domain.Card;
import my_click.service.TransActionService;

import static my_click.service.UserService.USER_CARDS;

public class TransActionServiceImpl implements TransActionService {
    @Override
    public void showAll(Card card) {
        for (Card card1:USER_CARDS) {
//            if (card1.)
        }
    }
}
