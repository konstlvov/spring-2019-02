import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { DataSource } from '@angular/cdk/collections';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  books: IBook[];
  displayedColumns = ['isbn', 'title', 'author'];
  dataSource = new BookDataSource(this.api);  
  constructor(private api: ApiService) { }
	
	public routerLogout() {
		this.api.doLogout();
	}

  ngOnInit() {
    this.api.getBooks().subscribe(
      res => {console.log(res); this.books = res;}
      , (err: IErrMsg) => {
        console.log(err.errMsg);
        if (err.status == 401) {
          this.api.MessageBox('Ошибка', 'Не получилось Вас узнать. Пожалуйста, войдите под своим логином и паролем.');
        }
        else if (err.status == 403) {
          this.api.MessageBox('Ошибка', 'Похоже, у Вас недостаточно прав для просмотра записей.');
        }
        else {
          this.api.MessageBox('Ошибка', err.errMsg);
        }
      }
    );
  }

}

export class BookDataSource extends DataSource<IBook[]> {
  constructor(private api: ApiService) {
    super();
  }

  connect(): Observable<any> {
    return this.api.getBooks();
  }

  disconnect() {
  }

}


