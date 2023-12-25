package my_click;

import my_click.domain.Card;
import my_click.domain.User;
import my_click.enums.CardTypeEnums;
import my_click.service.CardService;
import my_click.service.UserService;
import my_click.service.impl.CardServiceImpl;
import my_click.service.impl.UserServiceImpl;
import my_click.utils.StringUtils;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {

    static Scanner scStr = new Scanner(System.in);
    static Scanner scInt = new Scanner(System.in);
    static UserService userService = new UserServiceImpl();
    static CardService cardService = new CardServiceImpl();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        while (true) {
            System.out.println("""
                    1. Login :
                    2. Register :
                    0. Exit :
                    """);

            System.out.print("Enter...");
            int userInput = scInt.nextInt();
            if (userInput == 0) {
                break;
            }
            if (userInput == 1) {
                System.out.print("Enter the name : ");
                String name = scStr.nextLine();
                System.out.print("Enter the password : ");
                String password = scStr.nextLine();
                var loggedUser = userService.login(password, name);
                if (loggedUser != null) {
                    userMenu(loggedUser);
                }
            } else if (userInput == 2) {
                System.out.print("Enter the name : ");
                String name = scStr.nextLine();
                System.out.print("Enter the password : ");
                String password = scStr.nextLine();
                User user = new User(name, password);
                var loggedUser= userService.registerUser(user);
                if (loggedUser != null) {
                    userMenu(user);
                }
            }
        }
    }

    private static void userMenu(User loggedUser){
        while (true) {
            printUserMenu();
            System.out.print("Enter...");
            int userInput = scInt.nextInt();
            if (userInput == 0) {
                break;
            }

            switch (userInput) {
                case 1 -> {
                    CardTypeEnums.printCardTypes();
                    System.out.print("Enter index of card : ");
                    int index = scInt.nextInt();
                    System.out.print("Enter the card name : ");
                    String name = scStr.nextLine();
                    System.out.print("Enter the card number : ");
                    String cardNumber = scStr.nextLine();
                    var result= StringUtils.weatherTheStringIsOnlyNumber(cardNumber);
                    if ((!result) && cardNumber.length() < 8) {
                        System.out.println("Invalid card number ");
                        break;
                    }
                    System.out.print("Enter date of card :");
                    String beforeDate = scStr.nextLine();
                    if (!(beforeDate.charAt(2) == '/')) {
                        System.out.println("card date is incorrect it must be with '/'");
                        break;
                    }
                    var cardType = CardTypeEnums.getTypeByIndex(index);
                    Card card = new Card(name, loggedUser.getId(), cardNumber, beforeDate, cardType);
                    loggedUser.setCard(card);
                    getThreadUserAddCard(card).start();
                }
                case 2 -> {
                    cardService.showUserCards(loggedUser.getId());
                    System.out.print("Enter the card id : ");
                    Integer cardId = scInt.nextInt();
                    System.out.print("Enter the amount of money : ");
                    Double amount = scInt.nextDouble();
                    getThreadUserAddMoney(cardId, loggedUser.getId(), amount).start();
                }
                case 3 -> {
                    getThreadUserShowUserCards(loggedUser.getId()).start();
                }
                case 4 -> {
                    cardService.showUserCards(loggedUser.getId());
                    System.out.print("Enter your card id which you want to send from: ");
                    Integer fromCardId = scInt.nextInt();
                    System.out.print("Enter your card id which you want to receive : ");
                    String cardNumber;
                    do {
                        cardNumber = scStr.nextLine();
                    } while (!StringUtils.weatherTheStringIsOnlyNumber(cardNumber));
                    Double amount;
                    do {
                        System.out.print("Enter amount: ");
                        amount = scInt.nextDouble();
                    } while (amount <= 0);
                    try {
                        var sendMoney = cardService.sendMoney(fromCardId, cardService.getCardByNumber(cardNumber).getId(), amount);
                        if (sendMoney) {
                            System.out.println("Success");
                        } else {
                            System.out.println("Something went wrong, try again later!");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println("Something went wrong!");
                    }
                }
            }
        }
    }

    private static void printUserMenu() {
        System.out.println("""
                1. Add card
                2. Add money card
                3. Show user cards
                4. Send money
                0. Exit
                """);
    }

    public static Thread getThreadUserAddCard(Card card) {
        return new Thread(() -> {
            cardService.addCard(card);
        });
    }

    public static Thread getThreadUserShowUserCards(Integer userId) {
        return new Thread(() -> {
            cardService.showUserCards(userId);
        });
    }

    public static Thread getThreadUserAddMoney(Integer cardId, Integer ownerId, Double amount) {
        return new Thread(() -> {
            userService.addUserCardMoney(cardId, ownerId, amount);
        });
    }
}