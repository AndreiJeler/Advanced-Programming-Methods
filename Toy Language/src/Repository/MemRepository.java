package Repository;

import Model.ADTS.ADTException;
import Model.ADTS.MyList;
import Model.Program.PrgState;

import java.util.LinkedList;
import java.util.List;

public class MemRepository implements IRepository{
    private List<PrgState> list;

    public MemRepository(){
        list=new LinkedList<>();
    }

    @Override
    public PrgState getCrtPrg() throws Exception {
        return list.get(0);
    }

    @Override
    public void addPrg(PrgState prg) throws Exception {
        list.add(prg);
    }

    @Override
    public void logPrgStateExec(PrgState state) {

    }

    @Override
    public List<PrgState> getPrgList() {
        return list;
    }

    @Override
    public void setPrgList(List<PrgState> list) {
        this.list=list;
    }
}
