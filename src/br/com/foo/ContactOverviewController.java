package br.com.foo;

import br.com.foo.model.Contact;
import br.com.foo.util.CalendarUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by moises on 5/31/14.
 */
public class ContactOverviewController {

    @FXML
    private TableView<Contact> contactTable;
    @FXML
    private TableColumn<Contact, String> firstNameColumn;
    @FXML
    private TableColumn<Contact, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    private FooApp fooApp;

    public ContactOverviewController() {}

    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));

        clearContactDetails();

        contactTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
            @Override
            public void changed(ObservableValue<? extends Contact> observableValue, Contact oldValue, Contact newValue) {
                showContactDetails(newValue);
            }
        });
    }

    public void setFooApp(FooApp fooApp) {
        this.fooApp = fooApp;
        contactTable.setItems(fooApp.getContactsFake());
    }

    private void clearContactDetails() {
        firstNameLabel.setText("");
        lastNameLabel.setText("");
        streetLabel.setText("");
        postalCodeLabel.setText("");
        cityLabel.setText("");
        birthdayLabel.setText("");
    }

    private void showContactDetails(Contact contact) {
        firstNameLabel.setText(contact.getFirstName());
        lastNameLabel.setText(contact.getLastName());
        streetLabel.setText(contact.getStreet());
        postalCodeLabel.setText(String.valueOf(contact.getPostalCode()));
        cityLabel.setText(contact.getCity());
        birthdayLabel.setText(CalendarUtil.format(contact.getBirthday()));
    }

    @FXML
    private void handleDeleteContact() {
        int selectedIndex = contactTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            contactTable.getItems().remove(selectedIndex);
        } else {
            Dialogs.showWarningDialog(fooApp.getPrimaryStage(), "Please select a person in the table.", "No Person Selected", "No Selection");
        }
    }
}
