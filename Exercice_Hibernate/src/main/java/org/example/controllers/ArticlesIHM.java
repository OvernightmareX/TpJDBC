package org.example.controllers;

import org.example.services.ArticleService;

import static org.example.constants.Constants.MAX_ARTICLE_CRUD_CHOICE;
import static org.example.utils.InputUtils.userRangeIntInput;

public class ArticlesIHM {

    ArticleService articleService = new ArticleService();

    public void StartArticleIHM(){
        int userChoice;

        do {
            userChoice = displayArticleCRUDMenu();
            switch (userChoice) {
                case 1 -> articleService.createArticle();
                case 2 -> articleService.modificationArticle();
                case 3 -> articleService.deleteArticle();
                case 4 -> articleService.displayArticleByID();
                case 5 -> articleService.displayAllArticles();
                case 6 -> articleService.restockArticleByID();
            }
        } while (userChoice != 0);
    }

    public static int displayArticleCRUDMenu(){
        System.out.println("\n=== Article manager ===\n");
        System.out.println("1. Create");
        System.out.println("2. Modification");
        System.out.println("3. Delete");
        System.out.println("4. Display articles by ID");
        System.out.println("5. Display all articles");
        System.out.println("6. Restock an article");
        System.out.println("0. Quit article manager\n");

        System.out.print("Your choice :");
        return userRangeIntInput(0,MAX_ARTICLE_CRUD_CHOICE);
    }
}
