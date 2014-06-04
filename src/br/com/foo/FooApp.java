package br.com.foo;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import br.com.foo.model.Contact;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Created by moises on 5/31/14.
 */
public class FooApp extends Application {

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

	public void createContactsListFake() throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://192.168.25.46:8080/foo-web/api/contact");
		httpGet.addHeader("accept", "application/json");
		CloseableHttpResponse response = httpClient.execute(httpGet);
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<Contact> contacts = mapper.readValue(EntityUtils.toString(response.getEntity()),
					TypeFactory.defaultInstance().constructCollectionType(List.class, Contact.class));
			if (contactsFake == null)
				contactsFake = FXCollections.observableArrayList();
			for (Contact c : contacts) {
				contactsFake.add(c);
			}
		} finally {
			response.close();
		}
	}

}
