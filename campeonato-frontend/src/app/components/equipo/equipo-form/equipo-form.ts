import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterLink, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { EquipoService } from '../../../services/equipo.service';
import { CampeonatoService } from '../../../services/campeonato.service';
import { Campeonato } from '../../../models/campeonato';

@Component({
    selector: 'app-equipo-form',
    standalone: true,
    imports: [ReactiveFormsModule, CommonModule, RouterLink],
    templateUrl: './equipo-form.html'
})
export class EquipoForm implements OnInit {

    form: FormGroup;
    campeonatos: Campeonato[] = [];
    mensaje = '';
    error = '';
    modoEditar = false;
    equipoId: number | null = null;

    constructor(
        private fb: FormBuilder,
        private equipoService: EquipoService,
        private campeonatoService: CampeonatoService,
        private router: Router,
        private route: ActivatedRoute
    ) {
        this.form = this.fb.group({
            nombre: ['', Validators.required],
            representante: ['', Validators.required],
            fechaCreacion: ['', Validators.required],
            idCampeonato: [null, Validators.required]
        });
    }

    ngOnInit(): void {
        this.campeonatoService.getAll().subscribe({
            next: (data) => this.campeonatos = data,
            error: (err) => console.error(err)
        });

        const id = this.route.snapshot.paramMap.get('id');
        if (id) {
            this.modoEditar = true;
            this.equipoId = +id;
            this.equipoService.getById(this.equipoId).subscribe({
                next: (e) => {
                    this.form.patchValue({
                        nombre: e.nombre,
                        representante: e.representante,
                        fechaCreacion: e.fechaCreacion,
                        idCampeonato: Number(e.idCampeonato)
                    });
                },
                error: () => {
                    this.error = '❌ No se pudo cargar el equipo';
                }
            });
        }
    }

    onSubmit(): void {
        if (this.form.valid) {
            const request$ = this.modoEditar
                ? this.equipoService.update(this.equipoId!, this.form.value)
                : this.equipoService.create(this.form.value);

            request$.subscribe({
                next: () => {
                    this.mensaje = this.modoEditar
                        ? '✅ Equipo actualizado correctamente'
                        : '✅ Equipo registrado correctamente';
                    setTimeout(() => this.router.navigate(['/equipos']), 1500);
                },
                error: (err) => {
                    this.error = err.error?.message
                        || (this.modoEditar ? '❌ Error al actualizar el equipo' : '❌ Error al registrar el equipo');
                    console.error(err);
                }
            });
        }
    }
}