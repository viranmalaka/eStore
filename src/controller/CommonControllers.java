/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.regex.Pattern;

/**
 *
 * @author Malaka
 */
public class CommonControllers {
    public static String convertIndex(long id, char c){
        return String.valueOf(c) + String.format("%05d", id);
    }
    
    public static boolean isName(String name){
        Pattern compile = Pattern.compile("^[\\p{L}\\s'.-]+$");
        return compile.matcher(name).matches();
    }
    
    public static boolean isTelephoneNumber(String num){
        Pattern compile = Pattern.compile("0[\\d]{2}-[\\d]{7}");
        return compile.matcher(num).matches();
    }
   
}
