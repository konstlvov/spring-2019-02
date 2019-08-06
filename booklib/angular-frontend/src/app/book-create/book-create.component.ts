import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';
import { FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';


@Component({
  selector: 'app-book-create',
  templateUrl: './book-create.component.html',
  styleUrls: ['./book-create.component.css']
})
export class BookCreateComponent implements OnInit {

  bookForm: FormGroup;
  isbn:string='';
  title:string='';
  description:string='';
  author:string='';
  publisher:string='';
  published_year:string='';  

  constructor(private router: Router, private api: ApiService, private formBuilder: FormBuilder, public matcher: ErrorStateMatcher) { }

  ngOnInit() {
    this.bookForm = this.formBuilder.group({
      'isbn' : [null, Validators.required],
      'title' : [null, Validators.required],
      'description' : [null, Validators.required],
      'author' : [null, Validators.required],
      'publisher' : [null, Validators.required],
      'published_year' : [null, Validators.required]
    });    
  }

  onFormSubmit(book: IBook) {
    this.api.postBook(book)
    .subscribe(res => {
        let id = res['_id'];
        this.router.navigate(['/book-details', id]);
      }, (err: IErrMsg) => {
        console.log(err.errMsg);
        if (err.status == 401) {
          this.api.MessageBox('Ошибка', 'Не получилось Вас узнать. Пожалуйста, войдите под своим логином и паролем.');
        }
        else if (err.status == 403) {
          this.api.MessageBox('Ошибка', 'Похоже, у Вас недостаточно прав для добавления записи.');
        }
        else {
          this.api.MessageBox('Ошибка', err.errMsg);
        }
      });
  }  

}

