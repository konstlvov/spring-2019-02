import { Component } from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material";
import {MsgboxComponent} from './msgbox/msgbox.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'mean-angular6';
  constructor(private dialog: MatDialog) {}
  openDialog() {

        const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = true;
        dialogConfig.data = {
            id: 1,
            title: 'Angular For Beginners'
        };

        this.dialog.open(MsgboxComponent, dialogConfig);
    }
}
