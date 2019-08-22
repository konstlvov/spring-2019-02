package booklib;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookService {
	private final RabbitTemplate rt;

	public BookService(RabbitTemplate rt) {
		this.rt = rt;
	}

	public void postBookOrder(Book b) {
		System.out.println("About to send order for  book " + b.getTitle() + " to RabbitMQ queue");
		rt.convertAndSend(Application.topicExchangeName, "foo.bar.baz", "New order for book " +b.getTitle());
	}
}