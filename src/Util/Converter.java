/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Judah
 */
public class Converter {

    private final Map<Character, Integer> dictionary;
//    private final Map<Integer, Character> dictionary2;
    private final char[] singleLeter;

    private static Converter instance = null;

    public static Converter getInstance() {
        if (instance == null) {
            instance = new Converter();
        }
        return instance;
    }

    protected Converter() {
        this.dictionary = new HashMap<>();

        this.dictionary.put('I', 1);
        this.dictionary.put('V', 5);
        this.dictionary.put('X', 10);
        this.dictionary.put('L', 50);
        this.dictionary.put('C', 100);
        this.dictionary.put('D', 500);
        this.dictionary.put('M', 1000);
        
//        this.dictionary2 = new HashMap<>();
//        
//        this.dictionary2.put(1,'I');
//        this.dictionary2.put(5,'V');
//        this.dictionary2.put(10,'X');
//        this.dictionary2.put(50,'L');
//        this.dictionary2.put(100,'C');
//        this.dictionary2.put(500,'D');
//        this.dictionary2.put(1000,'M');

        this.singleLeter = new char[]{'V', 'L', 'D'};
    }

    public int intArabicNumeralsForCharRomanNumerals(char charRomanNumeral) throws Exception {
        charRomanNumeral = Character.toUpperCase(charRomanNumeral);
        int result = (dictionary.get(charRomanNumeral) != null) ? dictionary.get(charRomanNumeral) : -1;
        if (result == -1) {
            throw new Exception("Invalid Roman Numeral!");
        }
        return result;
    }

    public int intArabicNumeralsForStringRomanNumerals(String stringRomanNumerals) throws Exception {

        if (stringRomanNumerals == null || stringRomanNumerals.length() == 0) {
            throw new Exception("Invalid Roman Numerals!");
        }
        if (stringRomanNumerals.length() == 1) {
            return intArabicNumeralsForCharRomanNumerals(stringRomanNumerals.charAt(0));
        }

        int result = 0;
        boolean delaySum = false;
        int repeatedLetter = 0;
        for (int index = 1; index < stringRomanNumerals.length(); index++) {
            char last = stringRomanNumerals.charAt(index - 1);
            char current = stringRomanNumerals.charAt(index);
            if (index == 1) {
                result = intArabicNumeralsForCharRomanNumerals(last);
            }
            if (booleanCheckIfCanSubtract(last, current)) {
                if(index==1){
                    result += intArabicNumeralsForCharRomanNumerals(current) - 2*intArabicNumeralsForCharRomanNumerals(last);
                }else{
                    if(delaySum){
                        delaySum=false;
                        result += intArabicNumeralsForCharRomanNumerals(current) - intArabicNumeralsForCharRomanNumerals(last);
                    }else{
                        result -= intArabicNumeralsForCharRomanNumerals(last);
                    }
                }
//                if (delaySum) {
//                    delaySum = false;
//                    result += intArabicNumeralsForCharRomanNumerals(stringRomanNumerals.charAt(index - 2));
//                }

            } else if (booleanCheckIfCanSum(stringRomanNumerals, index)) {
                result += intArabicNumeralsForCharRomanNumerals(current);// + intArabicNumeralsForCharRomanNumerals(last);
            } else {
                //lastResult=result;
                delaySum = true;
            }
            if (last == current) {
                if (repeatedLetter == 0) {
                    repeatedLetter++;
                }
                repeatedLetter++;
                checkRepeatedLetter(current, repeatedLetter);
            } else {
                repeatedLetter = 0;
            }
//            checkIfAlreadyExistNumber(stringRomanNumerals.substring(0, current+1),result);
        }

//        checkIfAlreadyExistNumber(stringRomanNumerals,result);
        
        return result;
    }

    private void checkRepeatedLetter(char letter, int number) throws Exception {
        letter = Character.toUpperCase(letter);
        if (number > 3) {
            throw new Exception("Invalid Roman Numerals!");
        }
        for (int index = 0; index < singleLeter.length; index++) {
            char currentLetter = singleLeter[index];
            if (currentLetter == letter && number > 1) {
                throw new Exception("Invalid Roman Numerals!");
            }
        }
    }

    private boolean booleanCheckIfCanSubtract(char last, char current) throws Exception {
        int intLast = intArabicNumeralsForCharRomanNumerals(last);
        int intCurrent = intArabicNumeralsForCharRomanNumerals(current);
        boolean result = (intLast < intCurrent);
        if (result) {
            if (intCurrent - intLast <= intCurrent / 2|| intLast<intCurrent/10) {
                throw new Exception("Invalid Roman Numerals!");
            }
        }
        return result;
    }

    private boolean booleanCheckIfCanSum(char last,char current, char next) throws Exception {
        int intLast = intArabicNumeralsForCharRomanNumerals(last);
        int intCurrent = intArabicNumeralsForCharRomanNumerals(current);
        int intNext = intArabicNumeralsForCharRomanNumerals(next);
        if (intNext > intCurrent) {
            if(intLast<intNext){
                throw new Exception("Invalid Roman Numerals!");
            }
            return false;
        }
        return true;
    }
    
    private void checkIfAlreadyExistNumber(String number,int value) throws Exception {
        
        if(number.length()==1){
            return;
        }
        
        for (int index=0; index<number.length(); index++) {
            if(this.dictionary.containsValue(value)){
                throw new Exception("Invalid Roman Numerals!");
            }
        }
    }

    private boolean booleanCheckIfCanSum(String stringRomanNumerals, int index) throws Exception {
        if (stringRomanNumerals.length() > index + 1) {
            return booleanCheckIfCanSum(stringRomanNumerals.charAt(index - 1), stringRomanNumerals.charAt(index), stringRomanNumerals.charAt(index + 1));
        } else {
            return true;
        }
    }
}
