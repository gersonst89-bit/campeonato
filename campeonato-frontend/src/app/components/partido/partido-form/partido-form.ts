import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterLink, ActivatedRoute } from '@angular/router';
import { PartidoService } from '../../../services/partido.service';
import { CampeonatoService } from '../../../services/campeonato.service';
import { EquipoService } from '../../../services/equipo.service';
import { Campeonato } from '../../../models/campeonato';
import { Equipo } from '../../../models/equipo';

@Component({
    selector: 'app-partido-form',
    standalone: true,
    imports: [ReactiveFormsModule, RouterLink],
    templateUrl: './partido-form.html'
})
export class PartidoForm implements OnInit {

    form: FormGroup;
    campeonatos: Campeonato[] = [];
    equipos: Equipo[] = [];
    mensaje = '';
    error = '';
    modoEditar = false;
    partidoId: number | null = null;

    constructor(
        private fb: FormBuilder,
        private partidoService: PartidoService,
        private campeonatoService: CampeonatoService,
        private equipoService: EquipoService,
        private router: Router,
        private route: ActivatedRoute,
        private cdr: ChangeDetectorRef
    ) {
        this.form = this.fb.group({
            idCampeonato: [null, Validators.required],
            equipoLocalId: [null, Validators.required],
            equipoVisitanteId: [null, Validators.required],
            golesLocal: [null, [Validators.required, Validators.min(0)]],
            golesVisitante: [null, [Validators.required, Validators.min(0)]],
            fecha: ['', Validators.required]
        });
    }

    ngOnInit(): void {
        this.campeonatoService.getAll().subscribe({
            next: (data) => { this.campeonatos = data; this.cdr.detectChanges(); }
        });
        this.equipoService.getAll().subscribe({
            next: (data) => { this.equipos = data; this.cdr.detectChanges(); }
        });

        const id = this.route.snapshot.paramMap.get('id');
        if (id) {
            this.modoEditar = true;
            this.partidoId = +id;
            this.partidoService.getById(this.partidoId).subscribe({
                next: (p) => {
                    this.form.patchValue({
                        idCampeonato: p.idCampeonato,
                        equipoLocalId: p.equipoLocalId,
                        equipoVisitanteId: p.equipoVisitanteId,
                        golesLocal: p.golesLocal,
                        golesVisitante: p.golesVisitante,
                        fecha: p.fecha
                    });
                    this.cdr.detectChanges();
                },
                error: () => {
                    this.error = '❌ No se pudo cargar el partido';
                    this.cdr.detectChanges();
                }
            });
        }
    }

    onSubmit(): void {
        this.error = '';
        if (this.form.value.equipoLocalId === this.form.value.equipoVisitanteId) {
            this.error = '❌ El equipo local y visitante no pueden ser el mismo';
            return;
        }
        if (this.form.valid) {
            const request$ = this.modoEditar
                ? this.partidoService.update(this.partidoId!, this.form.value)
                : this.partidoService.create(this.form.value);

            request$.subscribe({
                next: () => {
                    this.mensaje = this.modoEditar
                        ? '✅ Partido actualizado correctamente'
                        : '✅ Partido registrado correctamente';
                    this.cdr.detectChanges();
                    setTimeout(() => this.router.navigate(['/partidos']), 1500);
                },
                error: (err) => {
                    this.error = this.modoEditar
                        ? '❌ Error al actualizar el partido'
                        : '❌ Error al registrar el partido';
                    console.error(err);
                    this.cdr.detectChanges();
                }
            });
        }
    }
}