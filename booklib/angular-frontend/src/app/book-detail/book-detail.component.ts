import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {

  book: IBook;
  constructor(private route: ActivatedRoute, private api: ApiService, private router: Router) { }

  getBookDetails(id) {
    this.api.getBook(id).subscribe(data => { console.log(data); this.book = data; });
  }

  ngOnInit() {
    this.getBookDetails(this.route.snapshot.params['id']);
  }

  deleteBook(id) {
    this.api.deleteBook(id)
    .subscribe(res => {
        this.router.navigate(['/books']);
      }, (err: IErrMsg) => {
        console.log(err.errMsg);
        if (err.status == 401) {
          this.api.MessageBox('Ошибка', 'Не получилось Вас узнать. Пожалуйста, войдите под своим логином и паролем.');
        }
        else if (err.status == 403) {
          this.api.MessageBox('Ошибка', 'Похоже, у Вас недостаточно прав для удаления записи.');
        }
        else {
          this.api.MessageBox('Ошибка', err.errMsg);
        }
      }
    );
  }

  orderBook(id) {
    this.api.orderBook(id)
      .subscribe(
        res => {
          this.router.navigate(['/books']);
        }
        , err => {
          this.api.MessageBox('Какая-то ошибка...', err.errMsg);
        })
    ;
  }

}
