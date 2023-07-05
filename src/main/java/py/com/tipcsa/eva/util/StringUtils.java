/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.tipcsa.eva.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jose
 */
public class StringUtils {
    
	
	
	private static NumberFormat formatter = new DecimalFormat("###,###.##");
	public static boolean controlCaracteresNumericos(String textNumberField) {
		
		if (!textNumberField.trim().equals("") && textNumberField != null) {
			textNumberField = textNumberField.replace(",",".");
			if (! isDoubleNumeric(textNumberField)) {				
				return false;
			} else {				
				return true;
			}
			
		} 
		
		return false;
	}
    
    public static boolean isDoubleNumeric(String cadena){
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	
	/**
     * Agrega CEROS a la izquierda y la derecha de number si es necesario,
     * hasta alcanzar width digitos con dec decimales.
     * 
     * @param number
     * @param width
     * @param dec
     * @return
     */
    public static String padDouble(double number, int width, int dec) {
        NumberFormat formatter = new DecimalFormat("#########.##");
        String cadena = "" + formatter.format(number);
        StringTokenizer token = new StringTokenizer(cadena, ".");
        String entero = token.nextToken();
        String decimal = "";
        if(token.hasMoreTokens()) {
            decimal = token.nextToken();
        }
        int cont = width - entero.length() - dec;
        for(int i = 0; i < cont; i++)
            entero = "0" + entero;

        int cola = decimal.length();
        for(int j = 0; j < dec - cola; j++)
            decimal += "0";

        if(decimal.length() > 2) {
            decimal = decimal.substring(0, 2);
        }

        return (entero + decimal);
    }

    /**
     * Agrega CEROS a la izquierda de un number hasta completar width digitos
     * 
     * @param number
     * @param width
     * @return
     */
    public static String ZeroPad(int number, int width) {
        StringBuilder result = new StringBuilder("");

        for(int i=0; i<width-Integer.toString(number).length(); i++)
            result.append("0");
        
        result.append(Integer.toString(number));

        return result.toString();
    }

    /**
     * Agrega CEROS a la izquierda de un number hasta completar width digitos
     *
     * @param number
     * @param width
     * @return
     */
    public static String ZeroPad(long number, int width) {

        StringBuilder result = new StringBuilder("");

        for(int i=0; i<width-Long.toString(number).length(); i++)
            result.append("0");

        result.append(Long.toString(number));

        return result.toString();
    }

    /**
     * Agrega CEROS a la izquierda de un number hasta completar width digitos
     *
     * @param number
     * @param width
     * @return
     */
    public static String ZeroPad(double number, int width) {

        StringBuilder result = new StringBuilder("");

        for(int i=0; i<width-Double.toString(number).length(); i++)
            result.append("0");

        result.append(Double.toString(number));

        return result.toString();
    }

    /**
     * Agrega ESPACIOS a la derecha de una cadena hasta completar width digitos
     * 
     * @param str
     * @param width
     * @return
     */
    public static String padRight(String str, int width) {
        String s;

        if(str == null) {
            s = " ";
        } else {
            if(str.equals("")) {
                s = " ";
            } else {
                s = str;
            }
        }
        return String.format("%1$-" + width + "s", s);
    }
    
    
    public static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
    
    public static boolean formatoEmail(String Cadena) {
    	Pattern pat = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
    	Matcher mather = pat.matcher(Cadena);
    	if (mather.find() == true) {
           return true;
        } else {
            return false;
        }
    }
    
    
    
    
    
    public static String  colocarFormatoCI(String cadena) {
		
		if (StringUtils.isDoubleNumeric(cadena)){
			return cadena = formatter.format(Double.parseDouble(cadena));
		}else {
			return cadena;
		}
	}
    
    
    
    
    
    
    
    
    
}
