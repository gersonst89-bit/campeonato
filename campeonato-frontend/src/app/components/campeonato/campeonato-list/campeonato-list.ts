import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Campeonato } from '../../../models/campeonato';
import { CampeonatoService } from '../../../services/campeonato.service';
import { DatePipe } from '@angular/common';

@Component({
    selector: 'app-campeonato-list',
    standalone: true,
    imports: [RouterLink, DatePipe],
    templateUrl: './campeonato-list.html'
})
export class CampeonatoList implements OnInit {

    campeonatos: Campeonato[] = [];
    cargando = true;

    constructor(
        private campeonatoService: CampeonatoService,
        private cdr: ChangeDetectorRef
    ) { }

    ngOnInit(): void {
        this.cargarCampeonatos();
    }

    cargarCampeonatos(): void {
        this.cargando = true;
        this.campeonatoService.getAll().subscribe({
            next: (data) => {
                this.campeonatos = data;
                this.cargando = false;
                this.cdr.detectChanges(); 
            },
            error: (err) => {
                console.error('Error:', err);
                this.cargando = false;
                this.cdr.detectChanges();
            }
        });
    }

    eliminar(id: number): void {
        if (confirm('¿Eliminar este campeonato?')) {
            this.campeonatoService.delete(id).subscribe({
                next: () => this.cargarCampeonatos(),
                error: (err) => console.error(err)
            });
        }
    }
}