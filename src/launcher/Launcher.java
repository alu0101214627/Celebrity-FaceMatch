/**
* @file Launcher.java
* @Author Jorge Hdez. Batista
* @date 4/12/21
* @brief The controller of the rest of programs.
*/

package launcher;

import gui.MainGui;
import java.io.IOException;

public class Launcher {
	
	private static void loadBoot() {
		
		MainGui gui = new MainGui("Celebrity FaceMatch");
		gui.run();
	}
	
	public static void main(String[] args) throws IOException {
        loadBoot();
    }

}
