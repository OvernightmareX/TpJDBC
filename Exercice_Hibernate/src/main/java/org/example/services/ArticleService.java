package org.example.services;

import org.example.entities.Article;
import org.example.entities.ElectronicArticle;
import org.example.entities.FashionArticle;
import org.example.entities.FoodArticle;
import org.example.repositories.ArticleRepository;
import org.example.utils.InputUtils;
import org.example.utils.TypeArticle;
import org.example.utils.TypeFashionArticle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleService {
    private ArticleRepository articleRepository = new ArticleRepository();

    public void createArticle(){
        System.out.println("=== Article creation ===\n");
        TypeArticle typeArticle = selectArticleType();

        Article article = createSpecificArticleType(typeArticle);
        setBaseArticleDetails(article);
        setSpecificArticleDetails(article);

        saveArticle(article);
    }

    private static Article createSpecificArticleType(TypeArticle typeArticle) {
        return switch (typeArticle){
            case FOOD -> new FoodArticle();
            case ELECTRONIC -> new ElectronicArticle();
            case FASHION -> new FashionArticle();
        };
    }

    public void restockArticleByID(){
        System.out.println("\n=== Article to restock ===\n");
        displayAllArticles();

        Article articleSelected = getArticleByID();
        if(articleSelected == null)
            return;

        System.out.println(articleSelected);
        System.out.print("What's the new quantity : ");
        articleSelected.setQuantity(InputUtils.userIntInput());
        saveArticle(articleSelected);
    }

    public void saveArticle(Article article){
        articleRepository.save(article);
    }

    public void deleteArticle(){
        System.out.println("=== Article modification ===\n");

        displayAllArticles();

        Article articleSelected = getArticleByID();
        if(articleSelected == null)
            return;

        articleRepository.delete(articleSelected);
    }

    public void modifyArticle(){
        System.out.println("=== Article modification ===\n");

        displayAllArticles();

        Article articleSelected = getArticleByID();
        if(articleSelected == null)
            return;

        int userInput;
        do {
            System.out.println("\n=== Selected article infos ===");
            displayArticleModificationMenu(articleSelected);
            System.out.println("0. Quit modifications\n");
            System.out.print("Your choice : ");
            userInput = InputUtils.userRangeIntInput(0, 6);

            if(userInput != 0)
                modifyArticleValue(articleSelected, userInput);

        }while (userInput != 0);
    }

    public void modifyArticleValue(Article article, int inputToUpdate){
        switch (inputToUpdate) {
            case 1 -> {
                System.out.print("Please enter article's name: ");
                article.setName(InputUtils.userStringInput());
            }
            case 2 -> {
                System.out.print("Please enter article's description: ");
                article.setDescription(InputUtils.userStringInput());
            }
            case 3 -> {
                System.out.print("Please enter article's price: ");
                article.setPrice(InputUtils.userDoubleInput());
            }
            case 4 -> {
                System.out.print("Please enter article's quantity: ");
                article.setQuantity(InputUtils.userIntInput());
                article.setRestockDate(LocalDateTime.now());
            }
            case 5 -> {
                modifySpecificArticleValue(article);
            }
            case 6 -> {
                if (article instanceof FashionArticle fashionArticle) {
                    System.out.println("6. Please enter type: " + fashionArticle.getType());
                    fashionArticle.setType(selectFashionArticleType());
                }
            }
            default -> throw new IllegalArgumentException("Invalid input to update");
        }
        saveArticle(article);
    }

    private void modifySpecificArticleValue(Article article) {
        if (article instanceof FoodArticle foodArticle) {
            System.out.println("5. Please enter expiration date: " + foodArticle.getExpirationDate());
            foodArticle.setExpirationDate(InputUtils.userDateInput());
        } else if (article instanceof ElectronicArticle electronicArticle) {
            System.out.println("5. Please enter battery duration: " + electronicArticle.getBatteryDuration());
            electronicArticle.setBatteryDuration(InputUtils.userIntInput());
        } else if (article instanceof FashionArticle fashionArticle) {
            System.out.println("5. Please enter size: " + fashionArticle.getSize());
            fashionArticle.setSize(InputUtils.userIntInput());
        }
    }

    public TypeFashionArticle selectFashionArticleType(){
        System.out.println("Which category ?");
        System.out.println("1. Men");
        System.out.println("2. Women");
        System.out.println("3. Child");

        int userInput = InputUtils.userRangeIntInput(1,3);
        return switch (userInput){
            case 1 -> TypeFashionArticle.MEN;
            case 2 -> TypeFashionArticle.WOMEN;
            case 3 -> TypeFashionArticle.CHILD;
            default -> throw new IllegalArgumentException("Invalid fashion article typ");
        };
    }

    public TypeArticle selectArticleType(){
        System.out.println("What kind of article ?");
        System.out.println("1. Electronic article");
        System.out.println("2. Fashion article");
        System.out.println("3. Food article");

        System.out.print("Your choice : ");
        int userInput = InputUtils.userRangeIntInput(1,3);
        return switch (userInput){
            case 1 -> TypeArticle.ELECTRONIC;
            case 2 -> TypeArticle.FASHION;
            case 3 -> TypeArticle.FOOD;
            default -> throw new IllegalArgumentException("Invalid article type");
        };
    }

    public Article getArticleByID(){
        System.out.println("Please enter article's ID : ");
        int userInput = InputUtils.userIntInput();
        Article article = articleRepository.findById(Article.class, userInput);

        if(article == null){
            System.out.println("Article's ID not found");
            return null;
        }
        return article;
    }

    public List<Article> getAllArticles(){
        List<Article> articles = articleRepository.findAll(Article.class);

        if(articles.isEmpty()){
            System.out.println("No articles found");
            return null;
        }

        return articles;
    }

    public long getArticleCount(){
        return articleRepository.count(Article.class);
    }

    private void setSpecificArticleDetails(Article article) {
        if (article instanceof FoodArticle) {
            setFoodArticleDetails((FoodArticle) article);
        } else if (article instanceof ElectronicArticle) {
            setElectronicArticleDetails((ElectronicArticle) article);
        } else if (article instanceof FashionArticle) {
            setFashionArticleDetails((FashionArticle) article);
        }
    }

    public void setBaseArticleDetails(Article article){
        System.out.print("Please enter article's name : ");
        article.setName(InputUtils.userStringInput());
        System.out.print("Please enter article's description : ");
        article.setDescription(InputUtils.userStringInput());
        System.out.print("Please enter article's price : ");
        article.setPrice(InputUtils.userDoubleInput());
        System.out.print("Please enter article's quantity : ");
        article.setQuantity(InputUtils.userIntInput());
        article.setSaleLines(new ArrayList<>());
    }

    public void setFoodArticleDetails(FoodArticle article){
        System.out.print("Please enter article's expiration date : ");
        article.setExpirationDate(InputUtils.userDateInput());
    }

    public void setElectronicArticleDetails(ElectronicArticle article){
        System.out.print("Please enter article's battery duration : ");
        article.setBatteryDuration(InputUtils.userIntInput());
    }

    public void setFashionArticleDetails(FashionArticle article){
        System.out.print("Please enter article's size : ");
        article.setSize(InputUtils.userIntInput());
        article.setType(selectFashionArticleType());
    }

    public void displayArticleByID(){
        Article article = getArticleByID();
        if(article != null)
            System.out.println(article);
    }

    public void displayAllArticles(){
        List<Article> articles = getAllArticles();
        if(articles == null)
            return;

        for(Article article : articles){
            System.out.println(article);
        }
    }

    private static void displayArticleModificationMenu(Article articleSelected) {
        displayBaseArticleModificationMenu(articleSelected);
        displaySpecificArticleModificationMenu(articleSelected);
    }

    public static void displayBaseArticleModificationMenu(Article article){
        System.out.println("\n1. Name : " + article.getName());
        System.out.println("2. Description : " + article.getDescription());
        System.out.println("3. Price : " + article.getPrice());
        System.out.println("4. Quantity : " + article.getQuantity());
    }

    public static void displaySpecificArticleModificationMenu(Article article){
        if (article.getClass().equals(FoodArticle.class)) {
            System.out.println("5. Expiration date : " + ((FoodArticle) article).getExpirationDate());
        } else if (article.getClass().equals(ElectronicArticle.class)) {
            System.out.println("5. Battery duration : " + ((ElectronicArticle) article).getBatteryDuration());
        } else if (article.getClass().equals(FashionArticle.class)) {
            System.out.println("5. Size : " + ((FashionArticle) article).getSize());
            System.out.println("6. Type : " + ((FashionArticle) article).getType());
        }
    }



}
