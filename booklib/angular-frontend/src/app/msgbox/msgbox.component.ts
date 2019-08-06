import { Component, OnInit, Inject } from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormGroup, FormBuilder} from "@angular/forms";

@Component({
  selector: 'app-msgbox',
  templateUrl: './msgbox.component.html',
  styleUrls: ['./msgbox.component.css']
})

export class MsgboxComponent implements OnInit {
  //form: FormGroup; // на будущее
  title: string;
  msg: string;

  constructor(private fb: FormBuilder, private dialogRef: MatDialogRef<MsgboxComponent>, @Inject(MAT_DIALOG_DATA) data) {
    this.title = data.title;
    this.msg = data.msg;
  }

  ngOnInit() {

  }

  save() {
    //this.dialogRef.close(this.form.value); // тоже на будущее
  }

  close() {
    this.dialogRef.close();
  }

}
