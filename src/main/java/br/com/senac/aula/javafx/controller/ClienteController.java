package br.com.senac.aula.javafx.controller;

import br.com.senac.aula.javafx.model.Cliente;
import br.com.senac.aula.javafx.services.ClienteService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.List;
// Teste 2
// teste de pull
@Component
@FxmlView("/main.fxml")
public class ClienteController {

    @FXML
    private TextField nome;

    @FXML
    private TextField documento;

    @FXML
    private TableView<Cliente> tabelaClientes;

    @FXML
    private TableColumn<Cliente, String> colunaNome;

    @FXML
    private TableColumn<Cliente, String> colunaDocumento;

    private int index = -1;


    @FXML
    public void initialize() {
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));

        this.carregarListaClientes();

        // alteração para teste de commit

        tabelaClientes.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Cliente cli = tabelaClientes.getSelectionModel().getSelectedItem();
                    nome.setText(cli.getNome());
                    documento.setText(cli.getDocumento());

                    index = cli.getId();

                    documento.setDisable(true);
                    // alteração para teste de commit
                }
            }
        });
    }

    public void executarOk(){
        /*
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ALerta");
        alert.setHeaderText("Nome: " + nome.getText() + " Documento: " + documento.getText());
        alert.show();
         */

        Cliente cli = new Cliente();
        cli.setDocumento(documento.getText());
        cli.setNome(nome.getText());

        // atualiza item - resetar index
        if(index > -1){
            //tabelaClientes.getItems().set(index, cli);
            ClienteService.atualizarCliente(index, cli);
            index = -1;
        }else {
            // inclui novo registro
            //tabelaClientes.getItems().add(cli);
            if(ClienteService.buscarClienteByDocumento(cli.getDocumento())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alerta");
                alert.setHeaderText("Documento: " + documento.getText() + " já existe na base!");
                alert.show();
            } else {
                ClienteService.inserirCliente(cli);
            }
        }

        this.carregarListaClientes();

        this.limparCampos();

    }

    public void executarExcluir(){
        if(index > -1){
            //tabelaClientes.getItems().remove(index);
            ClienteService.deletarCliente(index);
            this.carregarListaClientes();
            index = -1;
            this.limparCampos();
        }
    }

    public void limparCampos(){
        nome.setText("");
        documento.setText("");

        documento.setDisable(false);
    }

    public void carregarListaClientes(){

        tabelaClientes.getItems().remove(0, tabelaClientes.getItems().size());

        List<Cliente> cliList = ClienteService.carregarClientes();

        tabelaClientes.getItems().addAll(cliList);
    }
}
