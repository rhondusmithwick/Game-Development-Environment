package voogasalad.util.facebookutil.login;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * General browser login view for any social apps that need a
 * browser in order to log in
 * @author Tommy
 *
 */
public class LoginView {
    private static final double BROWSER_SIZE = 700;
    
    private WebView myPage;
    
    public LoginView (String startURL) {
        myPage = new WebView();
        startPage(myPage, startURL);
        openStage(myPage);
    }

    /**
     * Opens browser up in new stage
     * @param myPage
     */
    private void openStage (WebView myPage) {
        Stage stage = new Stage();
        BorderPane pane = new BorderPane();
        pane.setCenter(myPage);
        stage.setScene(new Scene (pane, BROWSER_SIZE, BROWSER_SIZE));
        stage.show();
    }

    /**
     * Allows the login class to attach a listener for the url
     * @param listener
     */
    public void attachListener (ChangeListener<State> listener) {
        myPage.getEngine().getLoadWorker().stateProperty().addListener(listener);
    }

    /**
     * Starts the browser at a given page
     * @param page
     * @param startURL
     */
    private void startPage (WebView page, String startURL) {
        page.getEngine().load(startURL);
    }
    
    /**
     * Getter for login classes to access the engine
     * @return
     */
    public WebEngine getEngine () {
        return myPage.getEngine();
    }
}
