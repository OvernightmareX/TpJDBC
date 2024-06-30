package org.example.services;

import org.example.entities.Article;
import org.example.entities.Customer;
import org.example.entities.Sale;
import org.example.entities.SaleLine;
import org.example.repositories.SaleLineRepository;
import org.example.repositories.SaleRepository;
import org.example.utils.InputUtils;
import org.example.utils.TypeSale;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleService {
    private SaleRepository saleRepository = new SaleRepository();
    private SaleLineRepository saleLineRepository = new SaleLineRepository();
    private CustomerService customerService = new CustomerService();
    private ArticleService articleService = new ArticleService();

    public void createSale(){
        System.out.println("\n=== Sale registration ===\n");
        int userInput;

        Customer currentCustomer = null;
        List<SaleLine> saleLines = new ArrayList<>();
        Sale sale = new Sale();

        do {
            displayCurrentCustomer(currentCustomer);
            displaySelectedArticles(saleLines);
            displayOrderFinalization(currentCustomer, saleLines);

            userInput = InputUtils.userRangeIntInput(0, 2);

            switch (userInput){
                case 1 -> currentCustomer = selectCustomer();
                case 2 -> selectArticle(saleLines, sale);
            }
        }while (userInput != 0);

        if(currentCustomer == null || saleLines.isEmpty()){
            return;
        }

        sale.setSaleLines(saleLines);
        sale.setCustomer(currentCustomer);
        sale.setSaleDate(LocalDate.now());
        TypeSale typeSale = selectSaleType(sale);
        sale.setTypeSale(typeSale);
        saleRepository.save(sale);
        for(SaleLine saleLine : saleLines)
        {
            saleLineRepository.save(saleLine);
            if(typeSale == TypeSale.DONE) {
                Article article = saleLine.getArticle();
                article.setQuantity(article.getQuantity() - saleLine.getQuantity());
                articleService.saveArticle(article);
            }
        }

        List<Sale> sales = currentCustomer.getPurchasingHistory();
        sales.add(sale);
        currentCustomer.setPurchasingHistory(sales);
        customerService.saveCustomer(currentCustomer);
    }

    private Customer selectCustomer(){
        int userInput;
        int userInputMax;
        long customerCount = customerService.getCustomerCount();

        customerService.displayAllCustomer();
        System.out.println("\nYour customer is in the list ?");
        System.out.println("0. No (create a new customer)");

        if(customerCount == 0)
            userInputMax = 0;
        else {
            userInputMax = 1;
            System.out.println("1. Yes");
        }

        System.out.print("\nYour choice : ");
        userInput = InputUtils.userRangeIntInput(0,userInputMax);

        if(userInput == 0)
            return customerService.createCustomer();
        else
            return customerService.selectCustomerByID();
    }

    private void selectArticle(List<SaleLine> saleLines, Sale sale){
        Article selectedArticle;
        SaleLine saleLine = new SaleLine();

        if(articleService.getArticleCount() == 0){
            System.out.println("No articles available");
            return;
        }

        do {
            articleService.displayAllArticles();
            selectedArticle = articleService.getArticleByID();
            if(selectedArticle == null)
                return;

            if(selectedArticle.getQuantity() == 0){
                System.out.println("Your selected article is out of stock.");
                continue;
            }
            break;
        }while (true);

        saleLine.setArticle(selectedArticle);
        System.out.print("Please enter a quantity : ");
        saleLine.setQuantity(InputUtils.userRangeIntInput(1, selectedArticle.getQuantity()));
        saleLine.setSale(sale);

        saleLines.removeIf(saleLine1 -> saleLine1.getArticle().equals(saleLine.getArticle()));
        saleLines.add(saleLine);
    }

    private static TypeSale selectSaleType(Sale sale){
        int userChoice;
        System.out.println("\n=== Order resume ===\n");

        System.out.println("Customer : " + sale.getCustomer().getName() +"\n");
        displayOrder(sale.getSaleLines());

        System.out.println("1. Confirm the order");
        System.out.println("2. Delay the order");
        System.out.println("3. Cancel the order\n");

        System.out.print("Your choice : ");
        userChoice = InputUtils.userRangeIntInput(1,3);

        return switch (userChoice){
            case 1 -> TypeSale.DONE;
            case 2 -> TypeSale.CURRENT;
            case 3 -> TypeSale.CANCELED;
            default -> throw new IllegalStateException("Unexpected value: " + userChoice);
        };
    }

    public void displaySalesByArticle(){
        List<SaleLine> saleLines = selectAllSaleLines();
        List<Article> articles = articleService.getAllArticles();

        if(saleLines == null || articles == null)
            return;

        for(Article article : articles){
            System.out.println("Sales with the article \""+article.getName()+"\" : ");

            for (SaleLine line : saleLines){
                if(line.getArticle().equals(article))
                    System.out.println(line.getSale());
            }
        }
    }

    public void displaySalesByCustomer(){
        List<Customer> customers = customerService.selectAllCustomers();
        for (Customer customer : customers){
            System.out.println("Sales with the customer \""+customer.getName()+"\" : ");
            System.out.println(customer.getPurchasingHistory());
        }
    }

    public void displaySalesByPeriod(){
        List<Sale> sales = saleRepository.findAllSaleOrderByDate();
        LocalDate localDate = LocalDate.parse("1900-01-01");
        for (Sale sale : sales){
            if(!sale.getSaleDate().isEqual(localDate)){
                System.out.println("Sales with the period \""+sale.getSaleDate()+"\" : \n");
                localDate = sale.getSaleDate();
            }
            System.out.println(sale);
        }
    }

    private List<SaleLine> selectAllSaleLines(){
        List<SaleLine> sales = saleLineRepository.findAll(SaleLine.class);
        if(sales.isEmpty()){
            System.out.println("No sales");
            return null;
        }
        return sales;
    }

    private static void displayOrderFinalization(Customer currentCustomer, List<SaleLine> saleLines) {
        if(currentCustomer != null && !saleLines.isEmpty()){
            System.out.println("0. Next step");
        }
        else{
            System.out.println("0. Quit sale registration\n");
        }

        System.out.print("Your choice : ");
    }

    private static void displaySelectedArticles(List<SaleLine> saleLines) {
        if(saleLines.isEmpty())
            System.out.println("2. No articles selected");
        else {
            System.out.println("2. Articles selected : \n");
            displayOrder(saleLines);
        }
    }

    private static void displayOrder(List<SaleLine> saleLines) {
        double totalPrice;
        double totalOrderPrice = 0;
        System.out.println("Name\tQuantity\tPrice\tTotal price");
        for (SaleLine saleLine : saleLines){
            Article saleLineArticle = saleLine.getArticle();
            totalPrice = saleLine.getQuantity() * saleLineArticle.getPrice();
            totalOrderPrice += totalPrice;
            System.out.println(saleLineArticle.getName() + " " + saleLine.getQuantity() + " " + saleLineArticle.getPrice() + " " + totalPrice) ;
        }
        System.out.println("\nTotal for order : " + totalOrderPrice);
    }

    private static void displayCurrentCustomer(Customer currentCustomer) {
        if(currentCustomer == null)
            System.out.println("1. No customer selected");
        else
            System.out.println("1. Customer : "+ currentCustomer.getName());
    }
}
