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
public class Simulator implements Runnable{      
    
    private char acc;
    private char pc;
    private Character current_command;
    private int current_command_index; 
    private long totalSteps;
    private InstructionSource src;
    private long maxSteps;
    private SimulatorGUI GUI;
    private boolean running;
    private boolean stop;
    
    private Character[] memory;
    
    public Simulator(InstructionSource Src, long MaxSteps, SimulatorGUI Gui){
        src = Src;
        GUI = Gui;
        reset(MaxSteps);
    }    
    
    public void reset(long MaxSteps){
        running = false;
        stop=false;
        memory = new Character[Constants.MAX_MEMORY];
        acc =(char)0;
        pc = (char)0;
        totalSteps = 0;
        current_command_index = -1;
        current_command = null;
        maxSteps = MaxSteps;
        
        for(int i=0; i<Constants.MAX_MEMORY; i++){
            memory[i] = null;
        }      
        
        if(GUI!=null){
            GUI.updateGUI(current_command, current_command_index, acc, pc, memory, totalSteps, running);
        }
    }
    
    public void run(){
        try{
            this.execute();
        }
        catch(Exception e){
            if(GUI!=null){
                GUI.error(e.getMessage());
                System.out.println(current_command_index);
            }
        }
    }
    
    public void setInstructionSource(InstructionSource s){
        this.src = s;
    }
    
    private static void waitFor(int milliseconds){
    	
    			try{
    				Thread.sleep(milliseconds);
    			}
    			catch(Exception e){
    				System.err.println("What's up now???");
    			}	    	
    }//waitFor()
    
    public void execute() throws Exception{    
        running = true;
        totalSteps = 0;
        while( nextStep() && (!stop) ){
            
            if(maxSteps > 0){
                if(totalSteps>=maxSteps)
                {
                    break;
                }
            }          
            waitFor(1000);
        }
        running = false;
        
        if(GUI!=null){
            GUI.updateGUI(current_command, current_command_index, acc, pc, memory, totalSteps, running);
        }
        //printStatus();
    }    
   
    
    public void executeCommand(Character command) throws Exception{
        
        if((int)command.charValue() < 4096){
            pc++;
            return;
        }
        
        char instruction = (char)(command.charValue() & Constants.instr_mask);
        char address    = (char)(command.charValue() & Constants.addr_mask);
        
        switch(instruction){
        
            case Constants.INSTR_LOAD       : load(address);break;
            case Constants.INSTR_STORE      : store(address);break;
            case Constants.INSTR_ADD        : add(address);break;
            case Constants.INSTR_SUBTRACT   : subtract(address);break;
            case Constants.INSTR_MULTIPLY   : multiply(address);break;
            case Constants.INSTR_DIVIDE     : divide(address);break;
            case Constants.INSTR_JUMP       : jump(address);break;
            case Constants.INSTR_JUMPZERO   : jumpzero(address);break;
            case Constants.INSTR_JUMPMSB    : jumpmsb(address);break;
            case Constants.INSTR_JUMPSUB    : jumpsub(address);break;
            case Constants.INSTR_RETURN     : ret(address);break;
            default:{throw new Exception("Unknown instruction: "+ ( Utilities.addLeadingZeroesTo16BitBinaryString(Integer.toBinaryString((int) instruction)) ) ); }
        }
        
    }    
    
    public boolean nextStep()throws Exception{        
        if(totalSteps>0){
            current_command_index = pc;
            current_command = memory[current_command_index];
        }
        else{
            loadProgramInMemory();
            current_command_index = 0;
            current_command = memory[0];
        }
        
        if(current_command == null){
                return false;
        }
        
        executeCommand(current_command);
        totalSteps++;            
        if(GUI!=null){
            GUI.updateGUI(current_command, current_command_index, acc, pc, memory, totalSteps, running);
        }
        //printStatus();
        return true;   
    }
    
    private void loadProgramInMemory(){
        Character[] instructions = src.getInstructions();
        for(int i=0; i<instructions.length; i++){
            memory[i] = instructions[i];
        }        
        //printMemory();
    }    
    
