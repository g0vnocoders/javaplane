import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Object
class UserData {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

// Interactor
class UserInteractor {
    private UserData userData;
    private UserPresenter presenter;

    public UserInteractor(UserPresenter presenter) {
        this.presenter = presenter;
        this.userData = new UserData();
    }

    public void saveUsername(String username) {
        userData.setUsername(username);
        presenter.presentUserSaved();
    }
}

// Presenter
class UserPresenter {
    private UserView view;
    private UserInteractor interactor;

    public UserPresenter(UserView view, UserInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
        this.view.setPresenter(this);
    }

    public void usernameEntered(String username) {
        interactor.saveUsername(username);
    }

    public void presentUserSaved() {
        view.displayMessage("Username saved!");
    }
}

// Decorator
interface UserView {
    void setPresenter(UserPresenter presenter);

    void displayMessage(String message);
}

// AWT Decorator
class AwtUserView extends Frame implements UserView {
    private TextField usernameField;
    private Button saveButton;
    private UserPresenter presenter;

    public AwtUserView() {
        usernameField = new TextField(20);
        saveButton = new Button("Save");

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                presenter.usernameEntered(usernameField.getText());
            }
        });

        add(usernameField);
        add(saveButton);
        setSize(300, 200);
        setLayout(new FlowLayout());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    @Override
    public void setPresenter(UserPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void showView() {
        setVisible(true);
    }
}

// Router
class UserRouter {
    // Add navigation logic here if needed
}

// Decorator
class UserViewDecorator implements UserView {
    private UserView decoratedView;

    public UserViewDecorator(UserView decoratedView) {
        this.decoratedView = decoratedView;
    }

    @Override
    public void setPresenter(UserPresenter presenter) {
        decoratedView.setPresenter(presenter);
    }

    @Override
    public void displayMessage(String message) {
        System.out.println("Decorated: " + message);
    }
}

public class pidorTest {
    public static void main(String[] args) {
        AwtUserView awtUserView = new AwtUserView();
        UserView decoratedView = new UserViewDecorator(awtUserView);

        UserInteractor interactor = new UserInteractor(new UserPresenter(decoratedView, new UserInteractor(decoratedView)));

        awtUserView.showView();
    }
}
