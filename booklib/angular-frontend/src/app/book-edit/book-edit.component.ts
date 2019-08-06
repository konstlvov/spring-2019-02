import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ApiService } from '../api.service';
import { FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';

@Component({
  selector: 'app-book-edit',
  templateUrl: './book-edit.component.html',
  styleUrls: ['./book-edit.component.css']
})
export class BookEditComponent implements OnInit {

  bookForm: FormGroup;
  id:string = '';
  isbn:string = '';
  title:string = '';
  description:string = '';
  author:string = '';
  publisher:string = '';
  published_year:string = '';

  constructor(private router: Router, private route: ActivatedRoute, private api: ApiService, private formBuilder: FormBuilder, public matcher: ErrorStateMatcher) { }

  ngOnInit() {
    this.getBook(this.route.snapshot.params['id']);
    this.bookForm = this.formBuilder.group({
      'isbn' : [null, Validators.required],
      'title' : [null, Validators.required],
      'description' : [null, Validators.required],
      'author' : [null, Validators.required],
      'publisher' : [null, Validators.required],
      'published_year' : [null, Validators.required]
    });    
  }

  getBook(id) {
    this.api.getBook(id).subscribe(data => {
      this.id = data._id;
      this.bookForm.setValue({
        isbn: data.isbn,
        title: data.title,
        description: data.description,
        author: data.author,
        publisher: data.publisher,
        published_year: data.published_year
      });
    });
  }  

  onFormSubmit(book: IBook) {
    this.api.updateBook(this.id, book)
    .subscribe(res => {
        let id = res['_id'];
        this.router.navigate(['/book-details', id]);
      }, (err: IErrMsg) => {
        console.log(err.errMsg);
        if (err.status == 401) {
          this.api.MessageBox('Ошибка', 'Не получилось Вас узнать. Пожалуйста, войдите под своим логином и паролем.');
        }
        else if (err.status == 403) {
          this.api.MessageBox('Ошибка', 'Похоже, у Вас недостаточно прав для изменения записи.');
        }
        else {
          this.api.MessageBox('Ошибка', err.errMsg);
        }
      }
    );
  }

  bookDetails() {
    this.router.navigate(['/book-details', this.id]);
  }

}

