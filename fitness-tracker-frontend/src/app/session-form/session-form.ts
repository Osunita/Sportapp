import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../auth';
import { ReactiveFormsModule } from '@angular/forms';


@Component({
  selector: 'app-session-form',
  templateUrl: './session-form.html',
  styleUrls: ['./session-form.css'],
  standalone: true,
  imports: [ReactiveFormsModule],
  providers: [AuthService]
})
export class SessionFormComponent {
  sessionForm: FormGroup;

  private apiUrl = 'http://localhost:8080/api/sessions';

  constructor(private fb: FormBuilder, private http: HttpClient, private authService: AuthService) {
    this.sessionForm = fb.group({
      sportType: ['', Validators.required],
      duration: ['', Validators.required],
      intensity: ['', Validators.required],
      notes: ['']
    });
  }

  onSubmit() {
    if (this.sessionForm.valid) {
      this.http.post(this.apiUrl, this.sessionForm.value, { headers: new HttpHeaders({ Authorization: 'Bearer ' + this.authService.getToken() }) }).subscribe(
        () => console.log('Sesión creada'),  // Refresh dashboard después
        error => console.error(error)
      );
    }
  }
}
