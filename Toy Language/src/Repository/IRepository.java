package Repository;

import Model.ADTS.ADTException;
import Model.Program.PrgState;

import java.util.List;

public interface IRepository {
    PrgState getCrtPrg() throws Exception;
    void addPrg(PrgState prg) throws Exception;
    void logPrgStateExec(PrgState state);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> list);
}
