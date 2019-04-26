package hello;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="books") // имя коллекции в mongodb. По умолчанию будет book
public class Book {

	@Id private String id;

	private String isbn;
	private String title;
  private String author;
  private String description;
  private String publisher;
  @Field("updated_date")
  private Date updatedDate;
  
  public Date getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Date date) {
    this.updatedDate = date;
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
