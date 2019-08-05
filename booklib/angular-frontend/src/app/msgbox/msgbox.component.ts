import { Component, OnInit, Inject } from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormGroup, FormBuilder} from "@angular/forms";

@Component({
  selector: 'app-msgbox',
  templateUrl: './msgbox.component.html',
  styleUrls: ['./msgbox.component.css']
})
export class MsgboxComponent implements OnInit {
 form: FormGroup;
    description:string;

  constructor(private fb: FormBuilder, private dialogRef: MatDialogRef<MsgboxComponent>, @Inject(MAT_DIALOG_DATA) data) {
    this.description = data.description;
  }

  ngOnInit() {
  }

    save() {
        this.dialogRef.close(this.form.value);
    }

    close() {
        this.dialogRef.close();
    }

}
