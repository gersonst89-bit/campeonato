import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RouterLink } from '@angular/router';
import { PartidoResponse } from '../../../models/partido';
import { PartidoService } from '../../../services/partido.service';
import { DatePipe } from '@angular/common';

@Component({
    selector: 'app-partido-list',
    standalone: true,
    imports: [RouterLink, DatePipe],
    templateUrl: './partido-list.html'
})
export class PartidoList implements OnInit {

    partidos: PartidoResponse[] = [];
    cargando = true;

    constructor(
        private partidoService: PartidoService,
        private cdr: ChangeDetectorRef
    ) { }

    ngOnInit(): void {
        this.cargarPartidos();
    }

    cargarPartidos(): void {
        this.cargando = true;
        this.partidoService.getAll().subscribe({
            next: (data) => {
                this.partidos = data;
                this.cargando = false;
                this.cdr.detectChanges();
            },
            error: (err) => {
                console.error(err);
                this.cargando = false;
                this.cdr.detectChanges();
            }
        });
    }

    eliminar(id: number): void {
        if (confirm('¿Eliminar este partido?')) {
            this.partidoService.delete(id).subscribe({
                next: () => this.cargarPartidos()
            });
        }
    }
}