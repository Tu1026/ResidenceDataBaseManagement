package interfaces;

public interface ControllerDelegate {

 ConnectionStateDelegate login(String username, String password);

 void performQuery(String query);

 void initializeSQLDDL();

 ConnectionStateDelegate logout();

 void setUI(TableViewUI ui);

}
