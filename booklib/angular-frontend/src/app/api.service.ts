import { Injectable } from '@angular/core';

import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError, tap, map } from 'rxjs/operators';
import {MatDialog, MatDialogConfig} from "@angular/material";
import {MsgboxComponent} from './msgbox/msgbox.component';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
    //'Content-Type': 'application/x-www-form-urlencoded'
    , 'Accept': 'application/json, application/xml, text/plain, */*'
  })
  ,withCredentials: true // disable this when security is disabled on backend
};

//const apiUrl = "/api"; // pointer to Express backed API
//const apiUrl = "http://localhost:8080/books"; // pointed to Spring backed API - BookController
//const apiUrl = "http://localhost:8080/booklist"; // pointed to Spring backed autowired API: @RepositoryRestResource
//const apiUrl = "http://localhost:8080/fluxbooks"; // pointed to WebFlux backed API implemented in BookController.java again
//const apiUrl = "http://vm-oel71:8080/fluxbooks";
//const apiUrl = "http://localhost:8080/api/fluxbooks"; // pointed to WebFlux backed API implemented in BookController.java again
//const apiUrl = "http://orsapps-tst:8080/fluxbooks";
//const apiUrl = "http://localhost:8080/fluxbooks/api"; // use with @RequestMapping(value = "/api") //annotation on BookController
const apiUrl = "http://localhost:8080/fluxbooks";

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  authenticated: boolean = false;

  constructor(private http: HttpClient, private dialog: MatDialog) { }

  public MessageBox(title: string, msg: string) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      title: title
      ,msg: msg
    };
    const dialogRef = this.dialog.open(MsgboxComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
        data => console.log("Dialog output:", data)
    );    
  }

  private handleError(error: HttpErrorResponse) {
    var errForUser: IErrMsg = {status: 0, errMsg: ''};
    var msg: string = '';
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      msg = 'An error occurred:' + error.error.message;
      console.error(msg);
      errForUser.errMsg = msg;
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      msg = `Backend returned code ${error.status}, ` + `body was: ${error.error}`;
      console.error(msg);
      errForUser.errMsg = msg;
      errForUser.status = error.status;
      if (error.error instanceof ProgressEvent) {
        errForUser.errMsg += 'ProgressEvent';
        errForUser.errMsg += '#' + error.error.lengthComputable;
        errForUser.errMsg += '#' + error.error.loaded;
        errForUser.errMsg += '#' + error.error.total;
      }
    }
    // return an observable with a user-facing error message
    return throwError(errForUser);
  };  

  getBooks(): Observable<IBook[]> {
    return this.http.get<IBook[]>(apiUrl, httpOptions).pipe(
      catchError(this.handleError));
  }

  getBook(id: string): Observable<IBook> {
    const url = `${apiUrl}/${id}`;
    return this.http.get<IBook>(url, httpOptions).pipe(
      catchError(this.handleError));
  }

  postBook(book: IBook): Observable<IBook> {
    return this.http.post<IBook>(apiUrl, book, httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }

  updateBook(id: string, book: IBook): Observable<IBook> {
    const url = `${apiUrl}/${id}`;
    return this.http.put<IBook>(url, book, httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }

  deleteBook(id: string): Observable<{}> {
    const url = `${apiUrl}/${id}`;
    return this.http.delete<IBook>(url, httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }  

}
