import controller.CLIController;
import dao.AdminFunctions;

public class Main {
    public static void main(String[] args) throws Exception {
        CLIController controller = new CLIController();
        controller.BasicMenu();

        /*CLIController controller = new CLIController();
        controller.MemberMainMenu("ds");*/
    }
}
