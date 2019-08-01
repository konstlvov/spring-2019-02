import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ApiService } from '../api.service';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginData: LoginData;

  constructor(private apiService: ApiService, private router: Router, private http: HttpClient) { }

  ngOnInit() {
    this.loginData = {'login': 'username', 'password': 'userpass'};
  }

  public onLoginButtonClick() {
    console.log('you want to login with ' + this.loginData.login + ':' + this.loginData.password);
    this.apiService.getBooks().subscribe(r => {console.log(r);});
    // todo: if success
    this.router.navigate(['/books']);
  }

}
