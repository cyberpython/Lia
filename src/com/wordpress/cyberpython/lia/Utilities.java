/*
 * Copyright (c) 2010 Georgios Migdos <cyberpython@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.wordpress.cyberpython.lia;

/**
 *
 * @author cyberpython
 */
public class Utilities {
    
    public static String addLeadingZeroesTo16BitBinaryString(String binaryValue){
        
        int length = binaryValue.length();
        
        while(binaryValue.length() < 16){
            binaryValue = "0"+binaryValue;
        }
        
        return binaryValue;
        
    }
    
    public static String decodeInstruction(Character c){
        if(c == null){
            return "<undefined>";
        }
        char v = c.charValue();
        if(v < 4096){
            return String.valueOf( (int)v );
        }
        else {

            char instruction = (char) (v & Constants.instr_mask);
            char address = (char) (v & Constants.addr_mask);

            switch (instruction) {

                case Constants.INSTR_LOAD:
                    return "LOAD "+String.valueOf((int)address );
                case Constants.INSTR_STORE:
                    return "STORE "+String.valueOf((int)address );
                case Constants.INSTR_ADD:
                    return "ADD "+String.valueOf((int)address );
                case Constants.INSTR_SUBTRACT:
                    return "SUBTRACT "+String.valueOf((int)address );
                case Constants.INSTR_MULTIPLY:
                    return "MULTIPLY "+String.valueOf((int)address );
                case Constants.INSTR_DIVIDE:
                    return "DIVIDE "+String.valueOf((int)address );
                case Constants.INSTR_JUMP:
                    return "JUMP "+String.valueOf((int)address );
                case Constants.INSTR_JUMPZERO:
                    return "JUMPZERO "+String.valueOf((int)address );
                case Constants.INSTR_JUMPMSB:
                    return "JUMPMSB "+String.valueOf((int)address );
                case Constants.INSTR_JUMPSUB:
                    return "JUMPSUB "+String.valueOf((int)address );
                case Constants.INSTR_RETURN:
                    return "RETURN "+String.valueOf((int)address );
                default: {
                    return "<unknown>";
                }
            }

        }
    }   
    
    
    public static String to16BitBinaryString(Character c){
        
        String binaryValue = Integer.toBinaryString( (int)c.charValue() );
        
        int length = binaryValue.length();
        
        while(binaryValue.length() < 16){
            binaryValue = "0"+binaryValue;
        }
        
        return binaryValue;
        
    }

}
