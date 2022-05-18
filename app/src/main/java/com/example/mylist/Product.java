package com.example.mylist;

import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class Product {
    private String Name, Price;
    private Product[] allProducts = new Product[20];

    public Product(){}

    public Product(String name, String price) {
        Name = name;
        Price = price;
    }

    public boolean addProduct(String name, String price){
        for (int i = 0; i < allProducts.length; i++) {
            if(this.allProducts[i] == null){
                this.allProducts[i] = new Product(name, price);
                return true;
            }
        }
        return  false;
    }

    public boolean removeProduct(String name, String price){
        for (int i = 0; i < allProducts.length; i++) {
            if(allProducts[i].getName().compareTo(name) == 0 && allProducts[i].getPrice().compareTo(price) == 0){
                removeElement(allProducts, i);
                return true;
            }
        }
        return  false;
    }

    public void removeElement(Product[] allProducts, int i) {
        Product[] temp = new Product[allProducts.length];

        for (int j = 0; j < allProducts.length; j++) {
            if(j != i){
                temp[j] = allProducts[j];
            }
        }
        for (int j = 0; j < allProducts.length; j++) {
                allProducts[j] = null;
        }

        int count = 0;
        for (int j = 0; j < allProducts.length; j++) {
            if(temp[j] != null) {
                allProducts[count] = temp[j];
                count++;
            }
        }
    }

    public void clear() {
        for (int i = 0; i < allProducts.length; i++) {
            allProducts[i] = null;
        }
    }

    public Product[] getAllProducts(){
        return allProducts;
    }

    public ArrayList<String> getAllProductsString() {
        ArrayList<String> str = new ArrayList<String>(Arrays.asList());
        for (int i = 0; i < allProducts.length; i++) {
            if(allProducts[i] != null){
               str.add(allProducts[i].getName() + ": " + allProducts[i].getPrice());
            }
        }
        return str;
    }

    public String getName() {
        return this.Name;
    }

    public String getNameById(int index) {
        return allProducts[index].Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return this.Price;
    }

    public String getPriceById(int index) {
        return allProducts[index].Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

}
