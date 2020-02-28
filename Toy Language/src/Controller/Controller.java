package Controller;

import Model.ADTS.*;
import Model.MyException;
import Model.Program.PrgState;
import Model.Statements.IStmt;
import Model.Types.IType;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.IRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.io.IOException;
import java.sql.Ref;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository rep){
        repo=rep;
    }
    /*public PrgState oneStep(PrgState state) throws Exception{
        MyIStack<IStmt> stk=state.getExeStack();
        if(stk.isEmpty()) throw new ADTException("stack is empty");
        IStmt crtStmt=stk.pop();
       // System.out.println(crtStmt);
        repo.logPrgStateExec();
        return crtStmt.execute(state);
    }
    public void allStep() throws Exception{
        PrgState prg=repo.getCrtPrg();
        System.out.print(prg.toString());
        repo.logPrgStateExec();
        while(!prg.getExeStack().isEmpty()){
            oneStep(prg);
            System.out.print(prg.toString());
            repo.logPrgStateExec();
            prg.getHeap().setContents(safeGarbageCollector(
                    getAddrFromSymTable(prg.getSymTable().getContents().values()),
                    prg.getHeap().getContents()));

            // repo.logPrgStateExec();
        }
        repo.logPrgStateExec();
    }*/
    public void addProgram(PrgState prg) throws Exception {
        this.repo.addPrg(prg);
    }


    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value>
            heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

   private Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value>
            heap){
        Map<Integer,Value> map= heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Map<Integer,Value> mcopy = new HashMap<>();
        mcopy.putAll(map);
        for(Map.Entry<Integer,Value> entry:mcopy.entrySet()){
            Integer key=entry.getKey();
            Value val=entry.getValue();
            while(val instanceof RefValue){
                RefValue ref=(RefValue) val;
                Integer addr=ref.getAddress();
                Value aux=heap.get(addr);
                map.put(addr,aux);
                val=aux;
            }
        }
        return map;
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream().filter(p->p.isNotCompleted()).collect(Collectors.toList());
    }


    void oneStepForAllPrg(List<PrgState> prgList) throws Exception{
        prgList.forEach(prg->repo.logPrgStateExec(prg));
        List<Callable<PrgState>> callList=prgList.stream().map((PrgState p)->(Callable<PrgState>)(()->{return p.oneStep();})).collect(Collectors.toList());
        List<PrgState> newPrgList=executor.invokeAll(callList).stream().
                map(future->{try {return future.get();}
                catch (Exception e) {
                    System.out.println(e); }
            return null;
        })
        .filter(p->p!=null).collect(Collectors.toList());
        prgList.addAll(newPrgList);
        prgList.forEach(prg->repo.logPrgStateExec(prg));
        repo.setPrgList(prgList);
    }
    public void allStep() throws Exception {
        executor= Executors.newFixedThreadPool(2);
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        while(prgList.size()>0){
            //garbage collector
            List addrs= prgList.stream().map(p->p.getSymTable().getContents().values()).map(c->getAddrFromSymTable(c)).flatMap(Collection::stream).collect(Collectors.toList());
            MyIHeap<Integer,Value> Heap=prgList.get(0).getHeap();
            Heap.setContents(safeGarbageCollector(addrs,Heap.getContents()));
            /*List aux=new ArrayList();
            for(PrgState prg:prgList){
                List a=getAddrFromSymTable(prg.getSymTable().getContents().values());
                aux.addAll(a);
            }

            prgList.get(0).getHeap().setContents(safeGarbageCollector(
                    getAddrFromSymTable(aux),
                    prgList.get(0).getHeap().getContents()));*/
            oneStepForAllPrg(prgList);
            prgList=removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdown();
        for(PrgState p:repo.getPrgList()){
            repo.logPrgStateExec(p);
        }
        repo.setPrgList(prgList);
    }

    void initialTypecheck() throws MyException {
            repo.getPrgList().get(0).getOriginalProgram().typecheck(new MyDictionary<String, IType>());
    }

    public int nrPrgStates(){
        return repo.getPrgList().size();
    }
    public ObservableList<Integer> getIDs(){
        ObservableList<Integer> list= FXCollections.observableArrayList();
        for(PrgState p:repo.getPrgList()){
            list.add(p.getId());
        }

        return list;
    }
    public PrgState getPrgWithIndex(int id){
        return repo.getPrgList().get(id);
    }

    public void oneStepGUI() throws Exception{
        executor= Executors.newFixedThreadPool(2);
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        repo.setPrgList(prgList);
            List addrs= prgList.stream().map(p->p.getSymTable().getContents().values()).map(c->getAddrFromSymTable(c)).flatMap(Collection::stream).collect(Collectors.toList());
            MyIHeap<Integer,Value> Heap=prgList.get(0).getHeap();
            Heap.setContents(safeGarbageCollector(addrs,Heap.getContents()));
            oneStepForAllPrg(prgList);
            //prgList=removeCompletedPrg(repo.getPrgList());
        executor.shutdown();
        prgList.forEach(prg->repo.logPrgStateExec(prg));
        repo.setPrgList(prgList);
    }
}
