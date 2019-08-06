import { Injectable } from '@angular/core';

import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError, tap, map } from 'rxjs/operators';




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
const apiUrl = "http://localhost:8080/fluxbooks"; // pointed to WebFlux backed API implemented in BookController.java again
//const apiUrl = "http://orsapps-tst:8080/fluxbooks";

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  authenticated: boolean = false;

  constructor(private http: HttpClient) { }

  authenticate(credentials, callback) {

          const headers = new HttpHeaders(credentials ? {
              authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
          } : {});

          this.http.get('user', {headers: headers}).subscribe(response => {
              if (response['name']) {
                  this.authenticated = true;
              } else {
                  this.authenticated = false;
              }
              return callback && callback();
          });

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
      //if (error.status == 401) {
      //  console.log('Не получилось Вас узнать');
      //}
      //if (error.status == 403) {
      //  console.log('У Вас нет прав на данное действие');
      //}
    }
    // return an observable with a user-facing error message
    //return throwError('Something bad happened; please try again later.');
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
