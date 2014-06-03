    package br.com.foo;

    import br.com.foo.model.Contact;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import org.datafx.reader.RestSource;
import org.datafx.reader.RestSourceBuilder;

    /**
     * Created by moises on 5/31/14.
     */
    public class FooApp extends Application{

        private Stage primaryStage;
        private BorderPane foo;

        @Override
        public void start(Stage stage) throws Exception {
            createContactsListFake();
            primaryStage = stage;
            primaryStage.setTitle("Contacts");

            try {
                FXMLLoader loader = new FXMLLoader(FooApp.class.getResource("view/foo.fxml"));
                foo = (BorderPane) loader.load();
                Scene scene = new Scene(foo);
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            showContacts();
        }

        private void showContacts() {
            try {
                FXMLLoader loader = new FXMLLoader(FooApp.class.getResource("view/contacts.fxml"));
                AnchorPane contactsPage = (AnchorPane) loader.load();
                foo.setCenter(contactsPage);

                ContactOverviewController controller = loader.getController();
                controller.setFooApp(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            launch(args);
        }

        public Stage getPrimaryStage() {
            return primaryStage;
        }

        /* Test data */
        private ObservableList<Contact> contactsFake;

        public ObservableList<Contact> getContactsFake() {
            return contactsFake;
        }

        public void createContactsListFake() {
        	RestSource<Contact> restSource = RestSourceBuilder.create().build();
        	
        	
            if (contactsFake == null) contactsFake = FXCollections.observableArrayList();
            contactsFake.add(new Contact("Hans", "Muster"));
            contactsFake.add(new Contact("Ruth", "Mueller"));
            contactsFake.add(new Contact("Heinz", "Kurz"));
            contactsFake.add(new Contact("Cornelia", "Meier"));
            contactsFake.add(new Contact("Werner", "Meyer"));
            contactsFake.add(new Contact("Lydia", "Kunz"));
            contactsFake.add(new Contact("Anna", "Best"));
            contactsFake.add(new Contact("Stefan", "Meier"));
            contactsFake.add(new Contact("Martin", "Mueller"));
        }

        public boolean showContactEditDialog(Contact contact) {
            try {
                // Load the fxml file and create a new stage for the popup
                FXMLLoader loader = new FXMLLoader(FooApp.class.getResource("view/PersonEditDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Edit Person");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // Set the person into the controller
                ContactEditController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setPerson(contact);

                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();

                return controller.isOkClicked();
            } catch (IOException e) {
                // Exception gets thrown if the fxml file could not be loaded
                e.printStackTrace();
                return false;
            }
        }

    }
