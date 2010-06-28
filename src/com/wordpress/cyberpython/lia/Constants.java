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
public class Constants {
    
    public static char instr_mask = (char)(15 << 12);
    public static char addr_mask = (char)4095;
    
    public static final char INSTR_LOAD       = (char) ( 1 << 12);
    public static final char INSTR_STORE      = (char) ( 2 << 12);
    public static final char INSTR_ADD        = (char) ( 3 << 12);
    public static final char INSTR_SUBTRACT   = (char) ( 4 << 12);
    public static final char INSTR_MULTIPLY   = (char) ( 5 << 12);
    public static final char INSTR_DIVIDE     = (char) ( 6 << 12);
    public static final char INSTR_JUMP       = (char) ( 7 << 12);
    public static final char INSTR_JUMPZERO   = (char) ( 8 << 12);
    public static final char INSTR_JUMPMSB    = (char) ( 9 << 12);
    public static final char INSTR_JUMPSUB    = (char) (10 << 12);
    public static final char INSTR_RETURN     = (char) (11 << 12);
    
    public static final int LOAD      =  1;
    public static final int STORE     =  2;
    public static final int ADD       =  3;
    public static final int SUBTRACT  =  4;
    public static final int MULTIPLY  =  5;
    public static final int DIVIDE    =  6;
    public static final int JUMP      =  7;
    public static final int JUMPZERO  =  8;
    public static final int JUMPMSB   =  9;
    public static final int JUMPSUB   = 10;
    public static final int RETURN    = 11; 
    
     public static final char MAX_MEMORY    = (char)4096; 

}
