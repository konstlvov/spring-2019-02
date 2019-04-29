package hello;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="books") // имя коллекции в mongodb. По умолчанию будет book, если явно не задать collection="books"
public class Book {

	@Id private String id;

	private String isbn;
	private String title;
  private String author;
  private String description;
  private String publisher;
  @Field("published_year")
  private String published_year;
  @Field("updated_date") // так задаем имя поля внутри БД монги
  private Date updated_date;
  
  // надо чтобы в JSON оно выглядело как "_id":"5b6d46e8dc78061a6c664c58"
  // такое именование геттера позволяет этого достичь
  public String get_id() {
    return id;
  }
  
  public String getpublished_year() {
    return published_year;
  }
  
  public void setpublished_year(String published_year) {
    this.published_year = published_year;
  }
  
  // аналогично, надо чтобы в JSON оно было в виде "updated_date":"2018-08-10T08:03:52.648+0000"
  // поэтому геттер назван не в camelCase, а так, как назван
  public Date getupdated_date() {
    return updated_date;
  }

  public void setupdated_date(Date date) {
    this.updated_date = date;
  }
  

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
}
