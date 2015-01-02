//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2014 uniCenta
//    http://www.unicenta.com
//
//    This file is part of uniCenta oPOS
//
//    uniCenta oPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   uniCenta oPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.util;

/**
 *
 * @author Mikel Irurita
 */
public class LuhnAlgorithm {
    
    /** Creates a new instance of LuhnAlgorithm */
    private LuhnAlgorithm() {
    }

    /**
     *
     * @param cardNumber
     * @return
     */
    public static boolean checkCC(String cardNumber){
        int sum = 0;

        int flip = 0;
        
        if ( !StringUtils.isNumber(cardNumber) ){
            return false;
        }
        
        for (int i = cardNumber.length() -1; i >= 0; i--) {
             int k = Character.digit(cardNumber.charAt(i), 10);
             flip ++;

             if ( flip % 2 == 0 ) {
                k *= 2;
                if (k > 9) {
                    k -= 9;
                }
            }
            sum += k;
        }
        return (sum % 10 == 0);
    }

//  Resolution of AMEX card issue
//  Mikel Iurata, Openbravo Thu Sep 16
//  Jack Gerrard, uniCenta Fri Sep 17
//    public static void main(String[] args) {
//        // Testing sample numbers
//
//        System.out.println(LuhnAlgorithm.checkCC("4111111111111111")); // Visa
//        System.out.println(LuhnAlgorithm.checkCC("5500000000000004")); // Master card
//        System.out.println(LuhnAlgorithm.checkCC("340000000000009")); // AMEX
//        System.out.println(LuhnAlgorithm.checkCC("30000000000004")); // Diners
//        System.out.println(LuhnAlgorithm.checkCC("30000000000004")); // Carte blanche
//        System.out.println(LuhnAlgorithm.checkCC("6011000000000004")); // Discover
//        System.out.println(LuhnAlgorithm.checkCC("201400000000009")); // EnRoute

}

