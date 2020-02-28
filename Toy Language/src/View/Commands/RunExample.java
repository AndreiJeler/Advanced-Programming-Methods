package View.Commands;

import Controller.Controller;

public class RunExample extends Command {
    private  Controller ctr;
    public RunExample(String k,String d,Controller c){
        super(k,d);
        ctr=c;
    }
    @Override
    public void execute() {
        try{

            ctr.allStep();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
