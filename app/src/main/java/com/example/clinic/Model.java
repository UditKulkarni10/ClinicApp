package com.example.clinic;

import java.util.ArrayList;

public class Model {
    private String title, description;
    private int img;

    public Model(){
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public static ArrayList<Model> initialModels() {
        ArrayList<Model> list = new ArrayList<>();

        Model model1 = new Model();
        Model model2 = new Model();
        Model model3 = new Model();
        model1.setTitle("Is this working: 1");
        model1.setDescription("description1");
        model2.setTitle("Is this not workinggggggg");
        model2.setDescription("description2");
        model3.setTitle("This makes me saaaadd");
        model3.setDescription("description3");
        list.add(model3);
        list.add(model1);
        list.add(model2);


        return list;
    }
}
