import { Component, OnInit, OnDestroy, ChangeDetectorRef, ViewChild, ElementRef } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CampeonatoService } from '../../services/campeonato.service';
import { PartidoService } from '../../services/partido.service';
import { Campeonato } from '../../models/campeonato';
import { PartidoResponse, PosicionTabla } from '../../models/partido';
import { DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { forkJoin } from 'rxjs';
import {
    Chart, BarController, BarElement,
    CategoryScale, LinearScale, Tooltip, Legend
} from 'chart.js';

Chart.register(BarController, BarElement, CategoryScale, LinearScale, Tooltip, Legend);

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [ReactiveFormsModule, RouterLink, DatePipe],
    templateUrl: './dashboard.html'
})
export class Dashboard implements OnInit, OnDestroy {

    @ViewChild('chartCanvas') chartCanvas!: ElementRef<HTMLCanvasElement>;

    campeonatos: Campeonato[] = [];
    campeonatoSeleccionado: Campeonato | null = null;
    partidos: PartidoResponse[] = [];
    tabla: PosicionTabla[] = [];
    cargando = false;
    totalGoles = 0;
    liderTorneo = '—';

    form: FormGroup;
    private chart: Chart | null = null;

    constructor(
        private fb: FormBuilder,
        private campeonatoService: CampeonatoService,
        private partidoService: PartidoService,
        private cdr: ChangeDetectorRef
    ) {
        this.form = this.fb.group({
            idCampeonato: [null, Validators.required]
        });
    }

    ngOnInit(): void {
        this.campeonatoService.getAll().subscribe({
            next: (data) => {
                this.campeonatos = data;
                this.cdr.detectChanges();

                const savedId = localStorage.getItem('dashboardCampeonatoId');
                if (savedId) {
                    this.form.patchValue({ idCampeonato: savedId });
                    this.cargarDashboard();
                }
            }
        });
    }

    ngOnDestroy(): void {
        this.chart?.destroy();
    }

    cargarDashboard(): void {
        const id = this.form.value.idCampeonato;
        if (!id) return;

        localStorage.setItem('dashboardCampeonatoId', id);
        this.cargando = true;
        this.campeonatoSeleccionado = this.campeonatos.find(c => c.id == id) || null;

        forkJoin({
            partidos: this.partidoService.getByCampeonato(id),
            tabla: this.partidoService.getTabla(id)
        }).subscribe({
            next: ({ partidos, tabla }) => {
                this.partidos = partidos;
                this.tabla = tabla;
                this.totalGoles = partidos.reduce((sum, p) => sum + p.golesLocal + p.golesVisitante, 0);
                this.liderTorneo = tabla.length > 0 ? tabla[0].nombreEquipo : '—';
                this.cargando = false;
                this.cdr.detectChanges();
                setTimeout(() => this.renderChart(), 50);
            },
            error: () => {
                this.cargando = false;
                this.cdr.detectChanges();
            }
        });
    }

    private renderChart(): void {
        if (!this.chartCanvas || this.tabla.length === 0) return;

        this.chart?.destroy();

        const labels = this.tabla.map(t => t.nombreEquipo);
        const puntos = this.tabla.map(t => t.pts);
        const golesFavor = this.tabla.map(t => t.gf);

        this.chart = new Chart(this.chartCanvas.nativeElement, {
            type: 'bar',
            data: {
                labels,
                datasets: [
                    {
                        label: 'Puntos',
                        data: puntos,
                        backgroundColor: puntos.map((_, i) =>
                            i === 0 ? 'rgba(245,158,11,0.85)' : 'rgba(34,197,94,0.75)'
                        ),
                        borderColor: puntos.map((_, i) =>
                            i === 0 ? '#f59e0b' : '#22c55e'
                        ),
                        borderWidth: 2,
                        borderRadius: 8,
                        borderSkipped: false,
                    },
                    {
                        label: 'Goles a favor',
                        data: golesFavor,
                        backgroundColor: 'rgba(96,165,250,0.5)',
                        borderColor: '#60a5fa',
                        borderWidth: 2,
                        borderRadius: 8,
                        borderSkipped: false,
                    }
                ]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: {
                        labels: {
                            color: '#7a8fa6',
                            font: { family: 'Satoshi', size: 13, weight: 600 },
                            usePointStyle: true,
                            pointStyle: 'circle',
                            padding: 20
                        }
                    },
                    tooltip: {
                        backgroundColor: '#0d1117',
                        borderColor: 'rgba(34,197,94,0.3)',
                        borderWidth: 1,
                        titleColor: '#f0f4f8',
                        bodyColor: '#7a8fa6',
                        padding: 12,
                        cornerRadius: 10,
                        titleFont: { family: 'Cabinet Grotesk', size: 14, weight: 700 },
                        bodyFont: { family: 'Satoshi', size: 13 }
                    }
                },
                scales: {
                    x: {
                        ticks: {
                            color: '#7a8fa6',
                            font: { family: 'Satoshi', size: 13, weight: 600 }
                        },
                        grid: { color: 'rgba(30,42,53,0.8)' },
                        border: { color: 'rgba(30,42,53,0.8)' }
                    },
                    y: {
                        beginAtZero: true,
                        ticks: {
                            color: '#7a8fa6',
                            font: { family: 'Satoshi', size: 12 },
                            stepSize: 1
                        },
                        grid: { color: 'rgba(30,42,53,0.8)' },
                        border: { color: 'rgba(30,42,53,0.8)' }
                    }
                }
            }
        });
    }

    getPosicionIcon(index: number): string {
        if (index === 0) return '🥇';
        if (index === 1) return '🥈';
        if (index === 2) return '🥉';
        return `${index + 1}°`;
    }
}