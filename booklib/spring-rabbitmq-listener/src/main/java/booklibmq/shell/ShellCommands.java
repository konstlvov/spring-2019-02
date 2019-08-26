package booklibmq.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ShellCommands {

	@ShellMethod("Processes book order.")
	public void processBookOrder(@ShellOption String orderId) {
		System.out.println("Will process order with id " + orderId + ". Not implemented yet.");
	}

}
