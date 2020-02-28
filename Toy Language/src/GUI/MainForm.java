package GUI;

import Controller.Controller;
import Model.ADTS.MyHeap;
import Model.ADTS.MyIDictionary;
import Model.ADTS.MyIList;
import Model.Program.PrgState;
import Model.Statements.IStmt;
import Model.Values.StringValue;
import Model.Values.Value;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class MainForm implements Initializable {
    public TableView<Map.Entry<Integer, Integer>> lockT;
    public TableColumn<Map.Entry<Integer, Integer>, Integer> location;
    public TableColumn<Map.Entry<Integer, Integer>, Integer> value;
    @FXML
    private ListView<String> selectedPrg;
    @FXML
    private Button btn;
    @FXML
    private TextField nrPrgStates;
    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTable;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Integer> address;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Value> HValue;
    /*@FXML private TableView<MyIDictionary<Integer,Value>> heapTable;
    @FXML private TableColumn<MyIDictionary<Integer, Value>, Integer> address;
    @FXML private TableColumn<MyIDictionary<Integer, Value>, Value> HValue;*/

    @FXML
    private ListView<Value> outList;
    @FXML
    private ListView<StringValue> filesTable;
    @FXML
    private ListView<Integer> prgstatesList;
    @FXML
    private TableView<Map.Entry<String, Value>> symtbl;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> var_name;
    @FXML
    private TableColumn<Map.Entry<String, Value>, Value> SValue;
    @FXML
    private Text nrText;

    private Controller ctr;
   /* public void setController(Controller c){
        ctr=c;
        populateNr();
    }*/

    public void populateNr() {
        Integer n = ctr.nrPrgStates();
        nrPrgStates.setText(String.valueOf(n));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctr = RunForm.ctr;
        //nrPrgStates=new TextField();
      //  nrPrgStates.setText("sal");
       /* this.address.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.HValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        this.var_name.setCellValueFactory(new PropertyValueFactory<>("var_name"));
        this.SValue.setCellValueFactory(new PropertyValueFactory<>("value"));*/

        this.address.setCellValueFactory(p->new SimpleObjectProperty(p.getValue().getKey()));
        this.HValue.setCellValueFactory(p->new SimpleObjectProperty(p.getValue().getValue()));
        this.var_name.setCellValueFactory(p->new SimpleObjectProperty(p.getValue().getKey()));
        this.SValue.setCellValueFactory(p->new SimpleObjectProperty(p.getValue().getValue()));
        this.location.setCellValueFactory(p->new SimpleObjectProperty(p.getValue().getKey()));
        this.value.setCellValueFactory(p->new SimpleObjectProperty(p.getValue().getValue()));

        populatePrgLists();

        prgstatesList.setOnMouseClicked(mouseEvent -> {changePrg(getCurrentPrg());});
        prgstatesList.getSelectionModel().select(0);
        changePrg(getCurrentPrg());
    }

    public void populatePrgLists() {
        prgstatesList.setItems(ctr.getIDs());
        prgstatesList.refresh();
        nrPrgStates.setText(""+ctr.getIDs().size());
        nrText.setText(""+ctr.getIDs().size());
    }


    public void populateHeap(PrgState current) {
        ObservableList<Map.Entry<Integer, Value>> list = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Value> entry : current.getHeap().getAll()) {
            list.add(entry);
        }
        heapTable.setItems(list);
        heapTable.refresh();
    }

    public void populateSymTbl(PrgState current){
        ObservableList<Map.Entry<String, Value>> list = FXCollections.observableArrayList();
        for (Map.Entry<String, Value> entry : current.getSymTable().getAll()) {
            list.add(entry);
        }
        symtbl.setItems(list);
        symtbl.refresh();
    }

    public PrgState getCurrentPrg(){
        try {
            if (prgstatesList.getSelectionModel().getSelectedIndex() == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Choose a program state", ButtonType.OK);
                alert.show();
                return null;
            }
            int index = prgstatesList.getSelectionModel().getSelectedIndex();
            return ctr.getPrgWithIndex(index);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.show();
            prgstatesList.refresh();
        }
        return null;
    }

    public void populateOut(PrgState current){
        ObservableList<Value> out=FXCollections.observableArrayList();
        for(Value v:current.getOut().getAll()){
            out.add(v);
        }
        outList.setItems(out);
        outList.refresh();
    }

    public void populateFileT(PrgState current){
        ObservableList<StringValue> file=FXCollections.observableArrayList();
        for(StringValue v:current.getFileTable().getKeys()){
            file.add(v);
        }
        filesTable.setItems(file);
        filesTable.refresh();
    }

    public void populateStack(PrgState current){
        ObservableList<String> list=FXCollections.observableArrayList();
        for(IStmt s:current.getExeStack().getAll()){
            list.add(s.toString());
        }
        selectedPrg.setItems(list);
        selectedPrg.refresh();
    }

    public void populateLockT(PrgState current){
        ObservableList<Map.Entry<Integer, Integer>> list = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Integer> entry : current.getLockTable().getAll()) {
            list.add(entry);
        }
        lockT.setItems(list);
        lockT.refresh();
    }


    public void changePrg(PrgState current){
        if(current==null) return;
        //populateNr();
        populatePrgLists();
        populateStack(current);
        populateHeap(current);
        populateSymTbl(current);
        populateOut(current);
        populateFileT(current);
        populateLockT(current);
    }

    public void oneStep(){
        try{
            ctr.oneStepGUI();
            changePrg(getCurrentPrg());
        }
        catch (Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR,e.getMessage(),ButtonType.OK);
            //prgstatesList.refresh();
            alert.show();
        }
    }
}
