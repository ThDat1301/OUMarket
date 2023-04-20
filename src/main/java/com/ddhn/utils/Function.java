/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddhn.utils;

/**
 *
 * @author truon
 */
public class Function {
    public static boolean isNumber(String str) {
        if (str == null) 
            return false;
        try {
            float num = Float.parseFloat(str);
        }
        catch(NumberFormatException nfe){
            return false;         
        }
        return true;
    }
}