    private boolean checkAddress(char address)throws Exception{
        if((address >= 0) && (address<Constants.MAX_MEMORY)){
            return true;
        }
        else{
            throw new Exception("Address is out of range : "+address);
        }
    }
    
    
    private void load(char address)throws Exception{       
        if(checkAddress(address)){
            pc++;
            acc = memory[address].charValue();
        }
    }
    
    private void store(char address)throws Exception{        
        if(checkAddress(address)){
            pc++;
            memory[address] = new Character(acc);
        }
    }
    
    private void add(char address)throws Exception{
        if(checkAddress(address)){
            pc++;
            acc = (char)(acc + memory[address].charValue());
        }
    }
    
    private void subtract(char address)throws Exception{
        if(checkAddress(address)){
            pc++;
            acc = (char)(acc - memory[address].charValue());
        }
    }
    
    private void multiply(char address)throws Exception{
        if(checkAddress(address)){
            pc++;
            acc = (char)(acc * memory[address].charValue());
        }
    }
    
    private void divide(char address)throws Exception{
        if(checkAddress(address)){
            pc++;
            acc = (char)(acc / memory[address].charValue());
        }
    }
    
    private void jump(char address)throws Exception{
        if(checkAddress(address)){
            pc = address;
        }
    }
    
    private void jumpzero(char address)throws Exception{
        if(checkAddress(address)){
            if(acc == 0){
                pc = address;
            }
            else{
                pc++;
            }
        }
    }
    
    private void jumpmsb(char address)throws Exception{
        if(checkAddress(address)){
            char mask = (char) (1 << 15);
            if( ((acc & mask) >> 15) == 1 ){
                pc = address;
            }
            else{
                pc++;
            }
        }
    }
    
    private void jumpsub(char address)throws Exception{
        if(checkAddress(address)){
            memory[address] = new Character((char)(current_command_index + 1));
            pc = (char)(address+1);           
        }
    }
    
    private void ret(char address)throws Exception{
        if(checkAddress(address)){
           pc = memory[address].charValue();
        }
    }
    
    
    
    public int getACC(){
        return (int) acc;
    }
    
    public String getAccAsBinaryString(){
        return  Utilities.addLeadingZeroesTo16BitBinaryString(Integer.toBinaryString(acc));
    }
    
    public int getPC(){
        return (int) pc;
    }
    
    public int getCurrentCommandIndex(){
        return current_command_index;
    }
    
    public String getPcAsBinaryString(){
        return Utilities.addLeadingZeroesTo16BitBinaryString(Integer.toBinaryString(pc));
    }
    
    public long getTotalSteps(){
        return totalSteps;
    }
    
    
    public boolean isRunning(){
        return running;
    }
    
    public void terminateExecution(){
        stop = true;
    }
    
    public Character[] getMemory(){
        return memory;
    }
    
    
    
    
    
    public void printStatus(){
        System.out.println("Current instruction: "+Utilities.addLeadingZeroesTo16BitBinaryString(Integer.toBinaryString((int)(current_command)))+"\t Current index: "+(int)current_command_index
                            +"\t ACC: " +(int)acc+"\t PC: " +(int)pc +"\t TotalSteps: " + (long)totalSteps);
    }
    
    public void printMemory(){
        for(int i=0; i<memory.length; i++){
            if(memory[i]!=null){
                System.out.println("Memory["+i+"]: "+(int)memory[i].charValue());
            }            
        }
    }
    
    
    
    
    
    public static void main(String args[]){
               
        String[] lines = new String[6];
        lines[0] = new String("LOAD 4");
        lines[1] = new String("ADD 5");
        lines[2] = new String("STORE 4");
        lines[3] = new String("JUMP 1");
        lines[4] = new String("5");
        lines[5] = new String("2");
        
        try{
            Parser inter = new Parser();                       
            inter.initialize(lines);
            Simulator sim = new Simulator(inter, 20, null);
            sim.execute();
            sim.printMemory();
            /*sim.nextStep();
            sim.printMemory();
            sim.nextStep();
            sim.printMemory();
            sim.nextStep();
            sim.printMemory();
            sim.nextStep();
            sim.printMemory();
            sim.nextStep();
            sim.printMemory();*/
        }
        catch(Exception e){
            System.err.println(e.toString());
        }             
        
    }

}
