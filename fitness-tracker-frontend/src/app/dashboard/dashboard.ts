import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Chart, ChartConfiguration } from 'chart.js';  // Para gráficos
import { CommonModule } from '@angular/common';
import { SessionFormComponent } from '../session-form/session-form';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css'],
  standalone: true,
  imports: [CommonModule, SessionFormComponent],
  providers: [AuthService]
})
export class DashboardComponent implements OnInit {
  sessions: any[] = [];
  reminder: string = '';
  weeklyDuration = 0;
  sportCounts: any = {};

  private apiUrl = 'http://localhost:8080/api/sessions';  // Backend URL para sesiones

  constructor(private authService: AuthService, private http: HttpClient, private router: Router) {}

  ngOnInit() {
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/login']);  // Protect route
    } else {
      this.loadSessions();
      this.loadReminder();
      this.loadProgress();
    }
  }

  private getHeaders() {
    return new HttpHeaders({ Authorization: 'Bearer ' + this.authService.getToken() });
  }

  loadSessions() {
    this.http.get(this.apiUrl, { headers: this.getHeaders() }).subscribe((data: any) => this.sessions = data);
  }

  loadReminder() {
    this.http.get(`${this.apiUrl}/reminder`, { headers: this.getHeaders() }).subscribe((data: any) => this.reminder = data.message);
  }

  loadProgress() {
    this.http.get(`${this.apiUrl}/progress`, { headers: this.getHeaders() }).subscribe((data: any) => {
      this.weeklyDuration = data.weeklyDuration;
      this.sportCounts = data.sportCounts;
      this.renderCharts();
    });
  }

  renderCharts() {
    // Chart para duración semanal (barra)
    const weeklyConfig: ChartConfiguration = {
      type: 'bar',
      data: { labels: ['Semana Actual'], datasets: [{ data: [this.weeklyDuration], label: 'Minutos' }] }
    };
    new Chart('weeklyChart', weeklyConfig);

    // Chart para tipos de deporte (pie)
    const sportLabels = Object.keys(this.sportCounts);
    const sportData = Object.values(this.sportCounts) as number[];
    const sportConfig: ChartConfiguration = {
      type: 'pie',
      data: { labels: sportLabels, datasets: [{ data: sportData, label: 'Sesiones por Deporte' }] }
    };
    new Chart('sportChart', sportConfig);
  }
}
