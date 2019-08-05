import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../api.service';
import {MatDialog, MatDialogConfig} from "@angular/material";
import {MsgboxComponent} from '../msgbox/msgbox.component';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {

  book: IBook;
  constructor(private route: ActivatedRoute, private api: ApiService, private router: Router, private dialog: MatDialog) { }

  getBookDetails(id) {
    this.api.getBook(id).subscribe(data => { console.log(data); this.book = data; });
  }

  ngOnInit() {
    this.getBookDetails(this.route.snapshot.params['id']);
  }

  private openDialog(): void {
    console.log('xxx');
          const dialogConfig = new MatDialogConfig();

          dialogConfig.disableClose = true;
          dialogConfig.autoFocus = true;

          dialogConfig.data = {
              id: 1,
              title: 'Angular For Beginners'
          };

          this.dialog.open(MsgboxComponent, dialogConfig);
          
          const dialogRef = this.dialog.open(MsgboxComponent, dialogConfig);

          dialogRef.afterClosed().subscribe(
              data => console.log("Dialog output:", data)
          );    
      }  

  deleteBook(id) {
    this.api.deleteBook(id)
    .subscribe(res => {
        this.router.navigate(['/books']);
      }, (err) => {
        console.log(err);
        this.openDialog();
      }
    );
  }

}
