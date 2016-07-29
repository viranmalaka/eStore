/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Malaka
 */
public class CommonControllers {
    public static String convertIndex(long id, char c){
        return String.valueOf(c) + String.format("%05d", id);
    }
}
