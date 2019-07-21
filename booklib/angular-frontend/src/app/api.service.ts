import { Injectable } from '@angular/core';

import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError, tap, map } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

//const apiUrl = "/api"; // pointer to Express backed API
//const apiUrl = "http://localhost:8080/books"; // pointed to Spring backed API - BookController
//const apiUrl = "http://localhost:8080/booklist"; // pointed to Spring backed autowired API: @RepositoryRestResource
const apiUrl = "http://localhost:8080/fluxbooks"; // pointed to WebFlux backed API implemented in BookController.java again

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // return an observable with a user-facing error message
    return throwError('Something bad happened; please try again later.');
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
