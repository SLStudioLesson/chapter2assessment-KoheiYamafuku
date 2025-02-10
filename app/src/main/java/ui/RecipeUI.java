package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import data.RecipeFileHandler;

public class RecipeUI {
    private BufferedReader reader;
    private RecipeFileHandler fileHandler;

    public RecipeUI() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        fileHandler = new RecipeFileHandler();
    }

    public RecipeUI(BufferedReader reader, RecipeFileHandler fileHandler) {
        this.reader = reader;
        this.fileHandler = fileHandler;
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println();
                System.out.println("Main Menu:");
                System.out.println("1: Display Recipes");
                System.out.println("2: Add New Recipe");
                System.out.println("3: Search Recipe");
                System.out.println("4: Exit Application");
                System.out.print("Please choose an option: ");

                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        // 設問1: 一覧表示機能
                        displayRecipes();
                        break;
                    case "2":
                        // 設問2: 新規登録機能
                        addNewRecipe();
                        break;
                    case "3":
                        // 設問3: 検索機能
                        searchRecipe();
                        break;
                    case "4":
                        System.out.println("Exit the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error reading input from user: " + e.getMessage());
            }
        }
    }

    /**
     * 設問1: 一覧表示機能
     * RecipeFileHandlerから読み込んだレシピデータを整形してコンソールに表示します。
     */
    private void displayRecipes() {
        //レシピを出力するためのlistを用意
        ArrayList<String> list = new ArrayList<>();
        //読み込んだレシピをリストで返してくれるメソッドを代入
        list = fileHandler.readRecipes();

        System.out.println("Recipes:");
        if (list != null) {
            for (String ans : list) {
                System.out.println("-----------------------------------");
                //,で切り取って格納する
                String[] array = ans.split(",");
                //array[0]がレシピの名前、それ以上の値がレシピのグザイになるので
                //繰り返し処理を入れ子して出力
                System.out.println("Reciple Name: " + array[0]);
                System.out.print("Main Ingredients: ");
                for (int i = 1; i < array.length; i++) {
                    System.out.print(array[i] + ",");
                }
                System.out.println();
            }
        } else {
            System.out.println(" No recipes available.");
        }
    }

    /**
     * 設問2: 新規登録機能
     * ユーザーからレシピ名と主な材料を入力させ、RecipeFileHandlerを使用してrecipes.txtに新しいレシピを追加します。
     *
     * @throws java.io.IOException 入出力が受け付けられない
     */
    private void addNewRecipe() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter recipe name: ");
        String recipeName = reader.readLine();
        System.out.print("Enter main ingredients (comma separated): ");
        String ingredients = reader.readLine();
        
        //addRecipeに渡して、ファイルに書き込む
        fileHandler.addRecipe(recipeName, ingredients);
    }

    /**
     * 設問3: 検索機能
     * ユーザーから検索クエリを入力させ、そのクエリに基づいてレシピを検索し、一致するレシピをコンソールに表示します。
     *
     * @throws java.io.IOException 入出力が受け付けられない
     */
    private void searchRecipe() throws IOException {
        //ファイルをリストに読み込ませる
        ArrayList<String> list = new ArrayList<>();
        list = fileHandler.readRecipes();

        //コンソールへの書き込み部分
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter search query (e.g., 'name=Tomato&ingredient=Garlic'): ");
        String line = reader.readLine();
        
        String[] array = line.split("=");
        String[] keyword = array[1].split("&");
        //何も表示されなかったら、このカウントが０のまま
        int count = 0;
        System.out.println("Search Results:");
        
        /*for文で回しながら、一致する結果を探す。
        入れ子してlistがiの時にキーワード配列を周回して探していく
        */
        
        for(int i = 0; i < list.size(); i++){
           for(int y = 0; y < keyword.length; y++){

            if(list.get(i).contains(keyword[y])){
                System.out.println(list.get(i));
                count++;
                break;
            }
           
           }
        }
         //何も探せなかった場合表示するif文
         if(count == 0){
            System.out.println("No recipes found matching the criteria.");
        }
    }

}
