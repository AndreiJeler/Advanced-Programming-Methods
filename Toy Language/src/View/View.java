package View;

import Controller.Controller;
import Repository.IRepository;
import Repository.MemRepository;


public class View {
    static IRepository repo=new MemRepository();
    static Controller contrl=new Controller(repo);

    //public static void
}
