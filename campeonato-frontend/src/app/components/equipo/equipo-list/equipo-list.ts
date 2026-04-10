import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Equipo } from '../../../models/equipo';
import { EquipoService } from '../../../services/equipo.service';

@Component({
    selector: 'app-equipo-list',
    standalone: true,
    imports: [RouterLink],
    templateUrl: './equipo-list.html'
})
export class EquipoList implements OnInit {

    equipos: Equipo[] = [];
    cargando = true;

    constructor(
        private equipoService: EquipoService,
        private cdr: ChangeDetectorRef
    ) { }

    ngOnInit(): void {
        this.cargarEquipos();
    }

    cargarEquipos(): void {
        this.cargando = true;
        this.equipoService.getAll().subscribe({
            next: (data) => {
                this.equipos = data;
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
        if (confirm('¿Eliminar este equipo?')) {
            this.equipoService.delete(id).subscribe({
                next: () => this.cargarEquipos(),
                error: (err) => console.error(err)
            });
        }
    }
}