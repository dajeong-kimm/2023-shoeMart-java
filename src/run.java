import javax.swing.SwingUtilities;

public class run {
	

	
	public static void main(String[] args) {
		User.initializeDatabase();
		SwingUtilities.invokeLater(() -> {
            new StartPage();
        });
		// TODO Auto-generated method stub

	}

}
