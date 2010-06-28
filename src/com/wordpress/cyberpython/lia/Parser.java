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
public class Parser implements InstructionSource {
    
    private String[] code;
    private Character[] instructions;
    private int size;
    
    public Parser(){
        
    }
    
    
    public void initialize(String[] Code) throws Exception{
        code = Code;
        int length = code.length;
        instructions = new Character[Constants.MAX_MEMORY];
        
        for(int i =0; i<instructions.length; i++){
            instructions[i] = null;
        }
        
        size = length;
        for(int i = 0; i<length; i++){
            instructions[i] = interpret(code[i]);            
        }
    }
    
    private Character interpret(String input)throws Exception{
        
        input = input.trim();
        if(input.length() == 0){
            return null;
        }
        
        input = input.toUpperCase();
        String[] tokens = input.split("\\s+");               
        
        if(tokens.length==0){
            return null;
        }
        
        if((tokens.length>2) ){
            throw new Exception("Invalid command : "+input);
        }
        
        if(tokens.length == 1){            
            return new Character((char)(Integer.parseInt(tokens[0])));
        }
        
        char instruction;
        if(tokens[0].equals("LOAD")){
            instruction = Constants.INSTR_LOAD;
        }
        else if(tokens[0].equals("STORE")){
            instruction = Constants.INSTR_STORE;
        }
        else if(tokens[0].equals("ADD")){
            instruction = Constants.INSTR_ADD;
        }
        else if(tokens[0].equals("SUBTRACT")){
            instruction = Constants.INSTR_SUBTRACT;
        }
        else if(tokens[0].equals("MULTIPLY")){
            instruction = Constants.INSTR_MULTIPLY;
        }
        else if(tokens[0].equals("DIVIDE")){
            instruction = Constants.INSTR_DIVIDE;
        }
        else if(tokens[0].equals("JUMP")){
            instruction = Constants.INSTR_JUMP;
        }
        else if(tokens[0].equals("JUMPZERO")){
            instruction = Constants.INSTR_JUMPZERO;
        }
        else if(tokens[0].equals("JUMPMSB")){
            instruction = Constants.INSTR_JUMPMSB;
        }
        else if(tokens[0].equals("JUMPSUB")){
            instruction = Constants.INSTR_JUMPSUB;
        }
        else if(tokens[0].equals("RETURN")){
            instruction = Constants.INSTR_RETURN;
        }
        else{
            throw new Exception("Invalid Instruction: "+tokens[0]);
        }
        
        char address = (char)(Integer.parseInt(tokens[1]));
        
        Character result = new Character( (char)(instruction + address) );
                           
        return result;
    }
    
    public Character getInstruction(int index) throws Exception{
        if((index >= 0) && (index<size) ){
            return instructions[index];
        }
        else{
            throw new Exception("Retrieving next instruction - Index out of bounds: "+index);
        }
    }
    
    public Character[] getInstructions(){
       return this.instructions;
    }
    
    
    
    
    //Test method:
    public static void main(String[] args){
    
        String[] lines = new String[5];
        lines[0] = new String("LOAD 3");
        lines[1] = new String("ADD 4");
        lines[2] = new String("STORE 3");
        lines[3] = new String("5");
        lines[4] = new String("2");
        
        try{
            Parser inter = new Parser();
            inter.initialize(lines);
            Character[] instr = inter.getInstructions();
            for(int i=0; i<instr.length; i++){
                System.out.print(Utilities.addLeadingZeroesTo16BitBinaryString(Integer.toBinaryString(  instr[i].charValue() )) );
                System.out.print("\t");
                System.out.println(lines[i]);
            }            
        }
        catch(Exception e){
            System.err.println(e.toString());
        }       
        
    }
    

}
